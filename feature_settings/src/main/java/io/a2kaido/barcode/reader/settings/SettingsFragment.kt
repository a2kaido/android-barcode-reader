package io.a2kaido.barcode.reader.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import io.a2kaido.barcode.reader.BuildConfig
import io.a2kaido.barcode.reader.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().title = getString(R.string.settings_title)

        val versionName = requireContext().packageManager.getPackageInfo(
            requireContext().packageName,
            0,
        ).versionName

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    SettingsScreen(
                        versionName = versionName,
                        onClickOssLicense = {
                            OssLicensesMenuIntentBuilder.buildIntent?.invoke(
                                requireContext(),
                                getString(R.string.oss_license)
                            )?.run {
                                startActivity(this)
                            }
                        },
                        onClickPrivacyPolicy = {
                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = "https://barcode-capture.web.app/".toUri()
                            })
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(
    versionName: String,
    onClickOssLicense: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFF008577)),
                painter = painterResource(R.drawable.ic_settings_mark),
                contentDescription = null,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text("version: $versionName")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .clickable(
                    onClick = onClickOssLicense,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .padding(vertical = 16.dp),
                painter = painterResource(R.drawable.ic_description),
                contentDescription = null,
                tint = Color(0xFF008577),
            )
            Text(stringResource(R.string.oss_license))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = onClickPrivacyPolicy,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp)
                    .padding(vertical = 16.dp),
                painter = painterResource(R.drawable.ic_description),
                contentDescription = null,
                tint = Color(0xFF008577),
            )
            Text(stringResource(R.string.privacy_policy))
        }
    }
}
