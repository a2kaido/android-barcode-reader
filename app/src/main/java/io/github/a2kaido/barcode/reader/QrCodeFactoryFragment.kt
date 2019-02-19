package io.github.a2kaido.barcode.reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_qr_code_factory.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.EnumMap

class QrCodeFactoryFragment : Fragment() {

    private val viewModel: QrCodeFactoryViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qr_code_factory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        requireNotNull(args)
        code.text = QrCodeFactoryFragmentArgs.fromBundle(args).value

        viewModel.barcode.observe(this, Observer {
            val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
            hints[EncodeHintType.MARGIN] = 4

            val encoder = BarcodeEncoder()
            val bitmap = encoder.encodeBitmap(it, BarcodeFormat.QR_CODE, qr_code.width, qr_code.height, hints)
            qr_code.setImageBitmap(bitmap)
        })

        viewModel.setBarcode(code.text.toString())
    }
}