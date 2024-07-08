package com.mmfsin.estereotipia.presentation.customdecks.seecards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mmfsin.estereotipia.R
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentCardsBinding
import com.mmfsin.estereotipia.domain.models.Card
import com.mmfsin.estereotipia.presentation.allcards.dialogs.AllCardDialog
import com.mmfsin.estereotipia.presentation.allcards.interfaces.IAllCardsListener
import com.mmfsin.estereotipia.presentation.customdecks.seecards.adapter.SeeCardsAdapter
import com.mmfsin.estereotipia.utils.DECK_ID
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeCardsFragment : BaseFragment<FragmentCardsBinding, SeeCardsViewModel>(),
    IAllCardsListener {

    override val viewModel: SeeCardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var deckId: String? = null
    private var mCards = listOf<Card>()
    private var columns = 3

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCardsBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let { deckId = it.getString(DECK_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckId?.let { id -> viewModel.getDeck(id) } ?: run { error() }
    }

    override fun setUI() {
        binding.apply {
            topSpace.isVisible = false
        }
    }

    override fun setListeners() {
        binding.apply {
            rlZoom.setOnClickListener {
                columns = if (columns == 3) 2 else 3
                val zoom = if (columns == 3) R.drawable.ic_zoom_in else R.drawable.ic_zoom_out
                ivZoom.setImageResource(zoom)
                setUpCards(columns, mCards)
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is SeeCardsEvent.GetDeck -> {
                    setUpToolbar(event.deck.name)
                    viewModel.getCards(event.deck.id)
                }

                is SeeCardsEvent.DeckCards -> {
                    mCards = event.cards
                    setUpCards(3, event.cards)
                }

                is SeeCardsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpToolbar(deckName: String) =
        (activity as BedRockActivity).apply {
            inDashboard = false
            setUpToolbar(deckName, instructionsVisible = false)
        }


    private fun setUpCards(columns: Int, cards: List<Card>) {
        binding.rvCards.apply {
            layoutManager = StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
            adapter = SeeCardsAdapter(columns, cards, this@SeeCardsFragment)
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