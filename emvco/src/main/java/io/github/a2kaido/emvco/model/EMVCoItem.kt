package io.github.a2kaido.emvco.model

data class EMVCoItem(
    override val id: String,
    override val length: String,
    override val payload: String
) : EMVCoItemInterface {
    override fun getText(): String = "$id $length $payload\n"
}
