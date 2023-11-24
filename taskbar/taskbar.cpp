#include "framework.h"
#include "taskbar.h"
#include <shobjidl.h>
#include <dwmapi.h>
#include <gdiplus.h>

#pragma comment(lib, "Dwmapi.lib")
#pragma comment(lib, "gdiplus.lib")


#define MAX_LOADSTRING 100
#define BTN_COUNT 4
#define BG_COUNT 3

// 全局变量:

// 当前实例
HINSTANCE hInst;

// 标题栏文本
WCHAR szTitle[MAX_LOADSTRING];

// 主窗口类名
WCHAR szWindowClass[MAX_LOADSTRING];

// 任务栏按钮
THUMBBUTTON btns[BTN_COUNT];

// 任务栏对象
ITaskbarList3* pTaskbar;

// 当前下标
int bgIndex = 0;

// 背景图
WCHAR bgImgs[3][8] = { L"bg1.bmp", L"bg2.bmp", L"bg3.bmp" };

// 控制暂停/播放切换
bool play = true;

// 控制喜欢/取消喜欢切换
bool unlike = true;

// 开启自定义背景
BOOL enableBg = TRUE;

// 初始化 GDI+
ULONG_PTR gdiplusToken;

// 是否初始化 GDI+
bool initGDI = false;

// 临时窗口
HWND tmp;

void InitializeGDIPlus() {
    Gdiplus::GdiplusStartupInput gdiplusStartupInput;
    Gdiplus::GdiplusStartup(&gdiplusToken, &gdiplusStartupInput, nullptr);
}

void ShutdownGDIPlus() {
    Gdiplus::GdiplusShutdown(gdiplusToken);
}

// 加载图像文件并返回 HBITMAP
HBITMAP LoadImageAndConvertToHBITMAP(const WCHAR* filePath) {
    if (!initGDI) {
        InitializeGDIPlus();
        initGDI = true;
    }
    Gdiplus::Bitmap bitmap(filePath);
    if (bitmap.GetLastStatus() != Gdiplus::Ok) {
        return nullptr;
    }
    HBITMAP hBitmap = nullptr;
    Gdiplus::Color color;
    bitmap.GetHBITMAP(color, &hBitmap);
    return hBitmap;
}

// 此代码模块中包含的函数的前向声明:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPWSTR    lpCmdLine,
                     _In_ int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    // 初始化全局字符串
    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_TASKBAR, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // 执行应用程序初始化:
    if (!InitInstance(hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_TASKBAR));

    MSG msg;

    // 主消息循环:
    while (GetMessage(&msg, nullptr, 0, 0))
    {
        if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    pTaskbar->Release();
    CoUninitialize();
    ShutdownGDIPlus();

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
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_TASKBAR));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszClassName  = szWindowClass;
    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

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

   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
       600, 300, 300, 200, nullptr, nullptr, hInstance, nullptr);

   tmp = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
       0, 0, 0, 0, nullptr, nullptr, hInstance, nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   CoInitialize(NULL);
   CoCreateInstance(CLSID_TaskbarList, NULL, CLSCTX_INPROC_SERVER, IID_PPV_ARGS(&pTaskbar));

   ShowWindow(hWnd, nCmdShow);
   // SW_HIDE 隐藏窗口
   ShowWindow(tmp, SW_HIDE);

   WCHAR tips[BTN_COUNT][4] = { L"上一首", L"暂停", L"下一首", L"喜欢" };
   int icons[BTN_COUNT] = { IDI_PREVIOUS, IDI_PLAY, IDI_NEXT, IDI_UNLIKE };

   for (int i = 0; i < BTN_COUNT; i++)
   {
       btns[i].dwMask = THB_BITMAP | THB_ICON | THB_FLAGS | THB_TOOLTIP;
       btns[i].iId = 1000 + i;
       btns[i].iBitmap = i;
       btns[i].hIcon = LoadIcon(hInstance, MAKEINTRESOURCE(icons[i]));
       btns[i].dwFlags = THBF_ENABLED;
       wcscpy_s(btns[i].szTip, tips[i]);
   }

   pTaskbar->ThumbBarAddButtons(hWnd, BTN_COUNT, btns);

   // 注册成组
   pTaskbar->RegisterTab(tmp, hWnd);
   pTaskbar->SetTabOrder(tmp, hWnd);

   UpdateWindow(hWnd);
   UpdateWindow(tmp);

   // 发送 WM_DWMSENDICONICTHUMBNAIL 信息避免第一次缩略图显示异常
   SendMessage(tmp, WM_DWMSENDICONICTHUMBNAIL, (WPARAM)tmp, 0);

   return TRUE;
}

