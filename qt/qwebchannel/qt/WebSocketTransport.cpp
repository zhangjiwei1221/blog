#include "WebSocketTransport.h"
#include <QJsonDocument>
#include <QJsonObject>
#include <QWebSocket>

WebSocketTransport::WebSocketTransport(QWebSocket* socket)
    : QWebChannelAbstractTransport(socket), m_socket(socket)
{
    connect(socket, &QWebSocket::textMessageReceived, this, &WebSocketTransport::textMessageReceived);
    connect(socket, &QWebSocket::disconnected, this, &WebSocketTransport::deleteLater);
}

WebSocketTransport::~WebSocketTransport()
{
    m_socket->deleteLater();
}

void WebSocketTransport::sendMessage(const QJsonObject& message)
{
    QJsonDocument doc(message);
    QString sendData = QString::fromUtf8(doc.toJson(QJsonDocument::Compact));
    m_socket->sendTextMessage(sendData);
}

void WebSocketTransport::textMessageReceived(const QString& messageData)
{
    QJsonParseError error;
    QJsonDocument message = QJsonDocument::fromJson(messageData.toUtf8(), &error);
    if (error.error) {
        return;
    }
    else if (!message.isObject())
    {
        return;
    }
    emit messageReceived(message.object(), this);
}
