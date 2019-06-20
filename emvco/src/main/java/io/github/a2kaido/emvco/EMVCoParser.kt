package io.github.a2kaido.emvco

import io.github.a2kaido.emvco.model.EMVCoItem
import io.github.a2kaido.emvco.model.EMVCoItemInterface
import io.github.a2kaido.emvco.model.ParentEMVCoItem

fun parseEMVCo(str: String): List<EMVCoItemInterface> {
    var tmpStr: String? = str

    val ret = arrayListOf<EMVCoItemInterface>()

    while (!tmpStr.isNullOrEmpty()) {
        val (item, cutStr) = cutEMVCoItem(tmpStr)
        ret.add(item)
        tmpStr = cutStr
    }

    return ret
}

fun parseEMVCoText(str: String): String {
    val stringBuilder = StringBuilder()

    parseEMVCo(str).forEach {
        stringBuilder.append(it.getText())
    }

    return stringBuilder.toString()
}

private fun cutEMVCoItem(str: String): Pair<EMVCoItemInterface, String> {
    val id = str.substring(0, 2)
    val length = str.substring(2, 4)
    val payloadLength = length.toInt()

    val payload = str.substring(4, payloadLength + 4)

    if (id == "62" || id == "64") {
        return ParentEMVCoItem(
            id,
            length,
            payload,
            parseEMVCo(payload)
        ) to str.substring(payloadLength + 4)
    }

    return EMVCoItem(
        id,
        length,
        payload
    ) to str.substring(payloadLength + 4)
}
