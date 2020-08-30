package io.github.a2kaido.barcode.reader

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

//    private val manager: ReviewManager by lazy {
//        ReviewManagerFactory.create(requireContext())
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.settings_title)

        oss_license.setOnClickListener {
            startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java).apply {
                putExtra("title", getString(R.string.oss_license))
            })
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