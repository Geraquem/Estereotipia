package com.mmfsin.whoami.presentation.customdecks.mydecks

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DeleteMyDeckUseCase
import com.mmfsin.whoami.domain.usecases.GetMyDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyDecksViewModel @Inject constructor(
    private val getMyDecksUseCase: GetMyDecksUseCase,
    private val deleteMyDeckUseCase: DeleteMyDeckUseCase
) : BaseViewModel<MyDecksEvent>() {

    fun getMyDecks() {
        executeUseCase(
            { getMyDecksUseCase.execute() },
            { result -> _event.value = MyDecksEvent.MyDecks(result) },
            { _event.value = MyDecksEvent.SomethingWentWrong }
        )
    }

    fun deleteMyDeck(id: String) {
        executeUseCase(
            { deleteMyDeckUseCase.execute(DeleteMyDeckUseCase.Params(id)) },
            { _event.value = MyDecksEvent.FlowCompleted },
            { _event.value = MyDecksEvent.SomethingWentWrong }
        )
    }
}