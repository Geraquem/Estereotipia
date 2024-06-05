package com.mmfsin.whoami.presentation.customdecks.create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mmfsin.whoami.R
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.base.bedrock.BedRockActivity
import com.mmfsin.whoami.databinding.FragmentCreateDeckBinding
import com.mmfsin.whoami.domain.models.CreateDeckCard
import com.mmfsin.whoami.presentation.allcards.dialogs.AllCardDialog
import com.mmfsin.whoami.presentation.customdecks.create.adapter.NewDeckCardsAdapter
import com.mmfsin.whoami.presentation.customdecks.create.dialog.DeckNameDialog
import com.mmfsin.whoami.presentation.customdecks.create.interfaces.ICreateDeckCardListener
import com.mmfsin.whoami.presentation.customdecks.snackbar.CustomSnackbar
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDeckFragment : BaseFragment<FragmentCreateDeckBinding, CreateDeckViewModel>(),
    ICreateDeckCardListener {

    override val viewModel: CreateDeckViewModel by viewModels()

    private lateinit var mContext: Context

    private var mAdapter: NewDeckCardsAdapter? = null
    private val cardList = mutableListOf<String>()

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentCreateDeckBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCards()
    }

    override fun setUI() {
        binding.apply {
            setToolbar()
            clBtnOk.visibility = View.GONE
        }
    }

    private fun setToolbar() {
        (activity as BedRockActivity).setUpToolbar(getString(R.string.my_decks_create_new_toolbar))
    }

    override fun setListeners() {
        binding.apply {
            btnAccept.setOnClickListener {
                activity?.showFragmentDialog(
                    DeckNameDialog.newInstance(cardList, this@CreateDeckFragment)
                )
            }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is CreateDeckEvent.AllCards -> setUpCards(event.cards)
                is CreateDeckEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCards(cards: List<CreateDeckCard>) {
        binding.rvCards.apply {
            (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            mAdapter = NewDeckCardsAdapter(cards, this@CreateDeckFragment)
            adapter = mAdapter
        }
    }

    override fun onCardClick(id: String) {
        activity?.showFragmentDialog(AllCardDialog.newInstance(id))
    }

    override fun onCheckClick(position: Int, selected: Boolean, id: String) {
        if (selected) cardList.add(id)
        else {
            if (cardList.contains(id)) cardList.remove(id)
        }
        checkBtnVisibility()
        totalCardsText()
        mAdapter?.notifyItemChanged(position)
    }

    private fun checkBtnVisibility() {
        binding.clBtnOk.isVisible = cardList.size > 1
    }

    private fun totalCardsText() {
        val total = cardList.size.toString()
        binding.tvNumberCards.text = getString(R.string.custom_decks_total_cards, total)
    }

    override fun flowCompleted() {
        CustomSnackbar.make(binding.clMain, Snackbar.LENGTH_SHORT).show()
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}