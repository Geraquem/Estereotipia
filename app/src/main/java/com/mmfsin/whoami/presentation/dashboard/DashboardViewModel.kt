package com.mmfsin.whoami.presentation.dashboard

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetCardsUseCase
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getCardsUseCase: GetCardsUseCase
) : BaseViewModel<DashboardEvent>() {

    fun getActualDeck(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { DashboardEvent.GetActualDeck(it) }
                    ?: run { DashboardEvent.SomethingWentWrong }
            },
            { _event.value = DashboardEvent.SomethingWentWrong }
        )
    }

    fun getCards(deckId: String) {
        executeUseCase(
            { getCardsUseCase.execute(GetCardsUseCase.Params(deckId)) },
            { result ->
                _event.value = if (result.isEmpty()) DashboardEvent.SomethingWentWrong
                else DashboardEvent.GetCards(result)
            },
            { _event.value = DashboardEvent.SomethingWentWrong }
        )
    }
}