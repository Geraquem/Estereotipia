package com.mmfsin.whoami.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.bedrock.BedRockActivity
import com.mmfsin.whoami.databinding.ActivityMainBinding
import com.mmfsin.whoami.presentation.instructions.InstructionsFragment
import com.mmfsin.whoami.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.whoami.utils.BEDROCK_STR_ARGS
import com.mmfsin.whoami.utils.INSTRUCTIONS
import com.mmfsin.whoami.utils.ROOT_ACTIVITY_NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var checkVersion = true

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBar(R.color.orange)
        uri = intent.data
    }

    fun changeStatusBar(color: Int) {
        window.statusBarColor = ContextCompat.getColor(this, color)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
    }

    fun openInstructions() {
        supportFragmentManager.beginTransaction().addToBackStack(INSTRUCTIONS)
            .setCustomAnimations(R.anim.fragment_up, 0, 0, R.anim.fragment_down)
            .add(R.id.fc_instructions, InstructionsFragment()).commit()
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