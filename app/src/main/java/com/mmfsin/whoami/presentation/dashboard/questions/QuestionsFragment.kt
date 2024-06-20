package com.mmfsin.whoami.presentation.dashboard.questions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.whoami.base.BaseFragment
import com.mmfsin.whoami.base.bedrock.BedRockActivity
import com.mmfsin.whoami.databinding.FragmentQuestionsBinding
import com.mmfsin.whoami.domain.models.GameQuestion
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.presentation.dashboard.cards.dialogs.selected.SelectedCardDialog
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.MiniHelpSheet
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.NewQuestionDialog
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.adapter.QuestionsListAdapter
import com.mmfsin.whoami.presentation.dashboard.questions.dialogs.interfaces.INewQuestionListener
import com.mmfsin.whoami.presentation.dashboard.viepager.interfaces.IViewPagerListener
import com.mmfsin.whoami.utils.NUM_OF_QUESTIONS
import com.mmfsin.whoami.utils.countDown
import com.mmfsin.whoami.utils.showErrorDialog
import com.mmfsin.whoami.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFragment(
    private val selectedCardId: String,
    private val listener: IViewPagerListener
) : BaseFragment<FragmentQuestionsBinding, QuestionsViewModel>(), INewQuestionListener {

    override val viewModel: QuestionsViewModel by viewModels()

    private var totalQuestions: List<Question>? = null
    private val questionsDone = mutableListOf<GameQuestion>()
    private var cont = 0

    private var questionsAdapter: QuestionsListAdapter? = null

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
                totalQuestions?.let { list ->
                    if (list.isNotEmpty()) {
                        if (cont < NUM_OF_QUESTIONS) {
                            tvNewQuestion.isEnabled = false
                            viewModel.saveGameQuestion(list[cont])
                        }
                    } else viewModel.getQuestions()
                }
            }

            tvMyCard.setOnClickListener {
                activity?.showFragmentDialog(SelectedCardDialog.newInstance(selectedCardId))
            }

            tvMiniHelp.setOnClickListener { activity?.showFragmentDialog(MiniHelpSheet()) }
        }
    }

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is QuestionsEvent.GetQuestions -> {
                    totalQuestions = event.questions
                    binding.loading.root.visibility = View.GONE
                }

                is QuestionsEvent.GameQuestionSaved -> gameQuestionSaved(event.gameQuestion)
                is QuestionsEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun gameQuestionSaved(gameQuestion: GameQuestion) {
        activity?.showFragmentDialog(
            NewQuestionDialog.newInstance(gameQuestion, this@QuestionsFragment)
        )
        questionsDone.add(gameQuestion)
        cont++
        countDown(500) {
            if (cont >= NUM_OF_QUESTIONS) disableNewQuestionBtn()
            else binding.tvNewQuestion.isEnabled = true

            setUpQuestionList()
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
        viewModel.saveGameQuestion()
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}