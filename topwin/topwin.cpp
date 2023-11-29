#include "framework.h"
#include "topwin.h"
#include <string>
#include <vector>

#define MAX_LOADSTRING 100
#define MENU_ID 29

// 全局变量:
HINSTANCE hInst;                                // 当前实例
WCHAR szTitle[MAX_LOADSTRING];                  // 标题栏文本
WCHAR szWindowClass[MAX_LOADSTRING];            // 主窗口类名

int topHotkeyId = 1;
int untopHotkeyId = 2;
HWND hComboBox;
std::vector<HWND> windows;

// 此代码模块中包含的函数的前向声明:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);

// 展示并置顶窗口
void topwin(HWND hWnd)
{
    // 通过快捷键方式不需要传递窗口句柄
    if (hWnd == nullptr) {
        hWnd = GetForegroundWindow();
    }
    DWORD currentId = GetCurrentThreadId();
    DWORD topId = GetWindowThreadProcessId(GetForegroundWindow(), NULL);
    AttachThreadInput(currentId, topId, TRUE);

    // 展示并置顶窗口
    ShowWindow(hWnd, SW_SHOWNORMAL);
    SetWindowPos(hWnd, HWND_TOPMOST, 0, 0, 0, 0, SWP_NOSIZE | SWP_NOMOVE);
    SetForegroundWindow(hWnd);

    AttachThreadInput(currentId, topId, FALSE);
}

// 判断窗口是否在桌面正常显示
bool IsWindowOnDesktop(HWND hwnd) {
    WINDOWPLACEMENT placement = { sizeof(WINDOWPLACEMENT) };
    if (GetWindowPlacement(hwnd, &placement)) {
        return placement.showCmd == SW_SHOWNORMAL;
    }
    return false;
}

// 遍历窗口并获取窗口标题展示在下拉框中
BOOL CALLBACK EnumWindowsProc(HWND hwnd, LPARAM lParam) {
    if (IsWindowVisible(hwnd) && !IsWindowOnDesktop(hwnd)) {
        int titleLength = GetWindowTextLength(hwnd);
        if (titleLength > 0) {
            std::wstring windowTitle(titleLength + 1, L'\0');
            GetWindowText(hwnd, &windowTitle[0], titleLength + 1);
            SendMessageW(hComboBox, CB_ADDSTRING, 0, reinterpret_cast<LPARAM>(windowTitle.c_str()));
            windows.push_back(hwnd);
        }
    }
    return TRUE;
}

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPWSTR    lpCmdLine,
                     _In_ int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    // 初始化全局字符串
    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_TOPWIN, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // 执行应用程序初始化:
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    // 注册全局快捷键
    if (!RegisterHotKey(NULL, topHotkeyId, MOD_CONTROL | MOD_SHIFT, 'T')) {
        return 1;
    }

    if (!RegisterHotKey(NULL, untopHotkeyId, MOD_CONTROL | MOD_SHIFT, 'C')) {
        return 1;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_TOPWIN));

    MSG msg;

    // 主消息循环:
    while (GetMessage(&msg, nullptr, 0, 0))
    {
        if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
            if (msg.message == WM_HOTKEY) {
                HWND hWnd = GetForegroundWindow();
                if (msg.wParam == topHotkeyId) {
                    topwin(nullptr);
                }
                if (msg.wParam == untopHotkeyId) {
                    SetWindowPos(hWnd, HWND_NOTOPMOST, 0, 0, 100, 100, SWP_NOMOVE | SWP_NOSIZE);
                }
            }
        }
    }

    return (int) msg.wParam;
}


//
//  函数: MyRegisterClass()
//
//  目标: 注册窗口类。
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEXW wcex;

    wcex.cbSize = sizeof(WNDCLASSEX);

    wcex.style          = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc    = WndProc;
    wcex.cbClsExtra     = 0;
    wcex.cbWndExtra     = 0;
    wcex.hInstance      = hInstance;
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_TOPWIN));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszClassName  = szWindowClass;
    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_TOPWIN));

    return RegisterClassExW(&wcex);
}

//
//   函数: InitInstance(HINSTANCE, int)
//
//   目标: 保存实例句柄并创建主窗口
//
//   注释:
//
//        在此函数中，我们在全局变量中保存实例句柄并
//        创建和显示主程序窗口。
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   hInst = hInstance; // 将实例句柄存储在全局变量中

   // 居中窗口
   int screenWidth = GetSystemMetrics(SM_CXSCREEN);
   int screenHeight = GetSystemMetrics(SM_CYSCREEN);
   int windowWidth = 400;
   int windowHeight = 270;
   int x = (screenWidth - windowWidth) / 2;
   int y = (screenHeight - windowHeight) / 2;
   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
       x, y, windowWidth, windowHeight, nullptr, nullptr, hInstance, nullptr);

   // 创建下拉框
   hComboBox = CreateWindowW(L"ComboBox", L"", CBS_DROPDOWN | WS_CHILD | WS_VISIBLE,
       10, 10, 360, 200, hWnd, (HMENU)MENU_ID, nullptr, nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   ShowWindow(hWnd, nCmdShow);

   EnumWindows(EnumWindowsProc, 0);
   // 设置默认选中项
   SendMessageW(hComboBox, CB_SETCURSEL, 0, 0);

   UpdateWindow(hWnd);

   return TRUE;
}

//
//  函数: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  目标: 处理主窗口的消息。
//
//  WM_COMMAND  - 处理应用程序菜单
//  WM_PAINT    - 绘制主窗口
//  WM_DESTROY  - 发送退出消息并返回
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    switch (message)
    {
    case WM_COMMAND:
        {
            if (HIWORD(wParam) == CBN_SELCHANGE) {
                LRESULT selectedIndex = SendMessageW(GetDlgItem(hWnd, MENU_ID), CB_GETCURSEL, 0, 0);
                topwin(windows.at(selectedIndex));
            }
        }
        break;
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
            EndPaint(hWnd, &ps);
        }
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}
