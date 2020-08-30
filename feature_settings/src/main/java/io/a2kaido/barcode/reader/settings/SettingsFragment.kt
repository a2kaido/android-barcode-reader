package io.a2kaido.barcode.reader.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.a2kaido.barcode.reader.R
import io.a2kaido.barcode.reader.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

//    private val manager: ReviewManager by lazy {
//        ReviewManagerFactory.create(requireContext())
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.settings_title)

        binding.ossLicense.setOnClickListener {
            OssLicensesMenuIntentBuilder.buildIntent?.invoke(requireContext(), getString(R.string.oss_license))?.run {
                startActivity(this)
            }
        }

//        review.setOnClickListener {
//            val flow = manager.requestReviewFlow()
//            flow.addOnCompleteListener { request ->
//                if (request.isSuccessful) {
//                    val reviewInfo = request.result
//                    val reviewFlow = manager.launchReviewFlow(requireActivity(), reviewInfo)
//                    reviewFlow.addOnCompleteListener {
//                        it.exception?.printStackTrace()
//                    }
//                }
//            }
//        }
    }
}