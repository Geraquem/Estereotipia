package com.mmfsin.estereotipia.presentation.menu.decks

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetSystemDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DecksViewModel @Inject constructor(
    private val getSystemDecksUseCase: GetSystemDecksUseCase
) : BaseViewModel<DecksEvent>() {

    fun getDecks() {
        executeUseCase(
            { getSystemDecksUseCase.execute() },
            { result -> _event.value = DecksEvent.GetDecks(result) },
            { _event.value = DecksEvent.SomethingWentWrong }
        )
    }
}