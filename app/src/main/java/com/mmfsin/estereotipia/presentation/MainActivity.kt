package com.mmfsin.estereotipia.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.ActivityMainBinding
import com.mmfsin.estereotipia.presentation.dashboard.identities.IdentitiesActivity
import com.mmfsin.estereotipia.presentation.instructions.whoiswho.InstructionsFragment
import com.mmfsin.estereotipia.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.estereotipia.utils.BEDROCK_STR_ARGS
import com.mmfsin.estereotipia.utils.INSTRUCTIONS
import com.mmfsin.estereotipia.utils.ROOT_ACTIVITY_NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var checkVersion = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBar(R.color.orange)
    }

    fun changeStatusBar(color: Int) {
        window.statusBarColor = ContextCompat.getColor(this, color)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
    }

    fun openInstructions(openHTP: Boolean = false) {
        supportFragmentManager.beginTransaction().addToBackStack(INSTRUCTIONS)
            .setCustomAnimations(R.anim.fragment_up, 0, 0, R.anim.fragment_down)
            .add(R.id.fc_instructions, InstructionsFragment(openHTP)).commit()
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) super.onBackPressed()
        else {
            when (supportFragmentManager.getBackStackEntryAt(count - 1).name) {
                INSTRUCTIONS -> changeStatusBar(R.color.orange)
            }
            super.onBackPressed()
        }
    }
}