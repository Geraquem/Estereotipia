package com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IdentityCardDialogViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
) : BaseViewModel<IdentityCardDialogEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { IdentityCardDialogEvent.GetCard(it) }
                    ?: run { IdentityCardDialogEvent.SomethingWentWrong }
            },
            { _event.value = IdentityCardDialogEvent.SomethingWentWrong }
        )
    }
}