package io.github.a2kaido.emvco.cpm

import org.junit.Test

class EMVCoCpmParserTest {

    @Test
    fun `parse cpm emvco`() {
        val result = EMVCoCpmParser().parse("8505435056303161134F07A0000000555555500850726F647563743161134F07A0000000666666500850726F647563743262495A0812345678901234585F200E43415244484F4C4445522F454D565F2D08727565736465656E64219F100706010A030000009F2608584FD385FA234BCC9F360200019F37046D58EF13")
        result.forEach {
            println(it.getText())
        }
    }

    @Test
    fun `parse non cpm emvco`() {
        val result = EMVCoCpmParser().parse("ほげ")
        println(result)
    }
}