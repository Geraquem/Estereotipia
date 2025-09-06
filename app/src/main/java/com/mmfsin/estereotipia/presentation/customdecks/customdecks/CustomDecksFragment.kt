package com.mmfsin.estereotipia.presentation.customdecks.customdecks

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.databinding.FragmentCustomDecksBinding
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.presentation.MainActivity
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.adapter.CustomDecksAdapter
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.CustomDeckSheet
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.delete.DeleteCustomDeckDialog
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.edit.EditCustomDeckDialog
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.interfaces.ICustomDeckListener
import com.mmfsin.estereotipia.presentation.customdecks.snackbar.CustomSnackbar
import com.mmfsin.estereotipia.utils.encodeToBase64
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomDecksFragment : BaseFragment<FragmentCustomDecksBinding, CustomDecksViewModel>(),
    ICustomDeckListener {

    override val viewModel: CustomDecksViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCustomDecksBinding.inflate(inflater, container, false)

    override fun onResume() {
        super.onResume()
        viewModel.getCustomDecks()
    }

    override fun setUI() {
        binding.apply {
            llEmpty.visibility = View.GONE
            tvTitle.text = getString(R.string.custom_decks_toolbar)
        }
    }

    override fun setListeners() {
        binding.apply {
            tvShareError.setOnClickListener { navigateTo(R.navigation.nav_graph_share_deck_error) }
            btnCreateDeck.setOnClickListener { navigateTo(R.navigation.nav_graph_create_deck) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CustomDecksEvent.CustomDecks -> setUpDecks(event.decks)
                is CustomDecksEvent.FlowCompleted -> viewModel.getCustomDecks()
                is CustomDecksEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpDecks(decks: List<Deck>) {
        binding.apply {
            rvCustomDecks.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = CustomDecksAdapter(decks, this@CustomDecksFragment)
            }
            llEmpty.isVisible = decks.isEmpty()
            rvCustomDecks.isVisible = decks.isNotEmpty()
        }
    }

    override fun onCustomDeckClick(deckId: String) {
        activity?.showFragmentDialog(CustomDeckSheet(deckId, this@CustomDecksFragment))
    }

    override fun playWithCustomDeck(deckId: String) {
        (activity as MainActivity).openBedRockActivity(
            navGraph = R.navigation.nav_graph_who_is_who, strArgs = deckId
        )
    }

    override fun seeCards(deckId: String) = navigateTo(
        navGraph = R.navigation.nav_graph_see_deck_cards,
        strArgs = deckId
    )

    override fun editName(deckId: String) {
        activity?.showFragmentDialog(
            EditCustomDeckDialog.newInstance(
                deckId,
                this@CustomDecksFragment
            )
        )
    }

    override fun editCards(deckId: String) = navigateTo(
        navGraph = R.navigation.nav_graph_edit_deck_cards,
        strArgs = deckId
    )

    private fun navigateTo(navGraph: Int, strArgs: String? = null) =
        (activity as MainActivity).openBedRockActivity(navGraph = navGraph, strArgs = strArgs)

    override fun editCompleted() {
        CustomSnackbar.make(binding.clMain, Snackbar.LENGTH_SHORT).show()
        viewModel.getCustomDecks()
    }

    override fun shareDeck(name: String, cards: String) {
        val encodedText = encodeToBase64("$name/$cards")

        val sharedText = getString(R.string.shared_deck_shared_text)
        val sharedUrl = getString(R.string.shared_deck_url, encodedText)

        val text = """
        $sharedText
        
        $sharedUrl
        """.trimIndent()

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        mContext.startActivity(shareIntent)
    }

    override fun confirmDeleteCustomDeck(deckId: String) {
        activity?.showFragmentDialog(
            DeleteCustomDeckDialog.newInstance(
                deckId, this@CustomDecksFragment
            )
        )
    }

    override fun deleteCustomDeck(deckId: String) = viewModel.deleteCustomDeck(deckId)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}