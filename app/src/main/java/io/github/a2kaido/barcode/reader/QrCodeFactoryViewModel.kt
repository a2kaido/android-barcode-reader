package io.github.a2kaido.barcode.reader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QrCodeFactoryViewModel : ViewModel() {

    private val _barcode: MutableLiveData<String> = MutableLiveData()
    val barcode: LiveData<String>
        get() = _barcode
}
