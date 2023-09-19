package com.mmfsin.whoami.presentation.allcards

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetAllCardsUseCase
import com.mmfsin.whoami.domain.usecases.GetInstructionsUseCase
import com.mmfsin.whoami.presentation.menu.decks.DecksEvent
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