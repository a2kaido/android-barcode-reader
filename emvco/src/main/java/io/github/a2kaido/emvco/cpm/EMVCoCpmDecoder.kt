package io.github.a2kaido.emvco.cpm

import android.util.Base64
import android.util.Base64.DEFAULT

class EMVCoCpmDecoder {

    fun decode(str: String): String = Base64.decode(str, DEFAULT).joinToString(separator = "") {
        String.format("%02X", it)
    }
}
