package com.mmfsin.whoami.presentation.dashboard.viepager

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.mmfsin.whoami.base.BaseFragmentNoVM
import com.mmfsin.whoami.databinding.FragmentViewPagerBinding
import com.mmfsin.whoami.presentation.dashboard.viepager.adapter.ViewPagerAdapter
import com.mmfsin.whoami.utils.DECK_ID
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : BaseFragmentNoVM<FragmentViewPagerBinding>() {

    private var deckId: String? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentViewPagerBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { deckId = it.getString(DECK_ID) }
    }

    override fun setUI() {
        setUpViewPager()
    }

    private fun setUpViewPager() {
        deckId?.let { deckId ->
            binding.apply {
                activity?.let {
                    viewPager.adapter = ViewPagerAdapter(it, deckId)
                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        when (position) {
                            0 -> tab.setText("cartas")
                            1 -> tab.setText("preguntas")
                        }
                    }.attach()
                }
            }
        } ?: run { error() }
    }

    private fun error() = activity?.showErrorDialog()
}