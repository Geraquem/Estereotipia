package com.mmfsin.estereotipia.presentation.allcards.dialogs

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllCardDialogViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
) : BaseViewModel<AllCardDialogEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { AllCardDialogEvent.GetCard(it) }
                    ?: run { AllCardDialogEvent.SomethingWentWrong }
            },
            { _event.value = AllCardDialogEvent.SomethingWentWrong }
        )
    }
}