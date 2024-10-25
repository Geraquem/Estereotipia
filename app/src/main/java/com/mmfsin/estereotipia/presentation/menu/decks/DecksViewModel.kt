package com.mmfsin.estereotipia.presentation.menu.decks

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetAllDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DecksViewModel @Inject constructor(
    private val getAllDecksUseCase: GetAllDecksUseCase
) : BaseViewModel<DecksEvent>() {

    fun getDecks() {
        executeUseCase(
            { getAllDecksUseCase.execute() },
            { result -> _event.value = DecksEvent.GetDecks(result) },
            { _event.value = DecksEvent.SomethingWentWrong }
        )
    }
}