package com.mmfsin.whoami.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ActivityMainBinding
import com.mmfsin.whoami.presentation.exit.ExitDialog
import com.mmfsin.whoami.presentation.instructions.InstructionsFragment
import com.mmfsin.whoami.utils.INSTRUCTIONS
import com.mmfsin.whoami.utils.INSTRUCTIONS_DETAIL
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

            ivBack.setOnClickListener { onBackPressed() }

            ivInstructions.setOnClickListener { openInstructions() }
        }
    }

    fun openInstructions() {
        supportFragmentManager.beginTransaction().addToBackStack(INSTRUCTIONS)
            .setCustomAnimations(R.anim.fragment_up, 0, 0, R.anim.fragment_down)
            .add(R.id.fc_instructions, InstructionsFragment()).commit()
    }

    private fun removeFragment(fragmentName: String) {
        supportFragmentManager.popBackStack(fragmentName, POP_BACK_STACK_INCLUSIVE)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            if (inDashboard) {
                val dialog = ExitDialog() { super.onBackPressed() }
                dialog.show(supportFragmentManager, "")
            } else super.onBackPressed()
        } else {
            when (supportFragmentManager.getBackStackEntryAt(count - 1).name) {
                INSTRUCTIONS_DETAIL -> removeFragment(INSTRUCTIONS_DETAIL)
                INSTRUCTIONS -> removeFragment(INSTRUCTIONS)
            }
        }
    }
}