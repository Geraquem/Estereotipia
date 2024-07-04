package com.mmfsin.estereotipia.presentation.customdecks.shared

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ActivitySharedDeckBinding
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

        changeStatusBar()
        initialStatements()

        checkIfSharedDeck()
    }

    private fun changeStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        uri = intent?.data
        uri?.let { openSharedDeckDialog(it) }
    }

    private fun openSharedDeckDialog(mUri: Uri) {
        val sharedDeckDialog = SharedDeckDialog.newInstance(mUri) { finish() }
        countDown(500) { sharedDeckDialog.show(supportFragmentManager, "") }
    }
}
