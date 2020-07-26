package io.github.a2kaido.emvco.cpm

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EMVCoCpmDecoderTest {

    @Test
    fun `decode EMVCo CPM`() {
        assertEquals("85054350563031611A4F07A0000000555555570F1234567890123458D191220112345F", EMVCoCpmDecoder().decode("hQVDUFYwMWEaTwegAAAAVVVVVw8SNFZ4kBI0WNGRIgESNF8="))
    }
}
