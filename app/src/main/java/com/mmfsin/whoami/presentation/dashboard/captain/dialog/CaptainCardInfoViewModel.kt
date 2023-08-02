package com.mmfsin.whoami.presentation.dashboard.captain.dialog

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetCardByIdUseCase
import com.mmfsin.whoami.domain.usecases.SelectCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CaptainCardInfoViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val selectCardUseCase: SelectCardUseCase,
) : BaseViewModel<CaptainCardInfoEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { CaptainCardInfoEvent.GetPeopleCard(it) }
                    ?: run { CaptainCardInfoEvent.SomethingWentWrong }
            },
            { _event.value = CaptainCardInfoEvent.SomethingWentWrong }
        )
    }

    fun selectCard(cardId: String) {
        executeUseCase(
            { selectCardUseCase.execute(SelectCardUseCase.Params(cardId)) },
            { /** Flow do the rest */ },
            { _event.value = CaptainCardInfoEvent.SomethingWentWrong }
        )
    }
}