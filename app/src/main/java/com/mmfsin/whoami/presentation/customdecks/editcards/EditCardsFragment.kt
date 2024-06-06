package com.mmfsin.whoami.presentation.customdecks.editcards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.base.bedrock.BedRockActivity
import com.mmfsin.whoami.databinding.FragmentCreateDeckBinding
import com.mmfsin.whoami.databinding.FragmentCustomDecksBinding
import com.mmfsin.whoami.presentation.customdecks.customdecks.CustomDecksFragmentDirections.Companion.actionCustomDecksToCreateNewDeck
import com.mmfsin.whoami.utils.DECK_ID
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCardsFragment : BaseFragment<FragmentCreateDeckBinding, EditCardsViewModel>() {

    override val viewModel: EditCardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var deckId: String? = null

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
            (activity as BedRockActivity).setUpToolbar(getString(R.string.custom_decks_dialog_edit_cards))
        }
    }

    override fun setListeners() {
        binding.apply {}
    }

    private fun createNewDeck() = findNavController().navigate(actionCustomDecksToCreateNewDeck())

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is EditCardsEvent.GetDeck -> {}
                is EditCardsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}