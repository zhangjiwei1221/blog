#include "windows.h"
#include "ntstatus.h"
#include <locale>
#include <codecvt>
#include <QIcon>
#include <QObject>
#include <QThreadPool>
#include <QGuiApplication>
#include <QQmlApplicationEngine>

using namespace std;

// 系统信息类型枚举类
typedef enum _SYSTEM_INFORMATION_CLASS
{
    SystemBasicInformation,
    SystemProcessorInformation,
    SystemPerformanceInformation,
    SystemTimeOfDayInformation,
    SystemPathInformation,
    SystemProcessInformation,
    SystemCallCountInformation,
    SystemDeviceInformation,
    SystemProcessorPerformanceInformation,
    SystemFlagsInformation,
    SystemCallTimeInformation,
    SystemModuleInformation,
    SystemLocksInformation,
    SystemStackTraceInformation,
    SystemPagedPoolInformation,
    SystemNonPagedPoolInformation,
    SystemHandleInformation,
    SystemObjectInformation,
    SystemPageFileInformation,
    SystemVdmInstemulInformation,
    SystemVdmBopInformation,
    SystemFileCacheInformation,
    SystemPoolTagInformation,
    SystemInterruptInformation,
    SystemDpcBehaviorInformation,
    SystemFullMemoryInformation,
    SystemLoadGdiDriverInformation,
    SystemUnloadGdiDriverInformation,
    SystemTimeAdjustmentInformation,
    SystemSummaryMemoryInformation,
    SystemNextEventIdInformation,
    SystemEventIdsInformation,
    SystemCrashDumpInformation,
    SystemExceptionInformation,
    SystemCrashDumpStateInformation,
    SystemKernelDebuggerInformation,
    SystemContextSwitchInformation,
    SystemRegistryQuotaInformation,
    SystemExtendServiceTableInformation,
    SystemPrioritySeperation,
    SystemPlugPlayBusInformation,
    SystemDockInformation,
    SystemProcessorSpeedInformation,
    SystemCurrentTimeZoneInformation,
    SystemLookasideInformation
} SYSTEM_INFORMATION_CLASS, * PSYSTEM_INFORMATION_CLASS;

// 声明 Native API: NTQUERYSYSTEMINFOMATION
typedef NTSTATUS(WINAPI* NTQUERYSYSTEMINFOMATION)
(
    IN SYSTEM_INFORMATION_CLASS SystemClass,
    OUT LPVOID SystemInfo,
    IN ULONG SystemInfoLength,
    OUT PULONG ReturnLength
    );

// 句柄内容
typedef struct _SYSTEM_HANDLE
{
    ULONG ProcessId;
    UCHAR ObjectTypeNumber;
    UCHAR Flags;
    USHORT Handle;
    PVOID Object;
    ACCESS_MASK GrantedAccess;
} SYSTEM_HANDLE, * PSYSTEM_HANDLE;

// 系统句柄信息
typedef struct _SYSTEM_HANDLE_INFORMATION
{
    ULONG NumberOfHandles;
    SYSTEM_HANDLE HandleInfo[1];
} SYSTEM_HANDLE_INFORMATION, * PSYSTEM_HANDLE_INFORMATION;

// 对象名信息
typedef struct _OBJECT_NAME_INFORMATION
{
    string Name;
    WCHAR NameBuffer[1];
} OBJECT_NAME_INFORMATION, * POBJECT_NAME_INFORMATION;

// 声明 Native API: NtQueryObject
typedef long(__stdcall* PNtQueryObject)
(
    HANDLE ObjectHandle,
    ULONG ObjectInformationClass,
    PVOID ObjectInformation,
    ULONG ObjectInformationLength,
    PULONG ReturnLength
    );

POBJECT_NAME_INFORMATION pObject;
PNtQueryObject NtQueryObject;
HANDLE hCopy;
string processName;

// 系统句柄信息
PSYSTEM_HANDLE_INFORMATION pHandleInfo;

// 空文件句柄, 用于获取文件类型句柄对应的对象类型数字
HANDLE nulFileHandle;

