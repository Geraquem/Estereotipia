package com.mmfsin.estereotipia.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.ActivityMainBinding
import com.mmfsin.estereotipia.presentation.dashboard.identities.IdentitiesActivity
import com.mmfsin.estereotipia.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.estereotipia.utils.BEDROCK_STR_ARGS
import com.mmfsin.estereotipia.utils.ROOT_ACTIVITY_NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var checkVersion = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Estereotipia)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun changeStatusBarColor(color: Int) {
        // Android 15+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
                view.setBackgroundColor(ContextCompat.getColor(this, color))
                view.setPadding(0, statusBarInsets.top, 0, 0)
                insets
            }

        } else {
            // For Android 14 and below
            @Suppress("DEPRECATION")
            window.statusBarColor = ContextCompat.getColor(this, color)
        }

        //true == dark
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
    }

    fun openBedRockActivity(
        navGraph: Int,
        strArgs: String? = null,
        booleanArgs: Boolean? = null
    ) {
        val intent = Intent(this, BedRockActivity::class.java)
        strArgs?.let { intent.putExtra(BEDROCK_STR_ARGS, strArgs) }
        booleanArgs?.let { intent.putExtra(BEDROCK_BOOLEAN_ARGS, booleanArgs) }
        intent.putExtra(ROOT_ACTIVITY_NAV_GRAPH, navGraph)
        startActivity(intent)
    }

    fun openIdentitiesActivity() {
        val intent = Intent(this, IdentitiesActivity::class.java)
        startActivity(intent)
    }

    fun handleLoading(show: Boolean) {
        binding.loading.isVisible = show

        /** Si cambio el status bar de color, me hace un popping raro y no me gusta >:( */
//        val color = if (show) R.color.white else R.color.orange
//        changeStatusBarColor(R.color.orange)

        /** but set dark icons */
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
    }
}