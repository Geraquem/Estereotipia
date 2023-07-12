package com.mmfsin.whoami.presentation.dashboard

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.AAAAAAAAUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val aaaaaaaaUseCase: AAAAAAAAUseCase
) : BaseViewModel<DashboardEvent>() {

    fun getCategories() {
//        executeUseCase(
//            { aaaaaaaaUseCase.execute() },
//            { result ->
//                _event.value = if (result.isEmpty()) DashboardEvent.SomethingWentWrong
//                else DashboardEvent.Categories(result)
//            },
//            { _event.value = DashboardEvent.SomethingWentWrong }
//        )
    }
}