package io.github.a2kaido.barcode.reader

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import io.github.a2kaido.barcode.reader.mapper.toDomain
import kotlinx.android.synthetic.main.bottom_sheet_dialog_barcode.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.barcodeCreated.observe(this, Observer {
            it?.consume()?.let { barcode ->
                val dialog = BottomSheetDialog(requireContext()).apply {
                    setContentView(R.layout.bottom_sheet_dialog_barcode)
                    bottom_sheet_text.text = barcode.code
                    bottom_sheet_positive_button.setOnClickListener {
                        dismiss()
                    }
                    bottom_sheet_negative_button.setOnClickListener {
                        dismiss()
                    }
                    setOnDismissListener {
                        viewModel.dismissBarcodeBottomSheetDialog()
                    }
                }
                dialog.show()
            }
        })

        decorated_barcode_view.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let { barcodeResult ->
                    decorated_barcode_view?.pause()
                    viewModel.scannedBarcode(barcodeResult.text, barcodeResult.barcodeFormat.toDomain())
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
                // nothing to do now.
            }
        })

        viewModel.dismissBarcodeBottomSheetDialog.observe(this, Observer {
            it?.consume()?.let {
                resumeCameraWithPermissionCheck()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        resumeCameraWithPermissionCheck()
    }

    override fun onPause() {
        super.onPause()
        decorated_barcode_view.pause()
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun resumeCamera() {
        decorated_barcode_view.visibility = View.VISIBLE
        decorated_barcode_view.resume()
        notification_text.visibility = View.GONE
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        decorated_barcode_view.visibility = View.GONE
        notification_text.visibility = View.VISIBLE
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        decorated_barcode_view.visibility = View.GONE
        notification_text.visibility = View.VISIBLE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}
