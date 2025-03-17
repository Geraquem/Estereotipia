package com.mmfsin.estereotipia.presentation.customdecks.editcards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentCreateDeckBinding
import com.mmfsin.estereotipia.domain.models.CreateDeckCard
import com.mmfsin.estereotipia.presentation.allcards.dialogs.AllCardDialog
import com.mmfsin.estereotipia.presentation.customdecks.editcards.adapter.EditCardsAdapter
import com.mmfsin.estereotipia.presentation.customdecks.editcards.interfaces.IEditCardsListener
import com.mmfsin.estereotipia.presentation.customdecks.snackbar.CustomSnackbar
import com.mmfsin.estereotipia.utils.DECK_ID
import com.mmfsin.estereotipia.utils.getCards
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCardsFragment : BaseFragment<FragmentCreateDeckBinding, EditCardsViewModel>(),
    IEditCardsListener {

    override val viewModel: EditCardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var deckId: String? = null
    private var mAdapter: EditCardsAdapter? = null
    private var previousSelectedCards = listOf<String>()
    private var cardList = mutableListOf<String>()

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCreateDeckBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { deckId = it.getString(DECK_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckId?.let { id -> viewModel.getDeck(id) } ?: run { error() }
    }

    override fun setUI() {
        binding.apply {
            (activity as BedRockActivity).setUpToolbar(
                getString(R.string.custom_decks_dialog_edit_cards),
                instructionsVisible = false
            )
            btnAccept.text = getString(R.string.custom_decks_dialog_edit)
            checkBtnVisibility()
        }
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                deckId?.let { id -> viewModel.editCards(id, cardList) }
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is EditCardsEvent.AllCards -> setUpCards(event.cards)
                is EditCardsEvent.GetDeck -> {
                    previousSelectedCards = event.deck.cards.getCards()
                    viewModel.getAllCards()
                }

                is EditCardsEvent.CardsEditedCompleted -> {
                    CustomSnackbar.make(binding.clMain, Snackbar.LENGTH_SHORT).show()
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }

                is EditCardsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCards(cards: List<CreateDeckCard>) {
        binding.rvCards.apply {
            (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            mAdapter = EditCardsAdapter(cards, this@EditCardsFragment)
            adapter = mAdapter
        }

        previousSelectedCards.forEach { cardId ->
            val position = mAdapter?.getPositionByName(cardId)
            if (position != null && position != -1) {
                mAdapter?.checkPreviousCard(position)
                onCheckClick(position, true, cardId)
            }
        }
    }

    override fun onCardClick(id: String) {
        activity?.showFragmentDialog(AllCardDialog.newInstance(id))
    }

    override fun onCheckClick(position: Int, selected: Boolean, id: String) {
        if (selected) cardList.add(id)
        else {
            if (cardList.contains(id)) cardList.remove(id)
        }
        checkBtnVisibility()
        totalCardsText()
        mAdapter?.notifyItemChanged(position)
    }

    private fun checkBtnVisibility() {
        binding.clBtnOk.isVisible = cardList.size > 1
    }

    private fun totalCardsText() {
        val total = cardList.size.toString()
        binding.tvNumberCards.text = getString(R.string.custom_decks_total_cards, total)
    }

    override fun flowCompleted() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}