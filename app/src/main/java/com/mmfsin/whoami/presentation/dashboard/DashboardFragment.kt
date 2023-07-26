package com.mmfsin.whoami.presentation.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentDashboardBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.dashboard.adapter.CardsAdapter
import com.mmfsin.whoami.presentation.dashboard.dialog.CardInfoDialog
import com.mmfsin.whoami.presentation.dashboard.interfaces.ICardListener
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    ICardListener {

    override val viewModel: DashboardViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCards("cardDeckId")
    }

    override fun setUI() {
        binding.apply {
            setToolbar()
        }
    }

    private fun setToolbar() {
        (activity as MainActivity).apply {
            this.inDashboard = true
            setUpToolbar(showBack = true, "Name of Deck")
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is DashboardEvent.GetCards -> setUpCards(event.result)
                is DashboardEvent.SomethingWentWrong -> error()
            }
        }
    }

    override fun onCardClick(id: String) {
        activity?.let {
            val dialogFragment = CardInfoDialog.newInstance()
            dialogFragment.show(it.supportFragmentManager, "")
        }
    }

    private fun setUpCards(cards: List<Card>) {
        binding.rvCards.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = CardsAdapter(cards, this@DashboardFragment)
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}