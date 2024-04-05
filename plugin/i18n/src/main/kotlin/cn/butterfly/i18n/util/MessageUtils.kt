package cn.butterfly.i18n.util

import com.intellij.DynamicBundle
import org.jetbrains.annotations.PropertyKey

/**
 * 信息工具类
 *
 * @author zjw
 * @date 2024-04-03
 */
private const val BUNDLE = "messages.ActionBundles"

object MessageUtils: DynamicBundle(BUNDLE) {
    
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) = getMessage(key, *params)
    
}