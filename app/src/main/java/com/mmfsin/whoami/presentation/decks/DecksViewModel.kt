package com.mmfsin.whoami.presentation.decks

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetDecksUseCase
import com.mmfsin.whoami.domain.usecases.GetTwoPlayerModeUseCase
import com.mmfsin.whoami.domain.usecases.SaveTwoPlayerModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DecksViewModel @Inject constructor(
    private val saveTwoPlayerModeUseCase: SaveTwoPlayerModeUseCase,
    private val getTwoPlayerModeUseCase: GetTwoPlayerModeUseCase,
    private val getDecksUseCase: GetDecksUseCase
) : BaseViewModel<DecksEvent>() {

    fun getTwoPlayerMode() {
        executeUseCase(
            { getTwoPlayerModeUseCase.execute() },
            { result -> _event.value = DecksEvent.TwoPlayerMode(result) },
            { _event.value = DecksEvent.SomethingWentWrong }
        )
    }

    fun saveTwoPlayerMode(activated: Boolean) {
        executeUseCase(
            { saveTwoPlayerModeUseCase.execute(SaveTwoPlayerModeUseCase.Params(activated)) },
            { /** No result */ },
            { _event.value = DecksEvent.SomethingWentWrong }
        )
    }

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