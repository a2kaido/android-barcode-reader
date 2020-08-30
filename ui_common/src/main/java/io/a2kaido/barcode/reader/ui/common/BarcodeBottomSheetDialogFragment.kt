package io.a2kaido.barcode.reader.ui.common

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.a2kaido.barcode.reader.ui.common.databinding.BottomSheetDialogBarcodeBinding
import io.github.a2kaido.barcode.reader.domain.model.BarcodeData
import io.github.a2kaido.barcode.reader.domain.model.EMVCoBarcode
import io.github.a2kaido.barcode.reader.domain.model.EMVCoCPMBarcode
import io.github.a2kaido.barcode.reader.domain.model.RawDataBarcode
import io.github.a2kaido.barcode.reader.domain.model.UrlBarcode
import io.github.a2kaido.barcode.reader.domain.model.WifiBarcode
import io.github.a2kaido.emvco.cpm.EMVCoCpmDecoder
import io.github.a2kaido.emvco.cpm.EMVCoCpmParser
import io.github.a2kaido.emvco.parseEMVCoText

class BarcodeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private enum class Args {
        BARCODE
    }

    private val parser: EMVCoCpmParser by lazy {
        EMVCoCpmParser()
    }

    private val decoder: EMVCoCpmDecoder by lazy {
        EMVCoCpmDecoder()
    }

    companion object {
        fun newInstance(barcode: BarcodeData) = BarcodeBottomSheetDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Args.BARCODE.name, barcode)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val barcode = requireNotNull(arguments?.getSerializable(Args.BARCODE.name) as? BarcodeData)

        return BottomSheetDialog(requireContext()).apply {
            val binding = BottomSheetDialogBarcodeBinding.inflate(requireActivity().layoutInflater)
            setContentView(binding.root)

            binding.bottomSheetBarcodeType.text = when (barcode) {
                is UrlBarcode -> getString(R.string.barcode_type_url)
                is RawDataBarcode -> getString(R.string.barcode_type_raw)
                is WifiBarcode -> getString(R.string.barcode_type_wifi)
                is EMVCoBarcode -> getString(R.string.barcode_type_emvco_mpm)
                is EMVCoCPMBarcode -> getString(R.string.barcode_type_emvco_cpm)
            }
            binding.bottomSheetBarcodeFormat.text = barcode.format.name
            binding.bottomSheetText.text = barcode.code
            when (barcode) {
                is UrlBarcode -> {
                    binding.bottomSheetPositiveButton.visibility = View.VISIBLE
                    binding.bottomSheetPositiveButton.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(barcode.code)
                        })
                        dismiss()
                    }
                }
                is RawDataBarcode -> {
                    binding.bottomSheetPositiveButton.visibility = View.GONE
                }
                is WifiBarcode -> {
                    binding.bottomSheetPositiveButton.visibility = View.GONE
                }
                is EMVCoBarcode -> {
                    binding.bottomSheetPositiveButton.visibility = View.GONE
                    binding.bottomSheetText.text = parseEMVCoText(barcode.code)
                }
                is EMVCoCPMBarcode -> {
                    binding.bottomSheetPositiveButton.visibility = View.GONE

                    val result = parser.parse(decoder.decode(barcode.code))
                    val stringBuilder = StringBuilder()
                    result.forEach {
                        stringBuilder.append(it.getText())
                    }

                    binding.bottomSheetText.text = stringBuilder.toString()
                }
            }
            binding.share.setOnClickListener {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, binding.bottomSheetText.text.toString())
                sendIntent.type = "text/plain"
                startActivity(Intent.createChooser(sendIntent, "Share Code"))
            }
            binding.bottomSheetNegativeButton.setOnClickListener {
                dismiss()
            }
        }
    }
}