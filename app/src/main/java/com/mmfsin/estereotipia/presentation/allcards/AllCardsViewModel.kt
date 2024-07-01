package com.mmfsin.estereotipia.presentation.allcards

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetAllCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllCardsViewModel @Inject constructor(
    private val getAllCardsUseCase: GetAllCardsUseCase,
) : BaseViewModel<AllCardsEvent>() {

    fun getAllCards() {
        executeUseCase(
            { getAllCardsUseCase.execute() },
            { result ->
                _event.value = if (result.isEmpty()) AllCardsEvent.SomethingWentWrong
                else AllCardsEvent.GetCards(result)
            },
            { _event.value = AllCardsEvent.SomethingWentWrong }
        )
    }
}