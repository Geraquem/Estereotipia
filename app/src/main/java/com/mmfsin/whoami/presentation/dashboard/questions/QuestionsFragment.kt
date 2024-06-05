package com.mmfsin.whoami.presentation.dashboard.questions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.base.bedrock.BedRockActivity
import com.mmfsin.whoami.databinding.FragmentQuestionsBinding
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.selected.SelectedCardDialog
import com.mmfsin.whoami.presentation.dashboard.questions.minihelp.MiniHelpSheet
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.QuestionsListSheet
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.interfaces.INewQuestionListener
import com.mmfsin.whoami.presentation.dashboard.viepager.interfaces.IViewPagerListener
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

    override fun onResume() {
        checkIfGameFinished()
        super.onResume()
    }

    override fun setUI() {
        binding.apply {
            loading.root.visibility = View.VISIBLE

            tvNewQuestion.setOnClickListener { activity?.showFragmentDialog(MiniHelpSheet()) }
        }
    }

    private fun checkIfGameFinished() {
        binding.apply {
            if ((activity as BedRockActivity).isGameFinished) {
                tvNewQuestion.isEnabled = false
                tvNewQuestion.alpha = 0.75f
            } else {
                tvNewQuestion.isEnabled = true
                tvNewQuestion.alpha = 1f
            }
        }
    }

    override fun setListeners() {
        binding.apply {


//            tvNewQuestion.setOnClickListener {
//                questions?.let { list ->
//                    if (list.isNotEmpty()) {
//                        if (cont < NUM_OF_QUESTIONS) {
//                            val question = list[cont]
//                            questionsDone.add(question)
//                            activity?.showFragmentDialog(
//                                NewQuestionDialog.newInstance(this@QuestionsFragment, question)
//                            )
//                            cont++
//                        } else activity?.showFragmentDialog(NewQuestionDialog.newInstance(this@QuestionsFragment))
//                    } else viewModel.getQuestions()
//                }
//            }

//            tvAllQuestions.setOnClickListener { showAllQuestions() }

            tvMyCard.setOnClickListener {
                activity?.showFragmentDialog(SelectedCardDialog.newInstance(selectedCardId))
            }


//            llWhatNow.setOnClickListener {  }
//            cvWhatNow.setOnClickListener { setExpandableView(detailsWhatNow.linear, llWhatNow) }
//            cvButtons.setOnClickListener { setExpandableView(detailsButtons.linear, llButtons) }
//            cvWhenEnds.setOnClickListener { setExpandableView(detailsWhenEnds.linear, llWhenEnds) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is QuestionsEvent.GetQuestions -> {
                    questions = event.questions
                    finishFlow()
                }

                is QuestionsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun showAllQuestions() =
        activity?.showFragmentDialog(QuestionsListSheet(questionsDone.toList()))

    override fun goToAllQuestions() {
        showAllQuestions()
    }

    override fun viewCards() = listener.openCardsView()

    private fun finishFlow() {
        binding.apply {
            loading.root.visibility = View.GONE
//            countDown(300) {
//                svBottom.visibility = View.VISIBLE
//                svBottom.animateY(0f, 500)
//            }
        }
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}