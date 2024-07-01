package com.mmfsin.estereotipia.presentation.customdecks.editcards

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.mappers.toCreateDeckCardList
import com.mmfsin.estereotipia.domain.usecases.EditCustomDeckCardsUseCase
import com.mmfsin.estereotipia.domain.usecases.GetAllCardsUseCase
import com.mmfsin.estereotipia.domain.usecases.GetDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditCardsViewModel @Inject constructor(
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val editCustomDeckCardsUseCase: EditCustomDeckCardsUseCase
) : BaseViewModel<EditCardsEvent>() {

    fun getAllCards() {
        executeUseCase(
            { getAllCardsUseCase.execute() },
            { result ->
                _event.value = if (result.isEmpty()) EditCardsEvent.SomethingWentWrong
                else EditCardsEvent.AllCards(result.toCreateDeckCardList())
            },
            { _event.value = EditCardsEvent.SomethingWentWrong }
        )
    }

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

    fun editCards(id: String, cards: List<String>) {
        executeUseCase(
            { editCustomDeckCardsUseCase.execute(EditCustomDeckCardsUseCase.Params(id, cards)) },
            { _event.value = EditCardsEvent.CardsEditedCompleted },
            { _event.value = EditCardsEvent.SomethingWentWrong }
        )
    }
}