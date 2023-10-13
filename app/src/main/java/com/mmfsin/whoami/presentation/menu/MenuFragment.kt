package com.mmfsin.whoami.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMenuBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToAllCards
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToCreateDeck
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToDashboard
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToMyDecks
import com.mmfsin.whoami.presentation.menu.adapter.MenuCardsAdapter
import com.mmfsin.whoami.presentation.menu.decks.DecksDialog
import com.mmfsin.whoami.presentation.menu.listener.IMenuListener
import com.mmfsin.whoami.presentation.models.DeckType.SYSTEM_DECK
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IMenuListener {

    override val viewModel: MenuViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val checkVersion = (activity as MainActivity).checkVersion
        if (checkVersion) {
            (activity as MainActivity).checkVersion = false
            viewModel.checkVersion()
        } else binding.loading.root.visibility = View.GONE
    }

    override fun setUI() {
        binding.apply {
            setToolbar()
        }
    }

    private fun setToolbar() {
        (activity as MainActivity).apply {
            this.inDashboard = false
            setUpToolbar(showBack = false, getString(R.string.app_name))
        }
    }

    override fun setListeners() {
        binding.apply {
            tvInstructions.setOnClickListener { (activity as MainActivity).openInstructions() }

            tvPlay.setOnClickListener { activity?.showFragmentDialog(DecksDialog(this@MenuFragment)) }

            decks.tvMyDecks.setOnClickListener { navigateTo(actionMenuToMyDecks()) }
            decks.tvCreateDeck.setOnClickListener { navigateTo(actionMenuToCreateDeck()) }

            allCards.root.setOnClickListener { navigateToAllCards() }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> versionCheckCompleted()
                is MenuEvent.MenuCards -> setUpMenuCards(event.cards)
                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun versionCheckCompleted() {
        binding.loading.root.visibility = View.GONE
        viewModel.getMenuCards()
    }

    private fun setUpMenuCards(cards: List<Card>) {
        binding.allCards.rvMenuCards.apply {
            layoutManager = LinearLayoutManager(mContext, HORIZONTAL, false)
            adapter = MenuCardsAdapter(cards, this@MenuFragment)
        }
    }

    override fun onMenuCardClick() {
        navigateToAllCards()
    }

    override fun startGame(deckId: String) = navigateTo(actionMenuToDashboard(deckId, SYSTEM_DECK))

    private fun navigateToAllCards() = navigateTo(actionMenuToAllCards())

    private fun navigateTo(directions: NavDirections) = findNavController().navigate(directions)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}