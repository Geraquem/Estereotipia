package com.mmfsin.estereotipia.presentation.customdecks.seecards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentSeeCardsBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.allcards.dialogs.AllCardDialog
import com.mmfsin.estereotipia.presentation.allcards.interfaces.IAllCardsListener
import com.mmfsin.estereotipia.presentation.customdecks.seecards.adapter.SeeCardsAdapter
import com.mmfsin.estereotipia.utils.DECK_ID
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeCardsFragment : BaseFragment<FragmentSeeCardsBinding, SeeCardsViewModel>(),
    IAllCardsListener {

    override val viewModel: SeeCardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var deckId: String? = null

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentSeeCardsBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { deckId = it.getString(DECK_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckId?.let { id -> viewModel.getDeck(id) } ?: run { error() }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is SeeCardsEvent.GetDeck -> {
                    setUpToolbar(event.deck.name)
                    viewModel.getCards(event.deck.id)
                }

                is SeeCardsEvent.DeckCards -> setUpCards(event.cards)
                is SeeCardsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpToolbar(deckName: String) =
        (activity as BedRockActivity).apply {
            inDashboard = false
            setUpToolbar(deckName)
        }


    private fun setUpCards(cards: List<Card>) {
        binding.rvSeeCards.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = SeeCardsAdapter(cards, this@SeeCardsFragment)
        }
    }

    override fun onCardClick(cardId: String) {
        activity?.showFragmentDialog(AllCardDialog.newInstance(cardId))
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}