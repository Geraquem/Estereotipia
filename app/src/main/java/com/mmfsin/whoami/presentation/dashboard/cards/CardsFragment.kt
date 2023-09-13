package com.mmfsin.whoami.presentation.dashboard.cards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
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
import com.mmfsin.whoami.presentation.dialogs.instructions.WaitSelectDialog
import com.mmfsin.whoami.presentation.dialogs.selected.SelectedCardDialog
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment(val deckId: String) : BaseFragment<FragmentCardsBinding, CardsViewModel>(),
    ICardsListener {

    override val viewModel: CardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var cardsAdapter: CardsAdapter? = null

    private var selectedReady = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCardsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getActualDeck(deckId)
    }

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {}
    }

    private fun setToolbar(deckName: String) {
        (activity as MainActivity).apply {
            this.inDashboard = true
            setUpToolbar(showBack = true, deckName)
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CardsEvent.GetActualDeck -> {
                    setToolbar(event.deck.name)
                    viewModel.getCards(event.deck.id)
                }
                is CardsEvent.GetCards -> setUpCardsToSelect(event.cards)
                is CardsEvent.RandomSelectedCard -> actionOnCard(event.cardId)
                is CardsEvent.UpdateCard -> actionOnCard(event.cardId)
                is CardsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCardsToSelect(cards: List<Card>) {
        showDialog(WaitSelectDialog { viewModel.getRandomSelectedCard(cards) })
        binding.rvCards.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            cardsAdapter = CardsAdapter(cards, this@CardsFragment)
            adapter = cardsAdapter
        }
    }

    override fun onCardClick(cardId: String) {
        if (!selectedReady) showDialog(SelectedCardDialog.newInstance(cardId))
        else showDialog(DiscardDialog.newInstance(cardId))
    }

    private fun actionOnCard(cardId: String) {
        if (!selectedReady) {
            selectedReady = true
            showDialog(SelectedCardDialog.newInstance(cardId))
            cardsAdapter?.updateSelectedCard(cardId)
        } else cardsAdapter?.updateDiscardedCards(cardId)
    }

//    override fun onDiscardClick(cardId: String) = viewModel.discardCard(cardId, updateFlow = false)

    private fun showDialog(dialog: DialogFragment) {
        activity?.let { dialog.show(it.supportFragmentManager, "") }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}