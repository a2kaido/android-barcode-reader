package io.github.a2kaido.emvco.model

data class ParentEMVCoItem(
    override val id: String,
    override val length: String,
    override val payload: String,
    val children: List<EMVCoItemInterface>
) : EMVCoItemInterface {

    override fun getText(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append("$id $length ")
        children.forEachIndexed { index, item ->
            if (index == 0) {
                stringBuilder.append("${item.id} ${item.length} ${item.payload}\n")
            } else {
                stringBuilder.append("            ${item.id} ${item.length} ${item.payload}\n")
            }
        }

        return stringBuilder.toString()
    }
}