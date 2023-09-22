package com.mmfsin.whoami.presentation.dashboard.questions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.databinding.FragmentQuestionsBinding
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.selected.SelectedCardDialog
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.NewQuestionDialog
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.QuestionsListDialog
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.interfaces.INewQuestionListener
import com.mmfsin.whoami.presentation.dashboard.viepager.interfaces.IViewPagerListener
import com.mmfsin.whoami.utils.setExpandableView
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFragment(
    private val selectedCardId: String,
    private val listener: IViewPagerListener
) : BaseFragment<FragmentQuestionsBinding, QuestionsViewModel>(), INewQuestionListener {

    override val viewModel: QuestionsViewModel by viewModels()

    private var questions: List<Question>? = null
    private val questionsDone = ArrayList<Question>()
    private var cont = 0

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentQuestionsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestions()
    }

    override fun setUI() {
        binding.apply {
            loading.root.visibility = View.VISIBLE
        }
    }

    override fun setListeners() {
        binding.apply {
            tvNewQuestion.setOnClickListener {
                questions?.let { list ->
                    if (cont < list.size) {
                        val question = list[cont]
                        questionsDone.add(question)
                        activity?.showFragmentDialog(
                            NewQuestionDialog.newInstance(this@QuestionsFragment, question)
                        )
                        cont++
                    } else activity?.showFragmentDialog(NewQuestionDialog.newInstance(this@QuestionsFragment))
                }
            }

            tvAllQuestions.setOnClickListener { showAllQuestions() }

            tvMyCard.setOnClickListener {
                activity?.showFragmentDialog(SelectedCardDialog.newInstance(selectedCardId))
            }

            cvWhatNow.setOnClickListener { setExpandableView(detailsWhatNow.linear, llWhatNow) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is QuestionsEvent.GetQuestions -> {
                    questions = event.questions
                    binding.loading.root.visibility = View.GONE
                }
                is QuestionsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun showAllQuestions() =
        activity?.showFragmentDialog(QuestionsListDialog(questionsDone.toList()))

    override fun goToAllQuestions() {
        showAllQuestions()
    }

    override fun viewCards() {
        listener.openCardsView()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}