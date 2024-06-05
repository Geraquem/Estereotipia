package com.mmfsin.whoami.presentation.customdecks.customdecks.dialogs

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.EditCustomDeckNameUseCase
import com.mmfsin.whoami.domain.usecases.GetCustomDeckByIdUseCase
import com.mmfsin.whoami.domain.usecases.GetCustomDecksUseCase
import com.mmfsin.whoami.domain.usecases.GetSystemDeckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomDeckViewModel @Inject constructor(
    private val getCustomDeckByIdUseCase: GetCustomDeckByIdUseCase,
    private val editCustomDeckNameUseCase: EditCustomDeckNameUseCase
) : BaseViewModel<CustomDeckEvent>() {

    fun getCustomDeck(id: String) {
        executeUseCase(
            { getCustomDeckByIdUseCase.execute(GetCustomDeckByIdUseCase.Params(id)) },
            { result ->
                _event.value = result?.let { CustomDeckEvent.GetDeck(result) }
                    ?: run { CustomDeckEvent.SomethingWentWrong }
            },
            { _event.value = CustomDeckEvent.SomethingWentWrong }
        )
    }

    fun editCustomDeckName(id: String, name: String) {
        executeUseCase(
            { editCustomDeckNameUseCase.execute(EditCustomDeckNameUseCase.Params(id, name)) },
            { _event.value = CustomDeckEvent.EditedCompleted },
            { _event.value = CustomDeckEvent.SomethingWentWrong }
        )
    }
}