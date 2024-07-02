package com.mmfsin.estereotipia.presentation.customdecks.seecards

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetCardsByDeckIdUseCase
import com.mmfsin.estereotipia.domain.usecases.GetDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeCardsViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getCardsByDeckIdUseCase: GetCardsByDeckIdUseCase
) : BaseViewModel<SeeCardsEvent>() {

    fun getDeck(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { SeeCardsEvent.GetDeck(it) }
                    ?: run { SeeCardsEvent.SomethingWentWrong }
            },
            { _event.value = SeeCardsEvent.SomethingWentWrong }
        )
    }

    fun getCards(deckId: String) {
        executeUseCase(
            { getCardsByDeckIdUseCase.execute(GetCardsByDeckIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { SeeCardsEvent.DeckCards(it) }
                    ?: run { SeeCardsEvent.SomethingWentWrong }
            },
            { _event.value = SeeCardsEvent.SomethingWentWrong }
        )
    }
}