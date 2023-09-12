package com.mmfsin.whoami.presentation.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentDashboardBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.dashboard.adapter.CardsAdapter
import com.mmfsin.whoami.presentation.dashboard.interfaces.ICardsListener
import com.mmfsin.whoami.presentation.dialogs.discard.DiscardDialog
import com.mmfsin.whoami.presentation.dialogs.instructions.WaitSelectDialog
import com.mmfsin.whoami.presentation.dialogs.select.SelectedCardDialog
import com.mmfsin.whoami.utils.DECK_ID
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    ICardsListener {

    override val viewModel: DashboardViewModel by viewModels()
    private lateinit var mContext: Context

    private var deckId: String? = null
    private var cardsAdapter: CardsAdapter? = null

    private var selectedReady = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { deckId = it.getString(DECK_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckId?.let { id -> viewModel.getActualDeck(id) } ?: run { error() }
    }

    override fun setUI() {}
    override fun setListeners() {}

    private fun setToolbar(deckName: String) {
        (activity as MainActivity).apply {
            this.inDashboard = true
            setUpToolbar(showBack = true, deckName)
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DashboardEvent.GetActualDeck -> {
                    setToolbar(event.deck.name)
                    viewModel.getCards(event.deck.id)
                }
                is DashboardEvent.GetCards -> setUpCardsToSelect(event.cards)
                is DashboardEvent.RandomSelectedCard -> actionOnCard(event.cardId)
                is DashboardEvent.UpdateCard -> actionOnCard(event.cardId)
                is DashboardEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCardsToSelect(cards: List<Card>) {
        activity?.let {
            it.let {
                val dialog = WaitSelectDialog { viewModel.getRandomSelectedCard(cards) }
                dialog.show(it.supportFragmentManager, "")
            }
        }
        binding.rvCards.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            cardsAdapter = CardsAdapter(cards, this@DashboardFragment)
            adapter = cardsAdapter
        }
    }

    override fun onCardClick(cardId: String) {
        if (!selectedReady) {
            activity?.let {
                val dialogFragment = SelectedCardDialog.newInstance(cardId)
                dialogFragment.show(it.supportFragmentManager, "")
            }
        } else {
            activity?.let {
                val dialogFragment = DiscardDialog.newInstance(cardId)
                dialogFragment.show(it.supportFragmentManager, "")
            }
        }
    }

    private fun actionOnCard(cardId: String) {
        if (!selectedReady) {
            selectedReady = true
            activity?.let {
                val dialogFragment = SelectedCardDialog.newInstance(cardId)
                dialogFragment.show(it.supportFragmentManager, "")
            }
            cardsAdapter?.updateSelectedCard(cardId)
        } else cardsAdapter?.updateDiscardedCards(cardId)
    }

//    override fun onDiscardClick(cardId: String) = viewModel.discardCard(cardId, updateFlow = false)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}