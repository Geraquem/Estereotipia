package com.mmfsin.estereotipia.presentation.dashboard.identities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ActivityIdentitiesBinding
import com.mmfsin.estereotipia.presentation.instructions.InstructionsFragment
import com.mmfsin.estereotipia.utils.INSTRUCTIONS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentitiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBar()
        setUpNavGraph()
    }

    private fun changeStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
    }

    private fun setUpNavGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.br_fragment_container) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.nav_graph_identities)
    }

    private fun openInstructions() {
        supportFragmentManager.beginTransaction().addToBackStack(INSTRUCTIONS)
            .setCustomAnimations(R.anim.fragment_up, 0, 0, R.anim.fragment_down)
            .add(R.id.fc_instructions, InstructionsFragment()).commit()
    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        val count = supportFragmentManager.backStackEntryCount
//        if (count == 0) {
//            if (inDashboard) {
//                val dialog = ExitDialog() { super.onBackPressed() }
//                dialog.show(supportFragmentManager, "")
//            } else super.onBackPressed()
//        } else super.onBackPressed()
//    }
}