package com.mmfsin.whoami.presentation.menu.tabs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.IncludeMenuCardsBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.menu.MenuEvent
import com.mmfsin.whoami.presentation.menu.MenuViewModel
import com.mmfsin.whoami.presentation.menu.adapter.MenuCardsAdapter
import com.mmfsin.whoami.presentation.menu.listener.IMenuCardsListener
import com.mmfsin.whoami.presentation.menu.listener.IMenuListener
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuCardsTabFragment(val listener: IMenuListener) :
    BaseFragment<IncludeMenuCardsBinding, MenuViewModel>(), IMenuCardsListener {

    override val viewModel: MenuViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = IncludeMenuCardsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMenuCards()
    }

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            llMain.setOnClickListener { listener.openAllCards() }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> {}
                is MenuEvent.MenuCards -> setUpMenuCards(event.cards)
                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpMenuCards(cards: List<Card>) {
        binding.rvMenuCards.apply {
            layoutManager = LinearLayoutManager(mContext, HORIZONTAL, false)
            adapter = MenuCardsAdapter(cards, this@MenuCardsTabFragment)
        }
    }

    override fun onMenuCardClick() {
        listener.openAllCards()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}