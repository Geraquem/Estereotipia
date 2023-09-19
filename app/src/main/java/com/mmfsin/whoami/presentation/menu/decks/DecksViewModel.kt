package com.mmfsin.whoami.presentation.menu.decks

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.CheckVersionUseCase
import com.mmfsin.whoami.domain.usecases.GetDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DecksViewModel @Inject constructor(
    private val getDecksUseCase: GetDecksUseCase
) : BaseViewModel<DecksEvent>() {

    fun getDecks() {
        executeUseCase(
            { getDecksUseCase.execute() },
            { result ->
                _event.value = if (result.isEmpty()) DecksEvent.SomethingWentWrong
                else DecksEvent.GetDecks(result)
            },
            { _event.value = DecksEvent.SomethingWentWrong }
        )
    }
}