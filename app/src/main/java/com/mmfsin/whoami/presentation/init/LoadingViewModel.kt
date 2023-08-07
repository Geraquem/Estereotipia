package com.mmfsin.whoami.presentation.init

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetDecksUseCase
import com.mmfsin.whoami.domain.usecases.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val getDecksUseCase: GetDecksUseCase,
    private val getQuestionsUseCase: GetQuestionsUseCase
) : BaseViewModel<LoadingEvent>() {

    fun getQuestions() {
        executeUseCase(
            { getQuestionsUseCase.execute() },
            { result ->
                _event.value = result?.let { LoadingEvent.GetQuestions(it) }
                    ?: run { LoadingEvent.SomethingWentWrong }
            },
            { _event.value = LoadingEvent.SomethingWentWrong }
        )
    }
}