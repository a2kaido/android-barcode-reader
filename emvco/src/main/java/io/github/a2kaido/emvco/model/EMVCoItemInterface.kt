package io.github.a2kaido.emvco.model

interface EMVCoItemInterface {
    val id: String
    val length: String
    val payload: String

    fun getText(): String
}
