package com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmfsin.estereotipia.base.BaseFragment
import com.mmfsin.estereotipia.base.bedrock.BedRockActivity
import com.mmfsin.estereotipia.databinding.FragmentQuestionsBinding
import com.mmfsin.estereotipia.domain.models.GamePhrase
import com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases.adapter.PhrasesListAdapter
import com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases.dialogs.NewPhraseDialog
import com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases.listeners.INewPhraseListener
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.selected.SelectedCardDialog
import com.mmfsin.estereotipia.presentation.dashboard.whoiswho.questions.dialogs.MiniHelpSheet
import com.mmfsin.estereotipia.utils.NUM_OF_QUESTIONS
import com.mmfsin.estereotipia.utils.countDown
import com.mmfsin.estereotipia.utils.showErrorDialog
import com.mmfsin.estereotipia.utils.showFragmentDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhrasesFragment(private val selectedCardId: String) :
    BaseFragment<FragmentQuestionsBinding, PhrasesViewModel>(), INewPhraseListener {

    override val viewModel: PhrasesViewModel by viewModels()

    private var totalQuestions: List<GamePhrase>? = null
    private val questionsDone = mutableListOf<GamePhrase>()
    private var cont = 0

    private var phrasesAdapter: PhrasesListAdapter? = null

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentQuestionsBinding.inflate(inflater, container, false)

    override fun onResume() {
        viewModel.getPhrases()
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
                            val question = list[cont]
                            questionsDone.add(question)
                            activity?.showFragmentDialog(
                                NewPhraseDialog.newInstance(question, this@PhrasesFragment)
                            )
                            cont++
                            countDown(500) {
                                if (cont >= NUM_OF_QUESTIONS) disableNewQuestionBtn()
                                else tvNewQuestion.isEnabled = true
                                setUpQuestionList()
                            }
                        }
                    } else viewModel.getPhrases()
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
                is PhrasesEvent.GetPhrases -> {
                    totalQuestions = event.phrases
                    binding.loading.root.visibility = View.GONE
                }

                is PhrasesEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpQuestionList() {
        binding.apply {
            llQuestions.isVisible = true
            if (phrasesAdapter == null) {
                rvAllQuestions.apply {
                    layoutManager = LinearLayoutManager(activity?.applicationContext)
                    phrasesAdapter = PhrasesListAdapter(questionsDone)
                    adapter = phrasesAdapter
                }
            } else {
                phrasesAdapter?.notifyItemInserted(questionsDone.size - 1)
            }
        }
    }

    override fun answerPhrase(phrase: GamePhrase, answer: String) {
        phrasesAdapter?.updateQuestionAnswer(phrase.id, answer)
    }

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}