/// <summary>
/// wchar_t* 转为 string
/// </summary>
/// <param name="szDst">目标字符串</param>
/// <param name="wchar">wchar 字符串</param>
void wStringToString(string& szDst, wchar_t* wchar)
{
    wchar_t* wText = wchar;
    DWORD dwNum = WideCharToMultiByte(CP_OEMCP, NULL, wText, -1, NULL, 0, NULL, FALSE);
    char* psText;
    psText = new char[dwNum];
    WideCharToMultiByte(CP_OEMCP, NULL, wText, -1, psText, dwNum, NULL, FALSE);
    szDst = psText;
    delete[] psText;
}

/// <summary>
/// 查询对象信息
/// </summary>
/// <param name="lpParam">参数</param>
/// <returns>返回值</returns>
DWORD queryObj(LPVOID lpParam)
{
    return NtQueryObject(hCopy, 1, pObject, MAX_PATH * 2, NULL);
}

/// <summary>
/// 获取文件名
/// </summary>
/// <param name="hCopy">文件句柄</param>
/// <param name="hCopy">文件名</param>
void getFileName(string& fileName)
{
    // 查找句柄对象信息并分配内存进行保存
    pObject = (POBJECT_NAME_INFORMATION)HeapAlloc(GetProcessHeap(), 0, MAX_PATH * 2);
    if (pObject == 0)
    {
        HeapFree(GetProcessHeap(), 0, pObject);
        return;
    }

    // NtQueryObject 调用会出现阻塞, 启动线程增加超时处理
    HANDLE hThread = CreateThread(NULL, 0, queryObj, NULL, 0, NULL);
    if (hThread == 0)
    {
        HeapFree(GetProcessHeap(), 0, pObject);
        return;
    }
    DWORD dwSatus = WaitForSingleObject(hThread, 200);
    if (dwSatus == WAIT_TIMEOUT)
    {
        HeapFree(GetProcessHeap(), 0, pObject);
        return;
    }

    // 返回文件名
    if (pObject->NameBuffer != NULL)
    {
        DWORD n = WideCharToMultiByte(CP_OEMCP, NULL, pObject->NameBuffer, -1, NULL, 0, NULL, FALSE);
        char* name = new char[n + 1];
        memset(name, 0, n + 1);
        WideCharToMultiByte(CP_OEMCP, NULL, pObject->NameBuffer, -1, name, n, NULL, FALSE);
        fileName = name;
        delete[] name;
        HeapFree(GetProcessHeap(), 0, pObject);
        return;
    }
    HeapFree(GetProcessHeap(), 0, pObject);
    return;
}

/// <summary>
/// 初始化处理
/// </summary>
/// <returns>是否正常初始化</returns>
bool init()
{
    // 从 ntdll.dll 中加载 Native API: NtQuerySystemInformation 用于遍历获取系统信息
    HMODULE hNtDll = LoadLibrary(L"ntdll.dll");
    if (hNtDll == NULL)
    {
        return false;
    }
    NTQUERYSYSTEMINFOMATION NtQuerySystemInformation = (NTQUERYSYSTEMINFOMATION)GetProcAddress(hNtDll, "NtQuerySystemInformation");
    if (NtQuerySystemInformation == NULL)
    {
        return false;
    }

    // 用于获取操作系统中文件类型句柄对应的对象类型数字
    nulFileHandle = CreateFile(L"NUL", GENERIC_READ, 0, NULL, OPEN_EXISTING, 0, 0);
    if (nulFileHandle == NULL)
    {
        return false;
    }

    // 从 ntdll.dll 中加载 Native API: NtQueryObject 用于获取句柄对象信息
    NtQueryObject = (PNtQueryObject)GetProcAddress(hNtDll, "NtQueryObject");

    // 查找所有的句柄信息并分配内存进行保存
    DWORD nSize = 4096;
    pHandleInfo = (PSYSTEM_HANDLE_INFORMATION)HeapAlloc(GetProcessHeap(), 0, nSize);
    while (NtQuerySystemInformation(SystemHandleInformation, pHandleInfo, nSize, NULL) == STATUS_INFO_LENGTH_MISMATCH)
    {
        HeapFree(GetProcessHeap(), 0, pHandleInfo);
        nSize += 4096;
        pHandleInfo = (PSYSTEM_HANDLE_INFORMATION)HeapAlloc(GetProcessHeap(), 0, nSize);
    }
    if (pHandleInfo == NULL)
    {
        return false;
    }
    return true;
}

