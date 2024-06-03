package com.mmfsin.whoami.presentation.allcards

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.base.bedrock.BedRockActivity
import com.mmfsin.whoami.databinding.FragmentAllCardsBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.allcards.adapter.AllCardsAdapter
import com.mmfsin.whoami.presentation.allcards.dialogs.AllCardDialog
import com.mmfsin.whoami.presentation.allcards.interfaces.IAllCardsListener
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCardsFragment : BaseFragment<FragmentAllCardsBinding, AllCardsViewModel>(),
    IAllCardsListener {

    override val viewModel: AllCardsViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentAllCardsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllCards()
    }

    override fun setUI() {
        (activity as BedRockActivity).setUpToolbar(getString(R.string.menu_all_cards))
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is AllCardsEvent.GetCards -> setUpAllCards(event.cards)
                is AllCardsEvent.SomethingWentWrong -> activity?.showErrorDialog()
            }
        }
    }

    private fun setUpAllCards(cards: List<Card>) {
        binding.rvAllCards.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = AllCardsAdapter(cards, this@AllCardsFragment)
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