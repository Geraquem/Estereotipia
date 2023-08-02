package com.mmfsin.whoami.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.mmfsin.whoami.databinding.ActivityMainBinding
import com.mmfsin.whoami.presentation.dashboard.dialogs.ExitDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var inDashboard = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setUpToolbar(showBack: Boolean, title: String) {
        binding.toolbar.apply {
            ivBack.isVisible = showBack
            tvTitle.text = title

            ivBack.setOnClickListener {
                inDashboard = false
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        val dialog = ExitDialog() { super.onBackPressed() }
        if (inDashboard) dialog.show(supportFragmentManager, "")
        else super.onBackPressed()
    }
}