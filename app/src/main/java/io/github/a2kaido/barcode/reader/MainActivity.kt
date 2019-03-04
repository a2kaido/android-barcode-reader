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
                    findNavController(R.id.main_fragment).navigate(R.id.homeFragment)
                    true
                }
                R.id.menu_history -> {
                    findNavController(R.id.main_fragment).navigate(R.id.historyFragment)
                    true
                }
                R.id.menu_settings -> {
                    findNavController(R.id.main_fragment).navigate(R.id.settingsFragment)
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
                findNavController(R.id.main_fragment).navigate(direction)
            }
            Intent.ACTION_ASSIST -> {
                findNavController(R.id.main_fragment).navigate(
                    R.id.homeFragment,
                    null,
                    NavOptions.Builder().setLaunchSingleTop(true).build()
                )
            }
        }
    }
}
