package com.mmfsin.whoami.presentation.customdecks.customdecks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.base.bedrock.BedRockActivity
import com.mmfsin.whoami.databinding.FragmentCustomDecksBinding
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.presentation.customdecks.customdecks.CustomDecksFragmentDirections.Companion.actionCustomDecksToCreateNewDeck
import com.mmfsin.whoami.presentation.customdecks.customdecks.CustomDecksFragmentDirections.Companion.actionCustomDecksToDashboard
import com.mmfsin.whoami.presentation.customdecks.customdecks.CustomDecksFragmentDirections.Companion.actionCustomDecksToEditCards
import com.mmfsin.whoami.presentation.customdecks.customdecks.adapter.CustomDecksAdapter
import com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs.CustomDeckDialog
import com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs.delete.DeleteCustomDeckDialog
import com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs.edit.EditCustomDeckDialog
import com.mmfsin.whoami.presentation.customdecks.customdecks.interfaces.ICustomDeckListener
import com.mmfsin.whoami.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
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
            tvEmpty.visibility = View.GONE
            (activity as BedRockActivity).apply {
                inDashboard = false
                setUpToolbar(getString(R.string.custom_decks_toolbar))
            }
        }
    }

    override fun setListeners() {
        binding.apply {
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
            tvEmpty.isVisible = decks.isEmpty()
            rvCustomDecks.isVisible = decks.isNotEmpty()
        }
    }

    override fun onCustomDeckClick(id: String) {
        activity?.showFragmentDialog(CustomDeckDialog(id, this@CustomDecksFragment))
    }

    override fun playWithCustomDeck(id: String) =
        findNavController().navigate(actionCustomDecksToDashboard(id))

    override fun editName(id: String) {
        activity?.showFragmentDialog(EditCustomDeckDialog.newInstance(id, this@CustomDecksFragment))
    }

    override fun editCards(id: String) =
        findNavController().navigate(actionCustomDecksToEditCards(id))

    override fun editCompleted() = viewModel.getCustomDecks()

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