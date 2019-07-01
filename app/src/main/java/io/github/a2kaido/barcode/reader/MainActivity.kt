package io.github.a2kaido.barcode.reader

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.main_fragment)
        bottom_nav.setupWithNavController(navController)

        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_scan -> {
                    navController.navigate(
                        R.id.homeFragment,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()
                    )
                    true
                }
                R.id.menu_history -> {
                    navController.navigate(
                        R.id.historyFragment,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.historyFragment, true).build()
                    )
                    true
                }
                R.id.menu_settings -> {
                    navController.navigate(
                        R.id.settingsFragment,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.settingsFragment, true).build()
                    )
                    true
                }
                else -> {
                    false
                }
            }
        }

        when (intent?.action) {
            Intent.ACTION_SEND -> {
                val direction = QrCodeFactoryFragmentDirections.actionGlobalQrCodeFactoryFragment(
                    intent?.clipData?.getItemAt(0)?.text?.toString() ?: ""
                )
                navController.navigate(direction)
            }
            Intent.ACTION_ASSIST -> {
                navController.navigate(
                    R.id.homeFragment,
                    null,
                    NavOptions.Builder().setLaunchSingleTop(true).build()
                )
            }
        }
    }
}
