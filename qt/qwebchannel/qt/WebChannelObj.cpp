#include "WebChannelObj.h"
#include <QHostAddress>
#include <QJsonObject>
#include <QFileSystemWatcher>
#include <QDir>
#include <QJsonArray>
#include <QFileInfoList>

WebChannelObj* WebChannelObj::obj = new WebChannelObj;

QString dirPath = "D:\\code\\temp\\";

WebChannelObj* WebChannelObj::getInstance()
{
	QFileSystemWatcher* m_FileWatcher = new QFileSystemWatcher;
	m_FileWatcher->addPath(dirPath);
	connect(m_FileWatcher, &QFileSystemWatcher::directoryChanged, obj, &WebChannelObj::directoryChanged);
	return obj;
}

QJsonDocument WebChannelObj::getInfo()
{
	return dirInfo(dirPath);
}

void WebChannelObj::directoryChanged(const QString& path)
{
	emit infoChanged(dirInfo(path));
}

QJsonDocument WebChannelObj::dirInfo(const QString& path)
{
	QDir dir(path);
	dir.setFilter(QDir::AllEntries | QDir::NoDotAndDotDot);
	QFileInfoList list = dir.entryInfoList();
	QJsonArray jsonArray;
	for (auto& file : list)
	{
		QJsonObject obj;
		obj["isDir"] = file.isDir();
		obj["fileName"] = file.fileName();
		obj["fileSize"] = file.size();
		obj["lastModified"] = file.lastModified().toMSecsSinceEpoch();
		jsonArray.append(obj);
	}
	QJsonDocument doc(jsonArray);
	return doc;
}