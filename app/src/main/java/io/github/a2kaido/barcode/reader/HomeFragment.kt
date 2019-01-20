package io.github.a2kaido.barcode.reader

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import io.github.a2kaido.barcode.reader.domain.model.RawDataBarcode
import io.github.a2kaido.barcode.reader.domain.model.UrlBarcode
import io.github.a2kaido.barcode.reader.domain.model.WifiBarcode
import io.github.a2kaido.barcode.reader.mapper.toDomain
import kotlinx.android.synthetic.main.bottom_sheet_dialog_barcode.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.merge_no_camera_permission_view.*
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

        requireActivity().title = getString(R.string.scan_title)

        open_settings.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            }
            startActivity(intent)
        }

        viewModel.barcodeCreated.observe(this, Observer {
            it?.consume()?.let { barcode ->
                val dialog = BottomSheetDialog(requireContext()).apply {
                    setContentView(R.layout.bottom_sheet_dialog_barcode)
                    bottom_sheet_barcode_type.text = when (barcode) {
                        is UrlBarcode -> getString(R.string.barcode_type_url)
                        is RawDataBarcode -> getString(R.string.barcode_type_raw)
                        is WifiBarcode -> getString(R.string.barcode_type_wifi)
                    }
                    bottom_sheet_barcode_format.text = barcode.format.name
                    bottom_sheet_text.text = barcode.code
                    when (barcode) {
                        is UrlBarcode -> {
                            bottom_sheet_positive_button.visibility = View.VISIBLE
                            bottom_sheet_positive_button.setOnClickListener {
                                startActivity(Intent(ACTION_VIEW).apply {
                                    data = Uri.parse(barcode.code)
                                })
                                dismiss()
                            }
                        }
                        is RawDataBarcode -> {
                            bottom_sheet_positive_button.visibility = View.GONE
                        }
                        is WifiBarcode -> {
                            bottom_sheet_positive_button.visibility = View.GONE
                        }
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
        no_camera_permission.visibility = View.GONE
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        decorated_barcode_view.visibility = View.GONE
        no_camera_permission.visibility = View.VISIBLE
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        decorated_barcode_view.visibility = View.GONE
        no_camera_permission.visibility = View.VISIBLE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}
