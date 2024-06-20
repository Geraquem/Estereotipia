package com.mmfsin.whoami.presentation.dashboard.questions

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.models.Question
import com.mmfsin.whoami.domain.usecases.GetQuestionsUseCase
import com.mmfsin.whoami.domain.usecases.SaveGameQuestionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val saveGameQuestionUseCase: SaveGameQuestionUseCase
) : BaseViewModel<QuestionsEvent>() {

    fun getQuestions() {
        executeUseCase(
            { getQuestionsUseCase.execute() },
            { result ->
                _event.value = result?.let { QuestionsEvent.GetQuestions(result) }
                    ?: run { QuestionsEvent.SomethingWentWrong }
            },
            { _event.value = QuestionsEvent.SomethingWentWrong }
        )
    }

    fun saveGameQuestion(question: Question) {
        executeUseCase(
            { saveGameQuestionUseCase.execute(SaveGameQuestionUseCase.Params(question.question)) },
            { result -> _event.value = QuestionsEvent.GameQuestionSaved(result) },
            { _event.value = QuestionsEvent.SomethingWentWrong }
        )
    }

    fun updateGameQuestion(gameQuestionId: String, answer: Boolean){

    }
}