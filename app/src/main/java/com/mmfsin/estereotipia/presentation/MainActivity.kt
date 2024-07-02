package com.mmfsin.estereotipia.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.snackbar.Snackbar
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.ActivityMainBinding
import com.mmfsin.estereotipia.presentation.customdecks.snackbar.CustomSnackbar
import com.mmfsin.estereotipia.presentation.instructions.InstructionsFragment
import com.mmfsin.estereotipia.presentation.menu.dialogs.SharedDeckDialog
import com.mmfsin.estereotipia.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.estereotipia.utils.BEDROCK_STR_ARGS
import com.mmfsin.estereotipia.utils.INSTRUCTIONS
import com.mmfsin.estereotipia.utils.ROOT_ACTIVITY_NAV_GRAPH
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
        checkIfSharedDeck()
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

    private fun openSharedDeckDialog(mUri: Uri) {
        val sharedDeckDialog = SharedDeckDialog.newInstance(mUri) { added ->
            uri = null
            if(added) CustomSnackbar.make(binding.clMain, Snackbar.LENGTH_SHORT).show()
        }
        sharedDeckDialog.show(supportFragmentManager, "")
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