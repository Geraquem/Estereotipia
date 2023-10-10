package com.mmfsin.whoami.presentation.decks.mydecks

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.CheckVersionUseCase
import com.mmfsin.whoami.domain.usecases.GetDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyDecksViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase
) : BaseViewModel<MyDecksEvent>() {

    fun checkVersion() {
        executeUseCase(
            { checkVersionUseCase.execute() },
            { _event.value = MyDecksEvent.Completed },
            { _event.value = MyDecksEvent.SomethingWentWrong }
        )
    }
}