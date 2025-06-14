package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.questions

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.estereotipia.domain.usecases.GetQuestionsUseCase
import com.mmfsin.estereotipia.domain.usecases.GetRandomCardUseCase
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