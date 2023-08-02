package com.mmfsin.whoami.presentation.dashboard.captain.dialog

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DiscardCardUseCase
import com.mmfsin.whoami.domain.usecases.GetCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CaptainCardInfoViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase
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
}