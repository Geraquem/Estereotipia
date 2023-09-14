package com.mmfsin.whoami.presentation.dashboard.viepager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentViewPagerBinding
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.dashboard.viepager.adapter.ViewPagerAdapter
import com.mmfsin.whoami.utils.DECK_ID
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding, ViewPagerViewModel>() {

    override val viewModel: ViewPagerViewModel by viewModels()

    private var deckId: String? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentViewPagerBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { deckId = it.getString(DECK_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckId?.let { id -> viewModel.getRandomSelectedCard(id) } ?: run { error() }
    }

    override fun setUI() {
        setUpViewPager()
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ViewPagerEvent.SelectedCard -> {
                    val a = 2
                }
                is ViewPagerEvent.SomethingWentWrong -> error()
            }
        }
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

    private fun error() {
        (activity as MainActivity).apply {
            this.inDashboard = false
        }
        activity?.showErrorDialog()
    }
}