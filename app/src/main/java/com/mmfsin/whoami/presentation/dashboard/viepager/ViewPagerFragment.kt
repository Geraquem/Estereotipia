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
import com.mmfsin.whoami.presentation.models.DeckType
import com.mmfsin.whoami.presentation.models.DeckType.CUSTOM_DECK
import com.mmfsin.whoami.presentation.models.DeckType.SYSTEM_DECK
import com.mmfsin.whoami.utils.DECK_ID
import com.mmfsin.whoami.utils.DECK_TYPE
import com.mmfsin.whoami.utils.checkNotNulls
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding, ViewPagerViewModel>(),
    IViewPagerListener {

    override val viewModel: ViewPagerViewModel by viewModels()

    private var deckType: DeckType? = null
    private var deckId: String? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentViewPagerBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { deckType = it.getSerializable(DECK_TYPE) as DeckType }
        arguments?.let { deckId = it.getString(DECK_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNotNulls(deckId, deckType) { id, type ->
            viewModel.getDeck(id, type)
        } ?: run { error() }
    }

    override fun setUI() {
        binding.loading.root.visibility = View.VISIBLE
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ViewPagerEvent.SystemDeck -> {
                    setUpToolbar(event.deck.name)
                    deckId?.let { id -> viewModel.getRandomSelectedCard(id, SYSTEM_DECK) }
                        ?: run { error() }
                }
                is ViewPagerEvent.CustomDeck -> {
                    setUpToolbar(event.myDeck.name)
                    deckId?.let { id -> viewModel.getRandomSelectedCard(id, CUSTOM_DECK) }
                        ?: run { error() }
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
        checkNotNulls(deckId, deckType) { id, type ->
            binding.apply {
                activity?.let {
                    viewPager.adapter =
                        ViewPagerAdapter(it, id, type, selectedCardId, this@ViewPagerFragment)
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