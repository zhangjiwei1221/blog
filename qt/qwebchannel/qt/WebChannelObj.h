#pragma once
#include <QObject>
#include <QTcpSocket>
#include <QJsonObject>
#include <QJsonDocument>

class WebChannelObj : public QObject
{
    Q_OBJECT

public:

    static WebChannelObj* getInstance();

    /// <summary>
    /// 获取目录信息
    /// </summary>
    /// <param name="path">路径</param>
    /// <returns>文件信息</returns>
    QJsonDocument dirInfo(const QString& path);

signals:

    /// <summary>
    /// 信息变更事件
    /// </summary>
    /// <param name="info">文件信息</param>
    void infoChanged(const QJsonDocument& info);

public slots:

    /// <summary>
    /// 获取文件信息
    /// </summary>
    /// <returns>转为 JSON 格式的文件信息</returns>
    QJsonDocument getInfo();

    /// <summary>
    /// 目录变更事件
    /// </summary>
    /// <param name="path">监听目录路径</param>
    void directoryChanged(const QString& path);

private:

    WebChannelObj() {}

    static WebChannelObj* obj;

};