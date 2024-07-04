package com.mmfsin.estereotipia.presentation.customdecks.shared

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.CreateCustomDeckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedDeckViewModel @Inject constructor(
    private val createCustomDeckUseCase: CreateCustomDeckUseCase
) : BaseViewModel<SharedDeckEvent>() {

    fun addDeck(name: String, cards: List<String>) {
        executeUseCase(
            { createCustomDeckUseCase.execute(CreateCustomDeckUseCase.Params(name, cards)) },
            { _event.value = SharedDeckEvent.AddedCompleted },
            { _event.value = SharedDeckEvent.SomethingWentWrong }
        )
    }
}