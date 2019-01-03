package io.github.a2kaido.barcode.reader.domain.model

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class) // robolectric for Uri.parse
class BarcodeTest {

    @Test
    fun barcodeFactory_create_url() {
        val data = BarcodeFactory.create("http://www.example.com/", BarcodeFormat.CODE_128)
        assertTrue(data is UrlBarcode)
    }

    @Test
    fun barcodeFactory_create_raw_data() {
        val data = BarcodeFactory.create("raw data", BarcodeFormat.CODE_128)
        assertTrue(data is RawDataBarcode)
    }
}