package com.mmfsin.whoami.presentation.decks.mydecks.dialogs

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetMyDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyDeckViewModel @Inject constructor(
    private val getMyDeckByIdUseCase: GetMyDeckByIdUseCase
) : BaseViewModel<MyDeckEvent>() {

    fun getMyDeck(id: String) {
        executeUseCase(
            { getMyDeckByIdUseCase.execute(GetMyDeckByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { MyDeckEvent.GetDeck(result) }
                    ?: run { MyDeckEvent.SomethingWentWrong }
            },
            { _event.value = MyDeckEvent.SomethingWentWrong }
        )
    }
}