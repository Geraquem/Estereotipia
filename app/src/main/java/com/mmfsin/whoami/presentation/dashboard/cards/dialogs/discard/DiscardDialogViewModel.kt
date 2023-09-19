package com.mmfsin.whoami.presentation.dashboard.cards.dialogs.discard

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DiscardCardUseCase
import com.mmfsin.whoami.domain.usecases.GetCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscardDialogViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val discardCardUseCase: DiscardCardUseCase,
) : BaseViewModel<DiscardDialogEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { DiscardDialogEvent.GetPeopleCard(it) }
                    ?: run { DiscardDialogEvent.SomethingWentWrong }
            },
            { _event.value = DiscardDialogEvent.SomethingWentWrong }
        )
    }

    fun discardCard(cardId: String) {
        executeUseCase(
            { discardCardUseCase.execute(DiscardCardUseCase.Params(cardId)) },
            { result -> _event.value = DiscardDialogEvent.DiscardPeopleCard(result) },
            { _event.value = DiscardDialogEvent.SomethingWentWrong }
        )
    }
}