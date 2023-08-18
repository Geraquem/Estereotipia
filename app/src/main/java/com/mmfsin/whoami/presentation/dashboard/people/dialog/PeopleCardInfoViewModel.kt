package com.mmfsin.whoami.presentation.dashboard.people.dialog

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DiscardCardUseCase
import com.mmfsin.whoami.domain.usecases.GetCardByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PeopleCardInfoViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val discardCardUseCase: DiscardCardUseCase,
) : BaseViewModel<PeopleCardInfoEvent>() {

    fun getCardById(cardId: String) {
        executeUseCase(
            { getCardByIdUseCase.execute(GetCardByIdUseCase.Params(cardId)) },
            { result ->
                _event.value = result?.let { PeopleCardInfoEvent.GetPeopleCard(it) }
                    ?: run { PeopleCardInfoEvent.SomethingWentWrong }
            },
            { _event.value = PeopleCardInfoEvent.SomethingWentWrong }
        )
    }

    fun discardCard(cardId: String) {
        executeUseCase(
            { discardCardUseCase.execute(DiscardCardUseCase.Params(cardId)) },
            { result -> _event.value = PeopleCardInfoEvent.DiscardPeopleCard(result) },
            { _event.value = PeopleCardInfoEvent.SomethingWentWrong }
        )
    }
}