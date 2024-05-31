package com.mmfsin.whoami.presentation.customdecks.mydecks.dialogs

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.EditMyDeckNameUseCase
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyDeckViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val editMyDeckNameUseCase: EditMyDeckNameUseCase
) : BaseViewModel<MyDeckEvent>() {

    fun getMyDeck(id: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(id)) },
            { result ->
//                _event.value = result?.let { MyDeckEvent.GetDeck(result) }
//                    ?: run { MyDeckEvent.SomethingWentWrong }
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