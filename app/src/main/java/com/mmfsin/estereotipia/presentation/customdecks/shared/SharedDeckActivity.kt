package com.mmfsin.estereotipia.presentation.customdecks.shared

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ActivitySharedDeckBinding
import com.mmfsin.estereotipia.presentation.MainActivity
import com.mmfsin.estereotipia.utils.countDown
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedDeckActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySharedDeckBinding

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedDeckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBarColor(R.color.white)
        initialStatements()

        checkIfSharedDeck()
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
            @Suppress("DEPRECATION") window.statusBarColor = ContextCompat.getColor(this, color)
        }

        //true == dark
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
    }

    private fun initialStatements() {
        binding.apply {
            toolbar.tvTitle.text = getString(R.string.shared_deck_title)
            toolbar.ivInstructions.isVisible = false
            toolbar.ivBack.setOnClickListener { finish() }
        }
    }

    private fun checkIfSharedDeck() {
        uri = intent.data
        uri?.let { openSharedDeckDialog(it) }
    }

    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent)
        uri = intent.data
        uri?.let { openSharedDeckDialog(it) }
    }

    private fun openSharedDeckDialog(mUri: Uri) {
        val sharedDeckDialog = SharedDeckDialog.newInstance(mUri) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        countDown(500) { sharedDeckDialog.show(supportFragmentManager, "") }
    }
}
