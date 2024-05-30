package com.mmfsin.whoami.presentation.dashboard.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentCardsBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.dashboard.cards.adapter.CardsAdapter
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.choice.ChoiceDialog
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.discard.DiscardDialog
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.selected.SelectedCardDialog
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.wait.WaitSelectDialog
import com.mmfsin.whoami.presentation.dashboard.cards.interfaces.ICardsListener
import com.mmfsin.whoami.presentation.models.DeckType
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment(
    val deckId: String, private val deckType: DeckType, private val selectedCardId: String
) : BaseFragment<FragmentCardsBinding, CardsViewModel>(), ICardsListener {

    override val viewModel: CardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var cardsAdapter: CardsAdapter? = null

    private var selectedReady = false
    private var opportunities = 2

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCardsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCards(deckId, deckType)
    }

    override fun setUI() {}

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CardsEvent.GetCards -> setUpCards(event.cards)
                is CardsEvent.UpdateCard -> actionOnCard(event.cardId)
                is CardsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCards(cards: List<Card>) {
        binding.rvCards.apply {
            (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            cardsAdapter = CardsAdapter(cards, this@CardsFragment)
            adapter = cardsAdapter
        }
        activity?.showFragmentDialog(WaitSelectDialog { actionOnCard(selectedCardId) })
    }

    override fun onCardClick(cardId: String) {
        if (!selectedReady) activity?.showFragmentDialog(SelectedCardDialog.newInstance(cardId))
        else activity?.showFragmentDialog(
            DiscardDialog.newInstance(cardId, opportunities, this@CardsFragment)
        )
    }

    private fun actionOnCard(cardId: String) {
        if (!selectedReady) {
            selectedReady = true
            activity?.showFragmentDialog(SelectedCardDialog.newInstance(cardId))
        } else cardsAdapter?.updateDiscardedCards(cardId)
    }

    override fun makeChoice(cardId: String) {
        activity?.showFragmentDialog(
            ChoiceDialog.newInstance(cardId, opportunities, this@CardsFragment)
        )
    }

    override fun choiceComplete(winner: Boolean, cardId: String) {
        if (winner) {
            opportunities = 0
            cardsAdapter?.updateRivalCard(cardId)
        } else opportunities--
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}