// 设置实时预览图
void SetWindowLivePreview(HWND hwnd, HBITMAP hBitmap) {
    // 不显示原窗口的预览图, 这里设置负坐标
    POINT ptOffset;
    ptOffset.x = -1000;
    ptOffset.y = -2000;
    DwmSetIconicLivePreviewBitmap(hwnd, hBitmap, &ptOffset, 0);
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
    case WM_CREATE:
        // 开启自定义缩略图
        DwmSetWindowAttribute(hWnd, DWMWA_HAS_ICONIC_BITMAP, &enableBg, sizeof(BOOL));
        break;
    case WM_COMMAND:
        {
            int wmId = LOWORD(wParam);
            // 分析菜单选择:
            switch (wmId)
            {
                case 1000:
                    bgIndex = (bgIndex + BG_COUNT - 1) % BG_COUNT;
                    DwmSetIconicThumbnail(hWnd, LoadImageAndConvertToHBITMAP(bgImgs[bgIndex]), 0);
                    break;
                case 1001:
                    if (play) {
                        btns[1].hIcon = LoadIcon(hInst, MAKEINTRESOURCE(IDI_PAUSE));
                        wcscpy_s(btns[1].szTip, L"播放");
                    }
                    else {
                        btns[1].hIcon = LoadIcon(hInst, MAKEINTRESOURCE(IDI_PLAY));
                        wcscpy_s(btns[1].szTip, L"暂停");
                    }
                    play = !play;
                    // 更新按钮显示
                    pTaskbar->ThumbBarUpdateButtons(hWnd, BTN_COUNT, btns);
                    break;
                case 1002:
                    bgIndex = (bgIndex + 1) % BG_COUNT;
                    DwmSetIconicThumbnail(hWnd, LoadImageAndConvertToHBITMAP(bgImgs[bgIndex]), 0);
                    break;
                case 1003:
                    if (unlike) {
                        btns[3].hIcon = LoadIcon(hInst, MAKEINTRESOURCE(IDI_LIKE));
                        wcscpy_s(btns[3].szTip, L"取消喜欢");
                    }
                    else {
                        btns[3].hIcon = LoadIcon(hInst, MAKEINTRESOURCE(IDI_UNLIKE));
                        wcscpy_s(btns[3].szTip, L"喜欢");
                    }
                    unlike = !unlike;
                    // 更新按钮显示
                    pTaskbar->ThumbBarUpdateButtons(hWnd, BTN_COUNT, btns);
                    break;
                default:
                    return DefWindowProc(hWnd, message, wParam, lParam);
            }
        }
        break;
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
            RECT clientRect;
            GetClientRect(hWnd, &clientRect);
            const wchar_t* text = L"Hello, World!";
            SIZE textSize;
            GetTextExtentPoint32(hdc, text, wcslen(text), &textSize);
            int x = (clientRect.right - textSize.cx) / 2;
            int y = (clientRect.bottom - textSize.cy) / 2;
            TextOut(hdc, x, y, text, wcslen(text));
            EndPaint(hWnd, &ps);
        }
        break;
    case WM_DWMSENDICONICTHUMBNAIL:
        // 需要重新设置按钮, 否则无法正常显示
        pTaskbar->ThumbBarAddButtons(hWnd, BTN_COUNT, btns);
        pTaskbar->ThumbBarUpdateButtons(hWnd, BTN_COUNT, btns);
        DwmSetWindowAttribute(hWnd, DWMWA_FORCE_ICONIC_REPRESENTATION, &enableBg, sizeof(BOOL));
        DwmInvalidateIconicBitmaps(hWnd);
        DwmSetIconicThumbnail(hWnd, LoadImageAndConvertToHBITMAP(bgImgs[bgIndex]), 0);
        break;
    case WM_DWMSENDICONICLIVEPREVIEWBITMAP:
        SetWindowLivePreview(hWnd, LoadImageAndConvertToHBITMAP(bgImgs[bgIndex]));
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}
