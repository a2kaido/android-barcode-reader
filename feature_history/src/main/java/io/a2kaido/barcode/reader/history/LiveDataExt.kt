package io.a2kaido.barcode.reader.history

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, callback: (T) -> Unit) {
    observe(owner) {
        it?.let { nonNull ->
            callback.invoke(nonNull)
        }
    }
}
