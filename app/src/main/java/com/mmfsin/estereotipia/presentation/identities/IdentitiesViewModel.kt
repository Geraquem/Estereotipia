package com.mmfsin.estereotipia.presentation.identities

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetIdentitiesUseCase
import com.mmfsin.estereotipia.domain.usecases.GetThreeRandomCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IdentitiesViewModel @Inject constructor(
    private val getIdentitiesUseCase: GetIdentitiesUseCase,
    private val getThreeRandomCardsUseCase: GetThreeRandomCardsUseCase
) : BaseViewModel<IdentitiesEvent>() {

    fun getIdentities() {
        executeUseCase(
            { getIdentitiesUseCase.execute() },
            { result ->
                _event.value = result?.let { IdentitiesEvent.GetIdentities(it) }
                    ?: run { IdentitiesEvent.SomethingWentWrong }
            },
            { _event.value = IdentitiesEvent.SomethingWentWrong }
        )
    }

    fun getThreeRandomCards() {
        executeUseCase(
            { getThreeRandomCardsUseCase.execute() },
            { result ->
                _event.value = result?.let { IdentitiesEvent.GetThreeCards(it) }
                    ?: run { IdentitiesEvent.SomethingWentWrong }
            },
            { _event.value = IdentitiesEvent.SomethingWentWrong }
        )
    }
}