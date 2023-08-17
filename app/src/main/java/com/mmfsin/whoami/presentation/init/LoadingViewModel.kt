package com.mmfsin.whoami.presentation.init

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.CheckVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase,
) : BaseViewModel<LoadingEvent>() {

    fun checkVersion() {
        executeUseCase(
            { checkVersionUseCase.execute() },
            { _event.value = LoadingEvent.Completed },
            { _event.value = LoadingEvent.SomethingWentWrong }
        )
    }
}