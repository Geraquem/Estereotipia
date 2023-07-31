package com.mmfsin.whoami.presentation.cardinfo

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DiscardCardUseCase
import com.mmfsin.whoami.domain.usecases.GetCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardInfoViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val discardCardUseCase: DiscardCardUseCase,
) : BaseViewModel<CardInfoEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { CardInfoEvent.GetCard(it) }
                    ?: run { CardInfoEvent.SomethingWentWrong }
            },
            { _event.value = CardInfoEvent.SomethingWentWrong }
        )
    }

    fun discardCard(cardId: String) {
        executeUseCase(
            { discardCardUseCase.execute(DiscardCardUseCase.Params(cardId)) },
            { _event.value = CardInfoEvent.DiscardCard },
            { _event.value = CardInfoEvent.SomethingWentWrong }
        )
    }
}