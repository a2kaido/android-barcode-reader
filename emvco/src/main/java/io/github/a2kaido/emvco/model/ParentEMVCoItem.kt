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
        appendChildren(stringBuilder)

        return stringBuilder.toString()
    }

    private fun appendChildren(stringBuilder: StringBuilder, isGrandChild: Boolean = false) {
        children.forEachIndexed { index, item ->
            if (index == 0) {
                stringBuilder.append("${item.id} ${item.length} ${item.payload}\n")
            } else {
                if (item is ParentEMVCoItem) {
                    stringBuilder.append("            ${item.id} ${item.length}\n                        ")
                    item.appendChildren(stringBuilder, true)
                } else {
                    if (isGrandChild) {
                        stringBuilder.append("            ")
                    }
                    stringBuilder.append("            ${item.id} ${item.length} ${item.payload}\n")
                }
            }
        }
    }
}