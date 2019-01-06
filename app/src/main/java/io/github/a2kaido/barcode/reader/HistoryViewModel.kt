package io.github.a2kaido.barcode.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.a2kaido.barcode.reader.domain.BarcodeUseCaseInterface
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HistoryViewModel(
    private val barcodeUseCase: BarcodeUseCaseInterface,
    private val coroutineContextPrefix: CoroutineContext = Dispatchers.Main
) : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = coroutineContextPrefix + job

    private val _barcodeList = MutableLiveData<List<BarcodeData>>()
    val barcodeList: LiveData<List<BarcodeData>>
        get() = _barcodeList

    private val _onClickHistoryItemEvent = MutableLiveData<Event<BarcodeData>>()
    val onClickHistoryItemEvent: LiveData<Event<BarcodeData>>
        get() = _onClickHistoryItemEvent

    private val _onLongClickHistoryItemEvent = MutableLiveData<Event<BarcodeData>>()
    val onLongClickHistoryItemEvent: LiveData<Event<BarcodeData>>
        get() = _onLongClickHistoryItemEvent

    fun fetchBarcodes() {
        launch {
            _barcodeList.value = withContext(Dispatchers.Default) {
                barcodeUseCase.getBarcodes()
            }
        }
    }

    fun deleteBarcode(barcode: BarcodeData) {
        launch {
            withContext(Dispatchers.Default) {
                barcodeUseCase.deleteBarcode(barcode)
            }
        }
    }

    fun onClickHistoryItem(barcode: BarcodeData) {
        _onClickHistoryItemEvent.value = Event(barcode)
    }

    fun onLongClickHistoryItem(barcode: BarcodeData) {
        _onLongClickHistoryItemEvent.value = Event(barcode)
    }
}