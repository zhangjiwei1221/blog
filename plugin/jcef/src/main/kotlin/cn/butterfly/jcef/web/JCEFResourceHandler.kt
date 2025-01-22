package cn.butterfly.jcef.web

import org.cef.callback.CefCallback
import org.cef.handler.CefResourceHandler
import org.cef.misc.IntRef
import org.cef.misc.StringRef
import org.cef.network.CefRequest
import org.cef.network.CefResponse

/**
 * 资源处理
 *
 * @author zjw
 * @date 2025-01-21
 */
class JCEFResourceHandler : CefResourceHandler {

    private var connection: JCEFConnection? = null

    override fun processRequest(cefRequest: CefRequest, callback: CefCallback): Boolean {
        val url = cefRequest.url
        // 拦截本地文件请求并处理
        if (url.startsWith("http://butterfly")) {
            val resource = javaClass.classLoader.getResource(url.replace("http://butterfly", "static")) ?: return false
            connection = JCEFConnection(resource.toURI().toURL().openConnection())
            callback.Continue()
            return true
        }
        return false
    }

    override fun getResponseHeaders(cefResponse: CefResponse, responseLength: IntRef, redirectUrl: StringRef?) {
        connection?.getResponseHeaders(cefResponse, responseLength)
    }

    override fun readResponse(dataOut: ByteArray, dataSize: Int, bytesRead: IntRef, callback: CefCallback?): Boolean {
        return connection?.readResponse(dataOut, dataSize, bytesRead) ?: false
    }

    override fun cancel() {
        connection?.close()
    }

}