package com.mmfsin.estereotipia.presentation.menu

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.CheckIfFirstTimeUseCase
import com.mmfsin.estereotipia.domain.usecases.CheckVersionUseCase
import com.mmfsin.estereotipia.domain.usecases.GetMenuCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
    private val getMenuCardsUseCase: GetMenuCardsUseCase,
    private val checkIfFirstTimeUseCase: CheckIfFirstTimeUseCase
) : BaseViewModel<MenuEvent>() {

    fun checkVersion() {
        executeUseCase(
            { checkVersionUseCase.execute() },
            { _event.value = MenuEvent.Completed },
            { _event.value = MenuEvent.SomethingWentWrong }
        )
    }

    fun getMenuCards() {
        executeUseCase(
            { getMenuCardsUseCase.execute() },
            { result -> _event.value = MenuEvent.MenuCards(result) },
            { _event.value = MenuEvent.SomethingWentWrong }
        )
    }

    fun checkIfFirstTimeInApp() {
        executeUseCase(
            { checkIfFirstTimeUseCase.execute() },
            { result -> _event.value = MenuEvent.FirstTime(result) },
            { _event.value = MenuEvent.SomethingWentWrong }
        )
    }
}