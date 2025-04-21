package com.mmfsin.estereotipia.presentation.dashboard.phrases.phrases

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetPhrasesUseCase
import com.mmfsin.estereotipia.domain.usecases.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhrasesViewModel @Inject constructor(
    private val getPhrasesUseCase: GetPhrasesUseCase
) : BaseViewModel<PhrasesEvent>() {

    fun getPhrases() {
        executeUseCase(
            { getPhrasesUseCase.execute() },
            { result ->
                _event.value = result?.let { PhrasesEvent.GetPhrases(result) }
                    ?: run { PhrasesEvent.SomethingWentWrong }
            },
            { _event.value = PhrasesEvent.SomethingWentWrong }
        )
    }
}