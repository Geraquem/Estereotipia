package com.mmfsin.estereotipia.presentation.dashboard.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentCardsBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.dashboard.cards.adapter.CardsAdapter
import com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.choice.ChoiceDialog
import com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.discard.DiscardDialog
import com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.selected.SelectedCardDialog
import com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.wait.WaitSelectDialog
import com.mmfsin.estereotipia.presentation.dashboard.cards.interfaces.ICardsListener
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment(val deckId: String, private val selectedCardId: String) :
    BaseFragment<FragmentCardsBinding, CardsViewModel>(), ICardsListener {

    override val viewModel: CardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var cardsAdapter: CardsAdapter? = null

    private var opportunities = 2

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCardsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCards(deckId)
    }

    override fun setUI() {}

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CardsEvent.GetCards -> setUpCards(event.cards)
                is CardsEvent.UpdateCard -> cardsAdapter?.updateDiscardedCards(event.cardId)
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
        activity?.showFragmentDialog(WaitSelectDialog {
            activity?.showFragmentDialog(SelectedCardDialog.newInstance(selectedCardId))
        })
    }

    override fun onCardClick(cardId: String) {
        activity?.showFragmentDialog(
            DiscardDialog.newInstance(
                cardId,
                opportunities,
                this@CardsFragment
            )
        )
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

        if (opportunities == 0) (activity as BedRockActivity).isGameFinished = true
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}