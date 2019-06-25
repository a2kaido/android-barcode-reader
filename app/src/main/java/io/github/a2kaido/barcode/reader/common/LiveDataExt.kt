package io.github.a2kaido.barcode.reader.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, callback: (T) -> Unit) {
    observe(owner, androidx.lifecycle.Observer<T> {
        it?.let { nonNull ->
            callback.invoke(nonNull)
        }
    })
}