package cn.butterfly.jcef.web

import org.cef.misc.IntRef
import org.cef.network.CefResponse
import java.net.URLConnection

/**
 * 资源连接处理
 *
 * @author zjw
 * @date 2025-01-21
 */
class JCEFConnection(private var connection: URLConnection) {

    private var inputStream = connection.getInputStream()

    fun getResponseHeaders(cefResponse: CefResponse, responseLength: IntRef) {
        // 设置响应头信息
        cefResponse.mimeType = connection.contentType
        responseLength.set(inputStream.available())
        cefResponse.status = 200
    }

    fun readResponse(dataOut: ByteArray, dataSize: Int, bytesRead: IntRef): Boolean {
        // 读取响应数据
        inputStream.use {
            val available = it.available()
            if (available > 0) {
                bytesRead.set(it.read(dataOut, 0, available.coerceAtMost(dataSize)))
                return true
            }
        }
        return false
    }

    fun close() {
        // 关闭流
        inputStream.close()
    }

}