package com.mmfsin.whoami.presentation.customdecks.editcards

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.mappers.toCreateDeckCardList
import com.mmfsin.whoami.domain.usecases.EditCustomDeckCardsUseCase
import com.mmfsin.whoami.domain.usecases.EditCustomDeckNameUseCase
import com.mmfsin.whoami.domain.usecases.GetAllCardsUseCase
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.whoami.presentation.customdecks.create.CreateDeckEvent
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