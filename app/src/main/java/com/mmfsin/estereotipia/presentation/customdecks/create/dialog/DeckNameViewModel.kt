package com.mmfsin.estereotipia.presentation.customdecks.create.dialog

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.CreateCustomDeckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeckNameViewModel @Inject constructor(
    private val createCustomDeckUseCase: CreateCustomDeckUseCase
) : BaseViewModel<DeckNameEvent>() {

    fun createDeck(name: String, cards: List<String>) {
        executeUseCase(
            { createCustomDeckUseCase.execute(CreateCustomDeckUseCase.Params(name, cards)) },
            { _event.value = DeckNameEvent.CreatedCompleted },
            { _event.value = DeckNameEvent.SomethingWentWrong }
        )
    }
}