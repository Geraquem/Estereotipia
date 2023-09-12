package com.mmfsin.whoami.presentation.dialogs.select

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetCardByIdUseCase
import com.mmfsin.whoami.domain.usecases.SelectCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectedCardDialogViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
) : BaseViewModel<SelectedCardDialogEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { SelectedCardDialogEvent.GetCard(it) }
                    ?: run { SelectedCardDialogEvent.SomethingWentWrong }
            },
            { _event.value = SelectedCardDialogEvent.SomethingWentWrong }
        )
    }
}