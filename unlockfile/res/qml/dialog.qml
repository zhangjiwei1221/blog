import QtQuick 2.9
import QtQuick.Window 2.2

Window {
    id: w
    visible: true
    width: 480
    height: 200
    title: "unlockfile"

    property bool run: true
    property int count: 0

    function showFile(fileText) {
        file.text = fileText
        run = false
    }

    Text {
        id: file
        anchors.fill: parent
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        text: "查找中"
    }

    Timer {
        interval: 1000
        running: run
        repeat: true
        onTriggered: {
            let str = ""
            for (let i = 0; i < count; i++) {
                str += "."
            }
            file.text = "查找中" + str
            count = (count + 1) % 4
        }
    }
}
