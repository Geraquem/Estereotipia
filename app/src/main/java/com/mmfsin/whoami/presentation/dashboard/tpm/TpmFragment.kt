package com.mmfsin.whoami.presentation.dashboard.tpm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentDashboardTpmBinding
import com.mmfsin.whoami.domain.models.Card
import com.mmfsin.whoami.presentation.MainActivity
import com.mmfsin.whoami.presentation.dashboard.captain.dialog.CaptainCardInfoDialog
import com.mmfsin.whoami.presentation.dashboard.people.dialog.PeopleCardInfoDialog
import com.mmfsin.whoami.presentation.dashboard.tpm.adapter.TpmAdapter
import com.mmfsin.whoami.presentation.dashboard.tpm.dialog.TpmQuestionsDialog
import com.mmfsin.whoami.presentation.dashboard.tpm.dialog.TpmSelectDialog
import com.mmfsin.whoami.presentation.dashboard.tpm.interfaces.ITpmListener
import com.mmfsin.whoami.utils.DECK_ID
import com.mmfsin.whoami.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TpmFragment : BaseFragment<FragmentDashboardTpmBinding, TpmViewModel>(), ITpmListener {

    override val viewModel: TpmViewModel by viewModels()
    private lateinit var mContext: Context

    private var deckId: String? = null
    private var tpmAdapter: TpmAdapter? = null

    private var selectedReady = false

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentDashboardTpmBinding.inflate(inflater, container, false)

    override fun getBundleArgs() {
        arguments?.let {
            deckId = it.getString(DECK_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deckId?.let { id -> viewModel.getActualDeck(id) } ?: run { error() }
    }

    override fun setUI() {}
    override fun setListeners() {}

    private fun setToolbar(deckName: String) {
        (activity as MainActivity).apply {
            this.inDashboard = true
            setUpToolbar(showBack = true, deckName)
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is TpmEvent.GetActualDeck -> {
                    setToolbar(event.deck.name)
                    viewModel.getCards(event.deck.id)
                }
                is TpmEvent.GetCards -> setUpCardsToSelect(event.cards)
                is TpmEvent.UpdateCard -> actionOnCard(event.cardId)
                is TpmEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpCardsToSelect(cards: List<Card>) {
        activity?.let {
            it.let { TpmSelectDialog().show(it.supportFragmentManager, "") }
        }
        binding.rvCards.apply {
            layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            tpmAdapter = TpmAdapter(cards, this@TpmFragment)
            adapter = tpmAdapter
        }
    }

    override fun onCardClick(cardId: String) {
        if (!selectedReady) {
            activity?.let {
                val dialogFragment = CaptainCardInfoDialog.newInstance(cardId)
                dialogFragment.show(it.supportFragmentManager, "")
            }
        } else {
            activity?.let {
                val dialogFragment = PeopleCardInfoDialog.newInstance(cardId)
                dialogFragment.show(it.supportFragmentManager, "")
            }
        }
    }

    private fun actionOnCard(cardId: String) {
        if (!selectedReady) {
            Toast.makeText(mContext, "selected false", Toast.LENGTH_SHORT).show()
            selectedReady = true
            activity?.let {
                val dialogFragment = TpmQuestionsDialog.newInstance()
                dialogFragment.show(it.supportFragmentManager, "")
            }
            tpmAdapter?.updateSelectedCard(cardId)

        } else {
            Toast.makeText(mContext, "selected true", Toast.LENGTH_SHORT).show()
            tpmAdapter?.updateDiscardedCards(cardId)
        }
    }

//    override fun onDiscardClick(cardId: String) = viewModel.discardCard(cardId, updateFlow = false)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}