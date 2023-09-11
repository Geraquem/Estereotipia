package com.mmfsin.whoami.presentation.dialogs.select

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetCardByIdUseCase
import com.mmfsin.whoami.domain.usecases.SelectCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCardDialogViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val selectCardUseCase: SelectCardUseCase,
) : BaseViewModel<SelectCardDialogEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { SelectCardDialogEvent.GetPeopleCardDialog(it) }
                    ?: run { SelectCardDialogEvent.SomethingWentWrong }
            },
            { _event.value = SelectCardDialogEvent.SomethingWentWrong }
        )
    }

    fun selectCard(cardId: String) {
        executeUseCase(
            { selectCardUseCase.execute(SelectCardUseCase.Params(cardId)) },
            { /** Flow do the rest */ },
            { _event.value = SelectCardDialogEvent.SomethingWentWrong }
        )
    }
}