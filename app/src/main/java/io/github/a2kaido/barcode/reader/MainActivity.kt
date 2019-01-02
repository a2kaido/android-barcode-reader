package io.github.a2kaido.barcode.reader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.main_fragment)
        bottom_nav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            println(destination)
            println(arguments)
        }

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
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
