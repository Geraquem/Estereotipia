package com.mmfsin.estereotipia.presentation.allcards

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
import com.mmfsin.estereotipia.presentation.allcards.adapter.AllCardsAdapter
import com.mmfsin.estereotipia.presentation.allcards.dialogs.AllCardDialog
import com.mmfsin.estereotipia.presentation.allcards.interfaces.IAllCardsListener
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCardsFragment : BaseFragment<FragmentCardsBinding, AllCardsViewModel>(),
    IAllCardsListener {

    override val viewModel: AllCardsViewModel by viewModels()
    private lateinit var mContext: Context

    private var mCards = listOf<Card>()
    private var columns = 3

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCardsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllCards()
    }

    override fun setUI() {
        (activity as BedRockActivity).setUpToolbar(getString(R.string.menu_all_cards))
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
                is AllCardsEvent.GetCards -> {
                    mCards = event.cards
                    setUpCards(3, event.cards)
                }

                is AllCardsEvent.SomethingWentWrong -> activity?.showErrorDialog()
            }
        }
    }

    private fun setUpCards(columns: Int, cards: List<Card>) {
        binding.rvCards.apply {
            layoutManager = StaggeredGridLayoutManager(columns, StaggeredGridLayoutManager.VERTICAL)
            adapter = AllCardsAdapter(columns, cards, this@AllCardsFragment)
        }
    }

    override fun onCardClick(cardId: String) {
        activity?.showFragmentDialog(AllCardDialog.newInstance(cardId))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}