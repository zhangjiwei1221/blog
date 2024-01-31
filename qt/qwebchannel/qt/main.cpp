#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include "WebChannelServer.h"

int main(int argc, char *argv[])
{

    QGuiApplication app(argc, argv);

    QQmlApplicationEngine engine;
    engine.load(QUrl(QStringLiteral("qrc:/qt/qml/qt/main.qml")));
    if (engine.rootObjects().isEmpty())
        return -1;

    WebChannelServer webChannelServer;

    return app.exec();
}
