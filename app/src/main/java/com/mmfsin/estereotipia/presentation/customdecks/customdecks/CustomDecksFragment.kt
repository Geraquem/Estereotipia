package com.mmfsin.estereotipia.presentation.customdecks.customdecks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentCustomDecksBinding
import com.mmfsin.estereotipia.domain.models.Deck
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.CustomDecksFragmentDirections.Companion.actionCustomDecksToCreateNewDeck
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.CustomDecksFragmentDirections.Companion.actionCustomDecksToEditCards
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.CustomDecksFragmentDirections.Companion.actionCustomDecksToSeeCards
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.adapter.CustomDecksAdapter
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.CustomDeckSheet
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.delete.DeleteCustomDeckDialog
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.dialogs.edit.EditCustomDeckDialog
import com.mmfsin.estereotipia.presentation.customdecks.customdecks.interfaces.ICustomDeckListener
import com.mmfsin.estereotipia.presentation.customdecks.snackbar.CustomSnackbar
import com.mmfsin.estereotipia.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.estereotipia.utils.encodeToBase64
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomDecksFragment : BaseFragment<FragmentCustomDecksBinding, CustomDecksViewModel>(),
    ICustomDeckListener {

    override val viewModel: CustomDecksViewModel by viewModels()
    private lateinit var mContext: Context

    private var openCreateNewDeck = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCustomDecksBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        openCreateNewDeck = activity?.intent?.getBooleanExtra(BEDROCK_BOOLEAN_ARGS, false) ?: false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (openCreateNewDeck) {
            openCreateNewDeck = false
            createNewDeck()
        }
        viewModel.getCustomDecks()
    }

    override fun setUI() {
        binding.apply {
            llEmpty.visibility = View.GONE
            (activity as BedRockActivity).apply {
                inDashboard = false
                setUpToolbar(
                    title = getString(R.string.custom_decks_toolbar),
                    instructionsVisible = false
                )
            }
        }
    }

    override fun setListeners() {
        binding.apply {
            clShareError.setOnClickListener {
                (activity as BedRockActivity).openBedRockActivity(
                    navGraph = R.navigation.nav_graph_share_deck_error,
                )
            }
            btnCreateDeck.setOnClickListener { createNewDeck() }
        }
    }

    private fun createNewDeck() = findNavController().navigate(actionCustomDecksToCreateNewDeck())

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

    override fun onCustomDeckClick(id: String) {
        activity?.showFragmentDialog(CustomDeckSheet(id, this@CustomDecksFragment))
    }

    override fun playWithCustomDeck(id: String) {
        (activity as BedRockActivity).openBedRockActivity(
            navGraph = R.navigation.nav_graph_dashboard,
            strArgs = id
        )
    }

    override fun seeCards(id: String) {
        findNavController().navigate(actionCustomDecksToSeeCards(id))
    }

    override fun editName(id: String) {
        activity?.showFragmentDialog(EditCustomDeckDialog.newInstance(id, this@CustomDecksFragment))
    }

    override fun editCards(id: String) =
        findNavController().navigate(actionCustomDecksToEditCards(id))

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

    override fun confirmDeleteCustomDeck(id: String) {
        activity?.showFragmentDialog(
            DeleteCustomDeckDialog.newInstance(
                id,
                this@CustomDecksFragment
            )
        )
    }

    override fun deleteCustomDeck(id: String) = viewModel.deleteCustomDeck(id)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}