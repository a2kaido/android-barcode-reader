package io.github.a2kaido.barcode.reader

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import io.a2kaido.barcode.reader.ui.common.BarcodeBottomSheetDialogFragment
import io.github.a2kaido.barcode.reader.databinding.FragmentHomeBinding
import io.github.a2kaido.barcode.reader.mapper.toDomain
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.PermissionUtils
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.scan_title)

        binding.noCameraPermission.openSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            }
            startActivity(intent)
        }

        viewModel.barcodeCreated.observe(viewLifecycleOwner) {
            it?.consume()?.let { barcode ->
                BarcodeBottomSheetDialogFragment.newInstance(barcode)
                    .show(parentFragmentManager, null)
            }
        }

        binding.decoratedBarcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let { barcodeResult ->
                    _binding?.decoratedBarcodeView?.pause()
                    viewModel.scannedBarcode(
                        barcodeResult.text,
                        barcodeResult.barcodeFormat.toDomain()
                    )
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

    override fun onResume() {
        super.onResume()
        if (PermissionUtils.hasSelfPermissions(requireActivity(), "android.permission.CAMERA")) {
            resumeCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.decoratedBarcodeView.pause()
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun resumeCamera() {
        binding.decoratedBarcodeView.visibility = View.VISIBLE
        binding.decoratedBarcodeView.resume()
        binding.noCameraPermission.root.visibility = View.GONE
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        binding.decoratedBarcodeView.visibility = View.GONE
        binding.noCameraPermission.root.visibility = View.VISIBLE
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        binding.decoratedBarcodeView.visibility = View.GONE
        binding.noCameraPermission.root.visibility = View.VISIBLE
    }

    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}
