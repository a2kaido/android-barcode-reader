package io.github.a2kaido.barcode.reader

class Event<out T>(private val content: T) {

    var consumed = false

    fun consume(): T? = if (consumed) {
        null
    } else {
        consumed = true
        content
    }
}