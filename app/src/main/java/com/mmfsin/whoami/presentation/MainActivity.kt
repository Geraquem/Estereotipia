package com.mmfsin.whoami.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ActivityMainBinding
import com.mmfsin.whoami.presentation.dashboard.dialogs.ExitDialog
import com.mmfsin.whoami.presentation.instructions.InstructionsFragment
import com.mmfsin.whoami.utils.INSTRUCTIONS
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

            ivInstructions.setOnClickListener {
                supportFragmentManager.beginTransaction().addToBackStack(INSTRUCTIONS)
                    .setCustomAnimations(R.anim.fragment_up, 0, 0, R.anim.fragment_down)
                    .add(R.id.fc_instructions, InstructionsFragment()).commit()
            }
        }
    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager.fragments
        var popBack = false
        fragments.forEach { if (it is InstructionsFragment) popBack = true }
        if (popBack) {
            supportFragmentManager.popBackStack(INSTRUCTIONS, POP_BACK_STACK_INCLUSIVE)
        } else {
            if (inDashboard) {
                val dialog = ExitDialog() { super.onBackPressed() }
                dialog.show(supportFragmentManager, "")
            } else super.onBackPressed()
        }
    }
}