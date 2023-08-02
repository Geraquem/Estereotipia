package com.mmfsin.whoami.presentation.decks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentDecksBinding
import com.mmfsin.whoami.domain.models.Deck
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.decks.adapter.DeckAdapter
import com.mmfsin.whoami.presentation.decks.interfaces.IDeckListener
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DecksFragment : BaseFragment<FragmentDecksBinding, DecksViewModel>(), IDeckListener {

    override val viewModel: DecksViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentDecksBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDecks()
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

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DecksEvent.GetDecks -> setUpDecks(event.result)
                is DecksEvent.SomethingWentWrong -> error()
            }
        }
    }

    override fun onDeckClick(deckId: String) {
//        activity?.let {
//            val dialog = SelectRolDialog(deckId)
//            it.let { dialog.show(it.supportFragmentManager, "") }
//        }

        /** DELETE */
        findNavController().navigate(DecksFragmentDirections.actionDeckToDashboardCaptain(deckId))
//        findNavController().navigate(DecksFragmentDirections.actionDeckToDashboardPeople(deckId))
    }

    private fun setUpDecks(decks: List<Deck>) {
        binding.rvDecks.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = DeckAdapter(decks, this@DecksFragment)
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}