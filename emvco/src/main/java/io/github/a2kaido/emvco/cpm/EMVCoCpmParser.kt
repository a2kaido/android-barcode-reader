package io.github.a2kaido.emvco.cpm

import io.github.a2kaido.emvco.cpm.model.CpmTag
import io.github.a2kaido.emvco.model.EMVCoItem
import io.github.a2kaido.emvco.model.EMVCoItemInterface
import io.github.a2kaido.emvco.model.ParentEMVCoItem
import io.github.a2kaido.emvco.parseEMVCo

class EMVCoCpmParser {

    fun parse(str: String, isChild: Boolean = false): List<EMVCoItemInterface> {
        var tmpStr: String? = str

        val ret = arrayListOf<EMVCoItemInterface>()

        while (!tmpStr.isNullOrEmpty()) {
            val (item, cutStr) = cutItem(tmpStr, isChild)
            ret.add(item)
            tmpStr = cutStr
        }

        return ret
    }

    private fun cutItem(str: String, isChild: Boolean): Pair<EMVCoItemInterface, String> {
        val cpmTag = CpmTag.values().first {
            str.startsWith(it.tag)
        }

        val id = cpmTag.tag
        val length = str.substring(cpmTag.tag.length, cpmTag.tag.length + 2)
        val payloadLength = length.toInt(16) * 2

        val payload =
            str.substring(cpmTag.tag.length + 2, cpmTag.tag.length + 2 + payloadLength)

        if (isChild && id != "64") {
            return EMVCoItem(
                id,
                length,
                payload
            ) to str.substring(payloadLength + cpmTag.tag.length + 2)
        }

        if (id == "61" || id == "62" || id == "64") {
            try {
                return ParentEMVCoItem(
                    id,
                    length,
                    payload,
                    parse(payload, true)
                ) to str.substring(payloadLength + cpmTag.tag.length + 2)
            } catch (e: Exception) {
            }
        }

        return EMVCoItem(
            id,
            length,
            payload
        ) to str.substring(payloadLength + cpmTag.tag.length + 2)
    }
}
