#include "WebChannelServer.h"
#include <QJsonObject>

WebChannelServer::WebChannelServer()
{
    m_webChannel = new QWebChannel(this);
    m_webChannel->registerObject("server", WebChannelObj::getInstance());
    startServer();
}

void WebChannelServer::startServer()
{
    m_websocketServer = new QWebSocketServer("xshouseWebchannel", QWebSocketServer::NonSecureMode, this);
    if (m_websocketServer->listen(QHostAddress::Any, 9999))
    {
        connect(m_websocketServer, &QWebSocketServer::newConnection, this, &WebChannelServer::onNewConnection);
    }
}

void WebChannelServer::onNewConnection()
{
    QWebSocket* client = m_websocketServer->nextPendingConnection();
    m_webChannel->connectTo(new WebSocketTransport(client));
}

