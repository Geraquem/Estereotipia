package com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mmfsin.estereotipia.base.BaseBottomSheet
import com.mmfsin.estereotipia.databinding.BsheetCustomDeckBinding
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.interfaces.ICustomDeckListener
import com.mmfsin.estereotipia.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomDeckSheet(
    private val customDeckId: String,
    val listener: ICustomDeckListener
) : BaseBottomSheet<BsheetCustomDeckBinding>() {

    val viewModel: CustomDeckViewModel by viewModels()

    private var deck: Deck? = null

    override fun inflateView(inflater: LayoutInflater) = BsheetCustomDeckBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        viewModel.getCustomDeck(customDeckId)
    }

    override fun setListeners() {
        binding.apply {
            tvPlay.setOnClickListener { actionAndDismiss { listener.playWithCustomDeck(customDeckId) } }
            tvSeeCards.setOnClickListener { actionAndDismiss { listener.seeCards(customDeckId) } }

            tvEditName.setOnClickListener { actionAndDismiss { listener.editName(customDeckId) } }
            tvEditCards.setOnClickListener { actionAndDismiss { listener.editCards(customDeckId) } }

            tvShare.setOnClickListener { actionAndDismiss { shareDeck() } }
            tvDelete.setOnClickListener {
                actionAndDismiss { listener.confirmDeleteCustomDeck(customDeckId) }
            }
        }
    }

    private fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CustomDeckEvent.GetDeck -> {
                    deck = event.deck
                    binding.tvTitle.text = event.deck.name
                }

                is CustomDeckEvent.EditedCompleted -> {}
                is CustomDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun actionAndDismiss(action: () -> Unit) {
        action()
        dismiss()
    }

    private fun shareDeck() =
        deck?.let { d -> listener.shareDeck(d.name, d.cards) } ?: run { error() }

    private fun error() = activity?.showErrorDialog(goBack = false)
}