package com.mmfsin.whoami.presentation.dashboard.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentCardsBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.dashboard.cards.adapter.CardsAdapter
import com.mmfsin.whoami.presentation.dashboard.cards.interfaces.ICardsListener
import com.mmfsin.whoami.presentation.dialogs.discard.DiscardDialog
import com.mmfsin.whoami.presentation.dialogs.selected.WaitSelectDialog
import com.mmfsin.whoami.presentation.dialogs.selected.SelectedCardDialog
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment(val deckId: String, private val selectedCardId: String) :
    BaseFragment<FragmentCardsBinding, CardsViewModel>(), ICardsListener {

    override val viewModel: CardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var cardsAdapter: CardsAdapter? = null

    private var selectedReady = false

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
                is CardsEvent.GetCards -> setUpCardsToSelect(event.cards)
                is CardsEvent.UpdateCard -> actionOnCard(event.cardId)
                is CardsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCardsToSelect(cards: List<Card>) {
        binding.rvCards.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            cardsAdapter = CardsAdapter(cards, this@CardsFragment)
            adapter = cardsAdapter
        }
        activity?.showFragmentDialog(WaitSelectDialog { actionOnCard(selectedCardId) })
    }

    override fun onCardClick(cardId: String) {
        if (!selectedReady) activity?.showFragmentDialog(SelectedCardDialog.newInstance(cardId))
        else activity?.showFragmentDialog(DiscardDialog.newInstance(cardId))
    }

    private fun actionOnCard(cardId: String) {
        if (!selectedReady) {
            selectedReady = true
            activity?.showFragmentDialog(SelectedCardDialog.newInstance(cardId))
            cardsAdapter?.updateSelectedCard(cardId)
        } else cardsAdapter?.updateDiscardedCards(cardId)
    }

//    override fun onDiscardClick(cardId: String) = viewModel.discardCard(cardId, updateFlow = false)

    private fun error() {
        (activity as MainActivity).inDashboard = false
        activity?.showErrorDialog()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}