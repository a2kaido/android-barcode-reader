package io.github.a2kaido.barcode.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData
import io.github.a2kaido.barcode.reader.domain.model.BarcodeFactory
import io.github.a2kaido.barcode.reader.domain.model.BarcodeFormat
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class HomeViewModel(
    private val coroutineContextPrefix: CoroutineContext = Dispatchers.Main
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = coroutineContextPrefix + job

    private val _barcodeCreated: MutableLiveData<Event<BarcodeData>> = MutableLiveData()
    val barcodeCreated: LiveData<Event<BarcodeData>>
        get() = _barcodeCreated

    fun scannedBarcode(text: String, format: BarcodeFormat) {
        launch {
            val barcode = withContext(Dispatchers.Default) {
                BarcodeFactory.create(text, format)
            }

            _barcodeCreated.value = Event(barcode)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancelChildren()
    }
}