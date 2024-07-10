package com.mmfsin.estereotipia.presentation.dashboard.cards

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetCardsByDeckIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsByDeckIdUseCase: GetCardsByDeckIdUseCase,
) : BaseViewModel<CardsEvent>() {

    fun getCards(deckId: String) {
        executeUseCase(
            { getCardsByDeckIdUseCase.execute(GetCardsByDeckIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { CardsEvent.GetCards(it) }
                    ?: run { CardsEvent.SomethingWentWrong }
            },
            { _event.value = CardsEvent.SomethingWentWrong }
        )
    }
}