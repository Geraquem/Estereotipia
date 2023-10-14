package com.mmfsin.whoami.presentation.decks.mydecks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentMyDecksBinding
import com.mmfsin.whoami.domain.models.MyDeck
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.decks.mydecks.MyDecksFragmentDirections.Companion.actionMyDecksToDashboard
import com.mmfsin.whoami.presentation.decks.mydecks.adapter.MyDecksAdapter
import com.mmfsin.whoami.presentation.decks.mydecks.dialogs.MyDeckDialog
import com.mmfsin.whoami.presentation.decks.mydecks.dialogs.delete.DeleteMyDeckDialog
import com.mmfsin.whoami.presentation.decks.mydecks.dialogs.edit.EditMyDeckDialog
import com.mmfsin.whoami.presentation.decks.mydecks.interfaces.IMyDeckListener
import com.mmfsin.whoami.presentation.models.DeckType.CUSTOM_DECK
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
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
            tvEmpty.visibility = View.GONE
        }
    }

    private fun setToolbar() {
        (activity as MainActivity).apply {
            this.inDashboard = false
            setUpToolbar(showBack = false, getString(R.string.my_decks_toolbar))
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MyDecksEvent.MyDecks -> setUpDecks(event.decks)
                is MyDecksEvent.FlowCompleted -> viewModel.getMyDecks()
                is MyDecksEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpDecks(decks: List<MyDeck>) {
        binding.apply {
            rvMyDecks.apply {
//                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                layoutManager = LinearLayoutManager(mContext)
                adapter = MyDecksAdapter(decks, this@MyDecksFragment)
            }
            tvEmpty.isVisible = decks.isEmpty()
            rvMyDecks.isVisible = decks.isNotEmpty()
        }
    }

    override fun onMyDeckClick(id: String) {
        activity?.showFragmentDialog(MyDeckDialog.newInstance(id, this@MyDecksFragment))
    }

    override fun playWithCustomDeck(id: String) =
        findNavController().navigate(actionMyDecksToDashboard(id, CUSTOM_DECK))

    override fun editName(id: String) {
        activity?.showFragmentDialog(EditMyDeckDialog.newInstance(id, this@MyDecksFragment))
    }

    override fun editCards(id: String) {
        TODO("Not yet implemented")
    }

    override fun editCompleted() = viewModel.getMyDecks()

    override fun confirmDeleteMyDeck(id: String) {
        activity?.showFragmentDialog(DeleteMyDeckDialog.newInstance(id, this@MyDecksFragment))
    }

    override fun deleteMyDeck(id: String) = viewModel.deleteMyDeck(id)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}