package com.mmfsin.whoami.presentation.dashboard

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
) : BaseViewModel<DashboardEvent>() {

    fun getCards(cardDeckId: String) {
        executeUseCase(
            { getCardsUseCase.execute(GetCardsUseCase.Params(cardDeckId)) },
            { result ->
                _event.value = if (result.isEmpty()) DashboardEvent.SomethingWentWrong
                else DashboardEvent.GetCards(result)
            },
            { _event.value = DashboardEvent.SomethingWentWrong }
        )
    }
}