/// <summary>
/// 获取文件类型对应的对象编号, 经测试 win11: 40 win10: 37 win7: 28, 默认返回 win11 下的编码
/// </summary>
/// <returns>文件类型对应的对象编号</returns>
int getFileObjectTypeNumber()
{
    // 遍历所有的句柄
    for (ULONG i = 0; i < pHandleInfo->NumberOfHandles; i++)
    {
        PSYSTEM_HANDLE pHandle = (PSYSTEM_HANDLE) & (pHandleInfo->HandleInfo[i]);

        if ((int)GetCurrentProcessId() == pHandle->ProcessId && pHandle->Handle == (USHORT)nulFileHandle)
        {
            return (int)pHandle->ObjectTypeNumber;
        }
    }
    return 40;
}

/// <summary>
/// 关闭文件
/// </summary>
/// <param name="closeFileName">关闭的文件名</param>
void closeFile(string& closeFileName)
{
    int fileObjectTypeNumber = getFileObjectTypeNumber();
    // 遍历所有的句柄
    for (ULONG i = 0; i < pHandleInfo->NumberOfHandles; i++)
    {
        PSYSTEM_HANDLE pHandle = (PSYSTEM_HANDLE) & (pHandleInfo->HandleInfo[i]);
        // 只处理类型为文件且不属于系统进程(id 为 4)的句柄
        if (pHandle->ObjectTypeNumber != fileObjectTypeNumber || pHandle->ProcessId == 4 || pHandle->Handle == 0)
        {
            continue;
        }
        // 打开句柄对应的进行并进行复制用于后续操作
        HANDLE hProcess = OpenProcess(PROCESS_DUP_HANDLE | PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, FALSE, pHandle->ProcessId);
        if (hProcess == NULL)
        {
            continue;
        }
        hCopy = 0;
        if (!DuplicateHandle(hProcess, (HANDLE)pHandle->Handle, GetCurrentProcess(), &hCopy, MAXIMUM_ALLOWED, FALSE, 0))
        {
            continue;
        }

        // 根据句柄获取文件名
        int pid = pHandle->ProcessId;
        string fileName;
        getFileName(fileName);
        if (fileName.find(closeFileName) != -1)
        {
            // 获取占用的进程名称
            WCHAR tmpName[MAX_PATH] = {};
            DWORD size = MAX_PATH;
            QueryFullProcessImageName(hProcess, 0, tmpName, &size);
            wStringToString(processName, tmpName);

            // 关闭占用的文件句柄
            HANDLE h_tar = NULL;
            if (DuplicateHandle(hProcess, (HANDLE)pHandle->Handle, GetCurrentProcess(), &h_tar, 0, FALSE, DUPLICATE_SAME_ACCESS | DUPLICATE_CLOSE_SOURCE))
            {
                CloseHandle(h_tar);
            }
            CloseHandle(hCopy);
            CloseHandle(hProcess);
            return;
        }
        CloseHandle(hCopy);
        CloseHandle(hProcess);
    }
    HeapFree(GetProcessHeap(), 0, pHandleInfo);
    return;
}

/// <summary>
/// 字符串转换, char* 转为 wchar_t* 类型
/// </summary>
/// <param name="charArray">原始字符串</param>
/// <returns>结果字符串</returns>
wchar_t* stringToWString(const char* charArray)
{
	wchar_t* wString = new wchar_t[4096];
	MultiByteToWideChar(CP_ACP, 0, charArray, -1, wString, 4096);
	return wString;
}

/// <summary>
/// GBK 编码字符串转为 UTF8 字符串
/// </summary>
/// <param name="gbkData">gbk 字符串</param>
/// <returns>UTF8 字符串</returns>
string gbkToUTF8(const std::string& gbkData)
{
	wstring_convert<codecvt<wchar_t, char, mbstate_t>> conv(new codecvt<wchar_t, char, mbstate_t>("CHS"));
	wstring_convert<codecvt_utf8<wchar_t>> convert;
    return convert.to_bytes(conv.from_bytes(gbkData));
}

