package com.mmfsin.whoami.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMenuBinding
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToAllCards
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToCreateDeck
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToDashboard
import com.mmfsin.whoami.presentation.menu.MenuFragmentDirections.Companion.actionMenuToMyDecks
import com.mmfsin.whoami.presentation.menu.adapter.MenuViewPagerAdapter
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
            setUpViewPager()
        }
    }

    private fun setToolbar() {
        (activity as MainActivity).apply {
            this.inDashboard = false
            hideToolbar()
        }
    }

    override fun setListeners() {
        binding.apply {
            instructions.setOnClickListener { (activity as MainActivity).openInstructions() }
            llPlay.setOnClickListener { activity?.showFragmentDialog(DecksDialog(this@MenuFragment)) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> versionCheckCompleted()
                is MenuEvent.MenuCards -> {}
                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun versionCheckCompleted() {
        binding.loading.root.visibility = View.GONE
        viewModel.getMenuCards()
    }

    private fun setUpViewPager() {
        binding.apply {
            activity?.let {
                viewPager.adapter = MenuViewPagerAdapter(it, this@MenuFragment)
                TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
            }
            loading.root.visibility = View.GONE
        }
    }

    override fun startGame(deckId: String) = navigateTo(actionMenuToDashboard(deckId, SYSTEM_DECK))
    override fun openMyDecks() = navigateTo(actionMenuToMyDecks())
    override fun openCreateDeck() = navigateTo(actionMenuToCreateDeck())
    override fun openAllCards() = navigateTo(actionMenuToAllCards())

    private fun navigateTo(directions: NavDirections) = findNavController().navigate(directions)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}