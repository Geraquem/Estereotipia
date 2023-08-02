package com.mmfsin.whoami.presentation.dashboard.captain

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentDashboardCaptainBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.dashboard.captain.adapter.CaptainCardsAdapter
import com.mmfsin.whoami.presentation.dashboard.captain.dialog.CaptainCardInfoDialog
import com.mmfsin.whoami.presentation.dashboard.captain.interfaces.ICaptainCardListener
import com.mmfsin.whoami.utils.DECK_ID
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaptainFragment : BaseFragment<FragmentDashboardCaptainBinding, CaptainViewModel>(),
    ICaptainCardListener {

    override val viewModel: CaptainViewModel by viewModels()
    private lateinit var mContext: Context

    private var gameReady: Boolean = false
    private var deckId: String? = null
    private var cardsAdapter: CaptainCardsAdapter? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentDashboardCaptainBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { deckId = it.getString(DECK_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckId?.let { id -> viewModel.getActualDeck(id) } ?: run { error() }
    }

    override fun setUI() {}
    override fun setListeners() {}

    private fun setToolbar(deckName: String) {
        (activity as MainActivity).apply {
            this.inDashboard = true
            setUpToolbar(showBack = true, deckName)
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CaptainEvent.GetActualDeck -> {
                    setToolbar(event.deck.name)
                    viewModel.getCards(event.deck.id)
                }
                is CaptainEvent.GetCards -> setUpCards(event.cards)
                is CaptainEvent.UpdateCard -> {
                    gameReady = true
                    cardsAdapter?.updateSelectedCard(event.cardId)
                }
                is CaptainEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCards(cards: List<Card>) {
        binding.rvCards.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            cardsAdapter = CaptainCardsAdapter(cards, this@CaptainFragment)
            adapter = cardsAdapter
        }
    }

    override fun onCardClick(cardId: String) {
        activity?.let {
            val dialogFragment = CaptainCardInfoDialog.newInstance(cardId, gameReady)
            dialogFragment.show(it.supportFragmentManager, "")
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}