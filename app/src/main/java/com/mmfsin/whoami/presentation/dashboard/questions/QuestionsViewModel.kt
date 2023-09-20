package com.mmfsin.whoami.presentation.dashboard.questions

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
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
}