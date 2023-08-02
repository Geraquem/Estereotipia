package com.mmfsin.whoami.presentation.dashboard.captain

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DiscardCardUseCase
import com.mmfsin.whoami.domain.usecases.GetCardsUseCase
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CaptainViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val discardCardUseCase: DiscardCardUseCase,
) : BaseViewModel<CaptainEvent>() {

    fun getActualDeck(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { CaptainEvent.GetActualDeck(it) }
                    ?: run { CaptainEvent.SomethingWentWrong }
            },
            { _event.value = CaptainEvent.SomethingWentWrong }
        )
    }

    fun getCards(deckId: String) {
        executeUseCase(
            { getCardsUseCase.execute(GetCardsUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { CaptainEvent.GetCards(it) }
                    ?: run { CaptainEvent.SomethingWentWrong }
            },
            { _event.value = CaptainEvent.SomethingWentWrong }
        )
    }

    fun discardCard(cardId: String, updateFlow: Boolean) {
        executeUseCase(
            { discardCardUseCase.execute(DiscardCardUseCase.Params(cardId, updateFlow)) },
            { /** No need for response */ },
            { _event.value = CaptainEvent.SomethingWentWrong }
        )
    }
}