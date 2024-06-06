package com.mmfsin.whoami.presentation.customdecks.editcards

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditCardsViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
) : BaseViewModel<EditCardsEvent>() {

    fun getDeck(id: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { EditCardsEvent.GetDeck(it) }
                    ?: run { EditCardsEvent.SomethingWentWrong }
            },
            { _event.value = EditCardsEvent.SomethingWentWrong }
        )
    }
}