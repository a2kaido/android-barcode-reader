package io.a2kaido.barcode.reader.ui.common

class Event<out T>(private val content: T) {

    var consumed = false

    fun consume(): T? = if (consumed) {
        null
    } else {
        consumed = true
        content
    }
}