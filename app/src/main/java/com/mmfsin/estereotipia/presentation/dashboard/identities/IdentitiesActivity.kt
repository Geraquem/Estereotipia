package com.mmfsin.estereotipia.presentation.dashboard.identities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.ActivityIdentitiesBinding
import com.mmfsin.estereotipia.presentation.exit.ExitDialog
import com.mmfsin.estereotipia.utils.ROOT_ACTIVITY_NAV_GRAPH
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IdentitiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentitiesBinding

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAds()
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

    fun openInstructions() {
        val intent = Intent(this, BedRockActivity::class.java)
        intent.putExtra(ROOT_ACTIVITY_NAV_GRAPH, R.navigation.nav_graph_instructions_identities)
        startActivity(intent)
    }

    private fun setAds() = loadInterstitial(AdRequest.Builder().build())

    fun showInterstitial() {
        mInterstitialAd?.let {
            it.show(this)
            loadInterstitial(AdRequest.Builder().build())
        }
    }

    private fun loadInterstitial(adRequest: AdRequest) {
        InterstitialAd.load(
            this,
            getString(R.string.interstitial),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    loadInterstitial(AdRequest.Builder().build())
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val dialog = ExitDialog { super.onBackPressed() }
        dialog.show(supportFragmentManager, "")
    }
}