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
import io.github.a2kaido.barcode.reader.databinding.FragmentQrCodeFactoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.EnumMap

class QrCodeFactoryFragment : Fragment() {

    private var _binding: FragmentQrCodeFactoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QrCodeFactoryViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentQrCodeFactoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        requireNotNull(args)
        binding.code.text = QrCodeFactoryFragmentArgs.fromBundle(args).value

        viewModel.barcode.observe(this, Observer {
            val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
            hints[EncodeHintType.MARGIN] = 4

            val encoder = BarcodeEncoder()
            val bitmap = encoder.encodeBitmap(it, BarcodeFormat.QR_CODE, binding.qrCode.width, binding.qrCode.height, hints)
            binding.qrCode.setImageBitmap(bitmap)
        })

        viewModel.setBarcode(binding.code.text.toString())
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
}