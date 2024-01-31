#pragma once
#include "WebChannelObj.h"
#include "WebSocketTransport.h"
#include <QObject>
#include <QWebChannel>
#include <QWebSocketServer>

class WebChannelServer : public QObject
{
    Q_OBJECT
public:
    explicit WebChannelServer();

    /// <summary>
    /// 启动 websocket 服务
    /// </summary>
    void startServer();

private Q_SLOTS:

    /// <summary>
    /// 处理新连接
    /// </summary>
    void onNewConnection();

private:

    QWebChannel* m_webChannel;

    QWebSocketServer* m_websocketServer;

};


