package com.mmfsin.whoami.presentation.customdecks.mydecks

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
import com.mmfsin.whoami.base.bedrock.BedRockActivity
import com.mmfsin.whoami.databinding.FragmentMyDecksBinding
import com.mmfsin.whoami.domain.models.MyDeck
import com.mmfsin.whoami.presentation.customdecks.mydecks.MyDecksFragmentDirections.Companion.actionMyDecksToCreateNewDeck
import com.mmfsin.whoami.presentation.customdecks.mydecks.adapter.MyDecksAdapter
import com.mmfsin.whoami.presentation.customdecks.mydecks.dialogs.MyDeckDialog
import com.mmfsin.whoami.presentation.customdecks.mydecks.dialogs.delete.DeleteMyDeckDialog
import com.mmfsin.whoami.presentation.customdecks.mydecks.dialogs.edit.EditMyDeckDialog
import com.mmfsin.whoami.presentation.customdecks.mydecks.interfaces.IMyDeckListener
import com.mmfsin.whoami.utils.BEDROCK_BOOLEAN_ARGS
import com.mmfsin.whoami.utils.BEDROCK_STR_ARGS
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDecksFragment : BaseFragment<FragmentMyDecksBinding, MyDecksViewModel>(), IMyDeckListener {

    override val viewModel: MyDecksViewModel by viewModels()
    private lateinit var mContext: Context

    private var openCreateNewDeck = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMyDecksBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        openCreateNewDeck = activity?.intent?.getBooleanExtra(BEDROCK_BOOLEAN_ARGS, false) ?: false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (openCreateNewDeck) {
            openCreateNewDeck = false
            createNewDeck()
        }
        viewModel.getMyDecks()
    }

    override fun setUI() {
        binding.apply {
            tvEmpty.visibility = View.GONE
            (activity as BedRockActivity).setUpToolbar(getString(R.string.my_decks_toolbar))
        }
    }

    override fun setListeners() {
        binding.apply {
            btnCreateDeck.setOnClickListener { createNewDeck() }
        }
    }

    private fun createNewDeck() = findNavController().navigate(actionMyDecksToCreateNewDeck())

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

    override fun playWithCustomDeck(id: String) {}

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