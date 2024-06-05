package com.mmfsin.whoami.presentation.menu.decks

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetSystemDecksUseCase
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