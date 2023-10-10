package com.mmfsin.whoami.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMenuBinding
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.decks.dialogs.MyDecksDialog
import com.mmfsin.whoami.presentation.decks.interfaces.IMyDecksListener
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToAllCards
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToCreateDeck
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToDashboard
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToMyDecks
import com.mmfsin.whoami.presentation.menu.decks.DecksDialog
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IMyDecksListener {

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

            tvPlay.setOnClickListener {
                activity?.showFragmentDialog(DecksDialog() { id -> navigateToDashboard(id) })
            }

            tvMyDecks.setOnClickListener { activity?.showFragmentDialog(MyDecksDialog(this@MenuFragment)) }
            tvAllCards.setOnClickListener { findNavController().navigate(actionMenuToAllCards()) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> binding.loading.root.visibility = View.GONE
                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun navigateToDashboard(deckId: String) {
        findNavController().navigate(actionMenuToDashboard(deckId))
    }

    override fun openMyDecks() {
        findNavController().navigate(actionMenuToMyDecks())
    }

    override fun createDeck() {
        findNavController().navigate(actionMenuToCreateDeck())
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}