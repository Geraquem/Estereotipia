package com.mmfsin.whoami.base.bedrock

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.mmfsin.whoami.R
import com.mmfsin.whoami.databinding.ActivityBedrockBinding
import com.mmfsin.whoami.presentation.exit.ExitDialog
import com.mmfsin.whoami.presentation.instructions.InstructionsFragment
import com.mmfsin.whoami.utils.INSTRUCTIONS
import com.mmfsin.whoami.utils.ROOT_ACTIVITY_NAV_GRAPH
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BedRockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBedrockBinding

    var isGameFinished = false
    var inDashboard = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBedrockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** handle back */
            onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        changeStatusBar()
        setUpNavGraph()
    }

    private fun changeStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
    }

    private fun setUpNavGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.br_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = intent.getIntExtra(ROOT_ACTIVITY_NAV_GRAPH, -1)
        navController.apply { if (navGraph != -1) setGraph(navGraph) else error() }
    }

    fun setUpToolbar(title: String? = "") {
        binding.toolbar.apply {
            ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            tvTitle.text = title
            ivInstructions.setOnClickListener { openInstructions() }
        }
    }

    private fun openInstructions() {
        supportFragmentManager.beginTransaction().addToBackStack(INSTRUCTIONS)
            .setCustomAnimations(R.anim.fragment_up, 0, 0, R.anim.fragment_down)
            .add(R.id.fc_instructions, InstructionsFragment()).commit()
    }

    private fun error() = showErrorDialog(goBack = true)

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (inDashboard) {
                val dialog = ExitDialog { finish() }
                dialog.show(supportFragmentManager, "")
            } else {
                this.isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}