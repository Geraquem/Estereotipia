package com.mmfsin.whoami.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetCardsUseCase
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.whoami.domain.usecases.ObserveDashboardFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val observeDashboardFlowUseCase: ObserveDashboardFlowUseCase
) : BaseViewModel<DashboardEvent>() {

    private val qrJob = Job()

    init {
        observeFlow()
    }

    private fun observeFlow() = viewModelScope.launch(qrJob) {
        observeDashboardFlowUseCase.execute().collect() {
            _event.value = DashboardEvent.UpdateCard(it.second)
        }
    }

    override fun onCleared() = qrJob.cancel()

    fun getActualDeck(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { DashboardEvent.GetActualDeck(it) }
                    ?: run { DashboardEvent.SomethingWentWrong }
            },
            { _event.value = DashboardEvent.SomethingWentWrong }
        )
    }

    fun getCards(deckId: String) {
        executeUseCase(
            { getCardsUseCase.execute(GetCardsUseCase.Params(deckId)) },
            { result ->
                _event.value = if (result.isEmpty()) DashboardEvent.SomethingWentWrong
                else DashboardEvent.GetCards(result)
            },
            { _event.value = DashboardEvent.SomethingWentWrong }
        )
    }
}