/// <summary>
/// 设置右键菜单
/// </summary>
/// <param name="strRegKeyKey">注册键</param>
/// <param name="strRegKeyName">注册名</param>
/// <param name="strApplication">应用地址</param>
/// <returns>是否添加成功</returns>
bool setRightMenu(string strRegKeyKey, string strRegKeyName, string strApplication)
{
	HKEY hresult;
	string strRegKey = "*\\shell\\" + strRegKeyKey;
	string strRegSubkey = strRegKey + "\\command";
	string strApplicationValue = "\"" + strApplication +  "\"" + " \"%1\"";
	DWORD dwPos;
	// 创建注册表键, 对应右键菜单项
	if (RegCreateKeyEx(HKEY_CLASSES_ROOT, stringToWString(strRegKey.c_str()), 0,
		NULL, REG_OPTION_NON_VOLATILE, KEY_CREATE_SUB_KEY | KEY_ALL_ACCESS, NULL, &hresult, &dwPos) != ERROR_SUCCESS)
	{
		RegCloseKey(hresult);
		return false;
	}

	// 创建注册表值, 对应右键菜单项显示的内容
	if (RegSetValueEx(hresult, NULL, 0, REG_SZ, (BYTE*)stringToWString(strRegKeyName.c_str()), (wcslen(stringToWString(strApplicationValue.c_str())) + 1) * sizeof(wchar_t)) != ERROR_SUCCESS)
	{
		RegCloseKey(hresult);
		return false;
	}

	// 设置右键菜单图标
	if (RegSetValueEx(hresult, stringToWString("Icon"), 0, REG_SZ, (BYTE*)stringToWString(strApplication.c_str()), (wcslen(stringToWString(strApplication.c_str())) + 1) * sizeof(wchar_t)) != ERROR_SUCCESS)
	{
		RegCloseKey(hresult);
		return false;
	}

	// 创建注册表子项键, 对应点击右键菜单项后的命令项
	if (RegCreateKeyEx(HKEY_CLASSES_ROOT, stringToWString(strRegSubkey.c_str()), 0, NULL, REG_OPTION_NON_VOLATILE, KEY_CREATE_SUB_KEY | KEY_ALL_ACCESS, NULL, &hresult, &dwPos) != ERROR_SUCCESS)
	{
		RegCloseKey(hresult);
		return false;
	}

	// 创建注册表子项值, 对应点击右键菜单项后的具体执行命令
	if (RegSetValueEx(hresult, NULL, 0, REG_SZ, (BYTE*)stringToWString(strApplicationValue.c_str()), (wcslen(stringToWString(strApplicationValue.c_str())) + 1) * sizeof(wchar_t)) != ERROR_SUCCESS)
	{
		RegCloseKey(hresult);
		return false;
	}
	RegCloseKey(hresult);
	return true;
}

/// <summary>
/// 程序入口, 通过右键菜单点击时会传递文件名的参数用于解锁文件
/// </summary>
/// <param name="argc">参数个数</param>
/// <param name="argv">参数</param>
/// <returns></returns>
int main(int argc, char *argv[])
{
    QGuiApplication app(argc, argv);

    QQmlApplicationEngine engine;
	if (argc == 1)
	{
        // 无参时为手动运行程序, 添加注册表信息
        engine.load(QUrl(QStringLiteral("qrc:/res/qml/main.qml")));
		QObject* root = engine.rootObjects().first();
		QVariant showInfo;
        string appPath = QCoreApplication::applicationDirPath().replace(QRegExp("/"), "\\").toStdString() + "\\unlockfile.exe";
		if (setRightMenu( "unlockfile", "解锁文件", appPath))
		{
			showInfo = u8"注册表添加成功";
		}
		else
		{
			showInfo = u8"注册表添加失败, 请确保以管理员身份运行";
		}
		QMetaObject::invokeMethod(root, "showInfo", Q_ARG(QVariant, showInfo));
	}
	else
	{
        // 有参时, 根据参数(即文件路径)查找文件句柄进行关闭
		engine.load(QUrl(QStringLiteral("qrc:/res/qml/dialog.qml")));
		QObject* root = engine.rootObjects().first();
        QThreadPool::globalInstance()->start([=]() {
            string fileName = gbkToUTF8(argv[1]).substr(3);
            if (init())
            {
                closeFile(fileName);
                string info = u8"解锁成功, 占用程序: " + processName;
                QMetaObject::invokeMethod(root, "showFile", Q_ARG(QVariant, QString::fromStdString(info)));
            }
        });
	}
    app.setWindowIcon(QIcon(":/res/img/unlockfile.ico"));
    return app.exec();
}
