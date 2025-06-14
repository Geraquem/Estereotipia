package com.mmfsin.estereotipia.presentation.customdecks.customdecks

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.DeleteCustomDeckUseCase
import com.mmfsin.estereotipia.domain.usecases.GetCustomDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomDecksViewModel @Inject constructor(
    private val getCustomDecksUseCase: GetCustomDecksUseCase,
    private val deleteCustomDeckUseCase: DeleteCustomDeckUseCase
) : BaseViewModel<CustomDecksEvent>() {

    fun getCustomDecks() {
        executeUseCase(
            { getCustomDecksUseCase.execute() },
            { result -> _event.value = CustomDecksEvent.CustomDecks(result) },
            { _event.value = CustomDecksEvent.SomethingWentWrong }
        )
    }

    fun deleteCustomDeck(id: String) {
        executeUseCase(
            { deleteCustomDeckUseCase.execute(DeleteCustomDeckUseCase.Params(id)) },
            { _event.value = CustomDecksEvent.FlowCompleted },
            { _event.value = CustomDecksEvent.SomethingWentWrong }
        )
    }
}