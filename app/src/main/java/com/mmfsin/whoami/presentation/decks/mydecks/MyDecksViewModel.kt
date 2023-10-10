package com.mmfsin.whoami.presentation.decks.mydecks

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetMyDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyDecksViewModel @Inject constructor(
    private val getMyDecksUseCase: GetMyDecksUseCase
) : BaseViewModel<MyDecksEvent>() {

    fun getMyDecks() {
        executeUseCase(
            { getMyDecksUseCase.execute() },
            { result ->
                _event.value = if (result.isEmpty()) MyDecksEvent.SomethingWentWrong
                else MyDecksEvent.MyDecks(result)
            },
            { _event.value = MyDecksEvent.SomethingWentWrong }
        )
    }
}