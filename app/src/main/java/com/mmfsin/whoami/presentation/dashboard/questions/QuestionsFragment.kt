package com.mmfsin.whoami.presentation.dashboard.questions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentQuestionsBinding
import com.mmfsin.whoami.presentation.dialogs.selected.SelectedCardDialog
import com.mmfsin.whoami.utils.setExpandableView
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFragment(private val selectedCardId: String) :
    BaseFragment<FragmentQuestionsBinding, QuestionsViewModel>() {

    override val viewModel: QuestionsViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentQuestionsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUI() {}

    override fun setListeners() {
        binding.apply {
            tvNewQuestion.setOnClickListener { }

            tvAllQuestions.setOnClickListener { }

            tvMyCard.setOnClickListener {
                activity?.showFragmentDialog(SelectedCardDialog.newInstance(selectedCardId))
            }

            cvWhatNow.setOnClickListener { setExpandableView(detailsWhatNow, llWhatNow) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is QuestionsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}