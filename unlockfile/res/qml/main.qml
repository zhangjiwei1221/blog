import QtQuick 2.9
import QtQuick.Window 2.2

Window {
    id: w
    visible: true
    width: 320
    height: 120
    title: "unlockfile"

    function showInfo(infoText) {
        info.text = infoText
    }

    Text {
        id: info
        anchors.fill: parent
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        text: "Enjoy!"
    }
}
