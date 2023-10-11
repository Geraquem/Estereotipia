package com.mmfsin.whoami.presentation.menu

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.CheckVersionUseCase
import com.mmfsin.whoami.domain.usecases.GetMenuCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
    private val getMenuCardsUseCase: GetMenuCardsUseCase
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
}