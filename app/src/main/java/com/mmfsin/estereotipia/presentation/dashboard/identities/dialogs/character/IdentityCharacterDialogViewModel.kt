package com.mmfsin.estereotipia.presentation.dashboard.identities.dialogs.character

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IdentityCharacterDialogViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
) : BaseViewModel<IdentityCharacterDialogEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { IdentityCharacterDialogEvent.GetCharacter(it) }
                    ?: run { IdentityCharacterDialogEvent.SomethingWentWrong }
            },
            { _event.value = IdentityCharacterDialogEvent.SomethingWentWrong }
        )
    }
}