package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.cards.dialogs.choice

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChoiceDialogViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
) : BaseViewModel<ChoiceDialogEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { ChoiceDialogEvent.GetCard(it) }
                    ?: run { ChoiceDialogEvent.SomethingWentWrong }
            },
            { _event.value = ChoiceDialogEvent.SomethingWentWrong }
        )
    }
}