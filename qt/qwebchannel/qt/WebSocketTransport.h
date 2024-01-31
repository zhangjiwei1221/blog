#pragma once
#include <QWebChannelAbstractTransport>

QT_BEGIN_NAMESPACE
class QWebSocket;
QT_END_NAMESPACE

class WebSocketTransport : public QWebChannelAbstractTransport
{
    Q_OBJECT
public:
    explicit WebSocketTransport(QWebSocket* socket);

    virtual ~WebSocketTransport();

    /// <summary>
    /// 发送消息
    /// </summary>
    /// <param name="message">消息</param>
    void sendMessage(const QJsonObject& message) override;

private slots:

    /// <summary>
    /// 处理文本接收事件
    /// </summary>
    void textMessageReceived(const QString& message);

private:
    QWebSocket* m_socket;
};

