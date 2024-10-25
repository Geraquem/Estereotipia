package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentViewPagerBinding
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.viewpager.adapter.ViewPagerAdapter
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.viewpager.interfaces.IViewPagerListener
import com.mmfsin.estereotipia.utils.BEDROCK_STR_ARGS
import com.mmfsin.estereotipia.utils.DECK_ID
import com.mmfsin.estereotipia.utils.checkNotNulls
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding, ViewPagerViewModel>(),
    IViewPagerListener {

    override val viewModel: ViewPagerViewModel by viewModels()

    private var deckId: String? = null
    private var selectedCardId: String? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentViewPagerBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let {
            deckId = it.getString(DECK_ID)
        } ?: run {
            deckId = activity?.intent?.getStringExtra(BEDROCK_STR_ARGS)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BedRockActivity).inDashboard = true
        deckId?.let { id -> viewModel.getDeck(id) } ?: run { error() }
    }

    override fun setUI() {
        binding.loading.root.visibility = View.VISIBLE
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is ViewPagerEvent.GetDeck -> {
                    setUpToolbar(event.deck.name)
                    viewModel.getRandomSelectedCard(event.deck.cards)
                }

                is ViewPagerEvent.SelectedCard -> {
                    selectedCardId = event.selectedCardId
                    setUpViewPager()
                }

                is ViewPagerEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpToolbar(deckName: String) =
        (activity as BedRockActivity).apply {
            setUpToolbar(
                title = deckName,
                instructionsVisible = true,
                instructionsNavGraph = R.navigation.nav_graph_instructions_who_is_who
            )
            showBanner(visible = true)
        }

    private fun setUpViewPager() {
        checkNotNulls(deckId, selectedCardId) { deck, selectedCard ->
            binding.apply {
                activity?.let {
                    viewPager.adapter =
                        ViewPagerAdapter(
                            fragmentActivity = it,
                            deckId = deck,
                            selectedCardId = selectedCard,
                            listener = this@ViewPagerFragment
                        )
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
        activity?.showErrorDialog()
    }
}