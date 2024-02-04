package core.caching

import kmp.observer.KVObserverProtocol
import kotlinx.cinterop.COpaquePointer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import platform.Foundation.NSKeyValueObservingOptionNew
import platform.Foundation.NSUserDefaults
import platform.Foundation.addObserver
import platform.Foundation.removeObserver
import platform.darwin.NSObject

class Listener(private val key: String, private val default: Any) : NSObject(), KVObserverProtocol {

    private val flow = MutableStateFlow(default)
    val stream = flow.onStart {
        NSUserDefaults.standardUserDefaults.addObserver(
            observer = this@Listener,
            forKeyPath = key,
            options = NSKeyValueObservingOptionNew,
            context = null
        )
    }.onCompletion {
        NSUserDefaults.standardUserDefaults.removeObserver(this@Listener, forKeyPath = key)
    }

    override fun observeValueForKeyPath(
        keyPath: String?,
        ofObject: Any?,
        change: Map<Any?, *>?,
        context: COpaquePointer?
    ) {
        change?.get("new").let { newChange -> flow.update { newChange ?: default } }
    }
}