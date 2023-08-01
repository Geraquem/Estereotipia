package com.mmfsin.whoami.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DiscardCardUseCase
import com.mmfsin.whoami.domain.usecases.GetCardsUseCase
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.whoami.domain.usecases.ObserveDashboardFlowUseCase
import com.mmfsin.whoami.presentation.cardinfo.CardInfoEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val discardCardUseCase: DiscardCardUseCase,
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
                _event.value = result?.let { DashboardEvent.GetCards(it) }
                    ?: run { DashboardEvent.SomethingWentWrong }
            },
            { _event.value = DashboardEvent.SomethingWentWrong }
        )
    }

    fun discardCard(cardId: String) {
        executeUseCase(
            { discardCardUseCase.execute(DiscardCardUseCase.Params(cardId)) },
            { },
            { _event.value = DashboardEvent.SomethingWentWrong }
        )
    }
}