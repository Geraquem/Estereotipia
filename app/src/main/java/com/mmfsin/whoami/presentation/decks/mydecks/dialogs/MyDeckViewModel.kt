package com.mmfsin.whoami.presentation.decks.mydecks.dialogs

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.EditMyDeckNameUseCase
import com.mmfsin.whoami.domain.usecases.GetMyDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyDeckViewModel @Inject constructor(
    private val getMyDeckByIdUseCase: GetMyDeckByIdUseCase,
    private val editMyDeckNameUseCase: EditMyDeckNameUseCase
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

    fun editMyDeckName(id: String, name: String) {
        executeUseCase(
            { editMyDeckNameUseCase.execute(EditMyDeckNameUseCase.Params(id, name)) },
            { _event.value = MyDeckEvent.EditedCompleted },
            { _event.value = MyDeckEvent.SomethingWentWrong }
        )
    }
}