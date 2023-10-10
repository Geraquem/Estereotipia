package com.mmfsin.whoami.presentation.decks.mydecks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMyDecksBinding
import com.mmfsin.whoami.domain.models.MyDeck
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.decks.mydecks.adapter.MyDecksAdapter
import com.mmfsin.whoami.presentation.decks.mydecks.interfaces.IMyDeckListener
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDecksFragment : BaseFragment<FragmentMyDecksBinding, MyDecksViewModel>(), IMyDeckListener {

    override val viewModel: MyDecksViewModel by viewModels()
    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMyDecksBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMyDecks()
    }

    override fun setUI() {
        binding.apply {
            setToolbar()
        }
    }

    private fun setToolbar() {
        (activity as MainActivity).apply {
            setUpToolbar(showBack = false, getString(R.string.my_decks_toolbar))
        }
    }

    override fun setListeners() {
        binding.apply {

        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyDecksEvent.MyDecks -> setUpDecks(event.decks)
                is MyDecksEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpDecks(decks: List<MyDeck>) {
        binding.rvMyDecks.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            adapter = MyDecksAdapter(decks, this@MyDecksFragment)
        }
    }

    override fun onMyDeckClick(id: String) {

    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}