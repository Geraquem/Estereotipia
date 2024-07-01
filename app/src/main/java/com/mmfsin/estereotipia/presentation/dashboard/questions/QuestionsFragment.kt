package com.mmfsin.estereotipia.presentation.dashboard.questions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.estereotipia.base.BaseFragmentNoVM
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentQuestionsBinding
import com.mmfsin.estereotipia.domain.models.GameQuestion
import com.mmfsin.estereotipia.presentation.dashboard.cards.dialogs.selected.SelectedCardDialog
import com.mmfsin.estereotipia.presentation.dashboard.questions.dialogs.MiniHelpSheet
import com.mmfsin.estereotipia.presentation.dashboard.questions.dialogs.NewQuestionDialog
import com.mmfsin.estereotipia.presentation.dashboard.questions.dialogs.adapter.QuestionsListAdapter
import com.mmfsin.estereotipia.presentation.dashboard.questions.dialogs.interfaces.INewQuestionListener
import com.mmfsin.estereotipia.presentation.dashboard.viepager.interfaces.IViewPagerListener
import com.mmfsin.estereotipia.utils.NUM_OF_QUESTIONS
import com.mmfsin.estereotipia.utils.countDown
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFragment(
    private val selectedCardId: String,
    private val questions: List<GameQuestion>,
    private val listener: IViewPagerListener
) : BaseFragmentNoVM<FragmentQuestionsBinding>(), INewQuestionListener {

    private val questionsDone = mutableListOf<GameQuestion>()
    private var cont = 0

    private var questionsAdapter: QuestionsListAdapter? = null

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentQuestionsBinding.inflate(inflater, container, false)

    override fun onResume() {
        checkIfGameFinished()
        super.onResume()
    }

    override fun setUI() {
        binding.apply {
            llQuestions.isVisible = false
        }
    }

    private fun checkIfGameFinished() {
        binding.apply {
            if ((activity as BedRockActivity).isGameFinished) disableNewQuestionBtn()
            else {
                tvNewQuestion.isEnabled = true
                tvNewQuestion.alpha = 1f
            }
        }
    }

    private fun disableNewQuestionBtn() {
        (activity as BedRockActivity).isGameFinished = true
        binding.apply {
            tvNewQuestion.isEnabled = false
            tvNewQuestion.alpha = 0.4f
            tvNewQuestion.elevation = 0f
        }
    }

    override fun setListeners() {
        binding.apply {
            tvNewQuestion.setOnClickListener {
                if (questions.isNotEmpty()) {
                    if (cont < NUM_OF_QUESTIONS) {
                        tvNewQuestion.isEnabled = false
                        val question = questions[cont]
                        questionsDone.add(question)
                        activity?.showFragmentDialog(
                            NewQuestionDialog.newInstance(question, this@QuestionsFragment)
                        )
                        cont++
                        countDown(500) {
                            if (cont >= NUM_OF_QUESTIONS) disableNewQuestionBtn()
                            else tvNewQuestion.isEnabled = true
                            setUpQuestionList()
                        }
                    }
                } else error()
            }

            tvMyCard.setOnClickListener {
                activity?.showFragmentDialog(SelectedCardDialog.newInstance(selectedCardId))
            }

            tvMiniHelp.setOnClickListener { activity?.showFragmentDialog(MiniHelpSheet()) }
        }
    }

    private fun setUpQuestionList() {
        binding.apply {
            llQuestions.isVisible = true
            if (questionsAdapter == null) {
                rvAllQuestions.apply {
                    layoutManager = LinearLayoutManager(activity?.applicationContext)
                    questionsAdapter = QuestionsListAdapter(questionsDone)
                    adapter = questionsAdapter
                }
            } else {
                questionsAdapter?.notifyItemInserted(questionsDone.size - 1)
            }
        }
    }

    override fun viewCards() = listener.openCardsView()

    override fun answer(question: GameQuestion, answer: Boolean) {
        questionsAdapter?.updateQuestionAnswer(question.id, answer)
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}