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
import com.mmfsin.whoami.presentation.dashboard.viepager.interfaces.IViewPagerListener
import com.mmfsin.whoami.utils.DECK_ID
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding, ViewPagerViewModel>(),
    IViewPagerListener {

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
        deckId?.let { id -> viewModel.getDeckById(id) } ?: run { error() }
    }

    override fun setUI() {
        binding.loading.root.visibility = View.VISIBLE
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ViewPagerEvent.ActualDeck -> {
                    setUpToolbar(event.deck.name)
                    deckId?.let { id -> viewModel.getRandomSelectedCard(id) } ?: run { error() }
                }
                is ViewPagerEvent.SelectedCard -> setUpViewPager(event.selectedCardId)
                is ViewPagerEvent.SomethingWentWrong -> error()

            }
        }
    }

    private fun setUpToolbar(deckName: String) {
        (activity as MainActivity).apply {
            this.inDashboard = true
            setUpToolbar(showBack = true, deckName)
        }
    }

    private fun setUpViewPager(selectedCardId: String) {
        deckId?.let { deckId ->
            binding.apply {
                activity?.let {
                    viewPager.adapter =
                        ViewPagerAdapter(it, deckId, selectedCardId, this@ViewPagerFragment)
                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                        when (position) {
                            0 -> tab.text = getString(R.string.vp_tab_cards)
                            1 -> tab.text = getString(R.string.vp_tab_questions)
                        }
                    }.attach()
                }
                loading.root.visibility = View.GONE
            }
        } ?: run { error() }
    }

    override fun openCardsView() {
        val tab = binding.tabLayout.getTabAt(0)
        tab?.select()
    }

    private fun error() {
        (activity as MainActivity).inDashboard = false
        activity?.showErrorDialog()
    }
}