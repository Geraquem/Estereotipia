package com.mmfsin.whoami.presentation.menu

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.CheckVersionUseCase
import com.mmfsin.whoami.domain.usecases.GetDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
    private val getDecksUseCase: GetDecksUseCase
) : BaseViewModel<MenuEvent>() {

    fun checkVersion() {
        executeUseCase(
            { checkVersionUseCase.execute() },
            { _event.value = MenuEvent.Completed },
            { _event.value = MenuEvent.SomethingWentWrong }
        )
    }

//    fun getDecks() {
//        executeUseCase(
//            { getDecksUseCase.execute() },
//            { result ->
//                _event.value = if (result.isEmpty()) MenuEvent.SomethingWentWrong
//                else MenuEvent.Ge(result)
//            },
//            { _event.value = MenuEvent.SomethingWentWrong }
//        )
//    }
}