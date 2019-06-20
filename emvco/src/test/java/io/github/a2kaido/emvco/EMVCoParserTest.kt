package io.github.a2kaido.emvco

import org.junit.Test

class EMVCoParserTest {

    @Test
    fun test_parseEMVCo() {
        val res = parseEMVCo("000201010212")
        res.forEach { item ->
            println(item.getText())
        }
    }
}