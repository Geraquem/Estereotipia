package com.mmfsin.estereotipia.base.bedrock

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.AdRequest
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.databinding.ActivityBedrockBinding
import com.mmfsin.estereotipia.presentation.exit.ExitDialog
import com.mmfsin.estereotipia.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.estereotipia.utils.BEDROCK_STR_ARGS
import com.mmfsin.estereotipia.utils.ROOT_ACTIVITY_NAV_GRAPH
import com.mmfsin.estereotipia.utils.showErrorDialog
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

        changeStatusBar()
        setUpNavGraph()
        setAds()
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

    fun setUpToolbar(
        title: String? = "",
        instructionsVisible: Boolean = true,
        instructionsNavGraph: Int? = null
    ) {
        binding.toolbar.apply {
            ivBack.setOnClickListener { onBackPressed() }
            tvTitle.text = title
            ivInstructions.isVisible = instructionsVisible
            instructionsNavGraph?.let { navGraph ->
                ivInstructions.setOnClickListener { openBedRockActivity(navGraph = navGraph) }
            }
        }
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

    private fun setAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        showBanner(visible = false)
    }

    fun showBanner(visible: Boolean = false) {
        binding.adView.isVisible = visible
    }

    private fun error() = showErrorDialog(goBack = true)

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            if (inDashboard) {
                val dialog = ExitDialog() { super.onBackPressed() }
                dialog.show(supportFragmentManager, "")
            } else super.onBackPressed()
        } else super.onBackPressed()
    }
}