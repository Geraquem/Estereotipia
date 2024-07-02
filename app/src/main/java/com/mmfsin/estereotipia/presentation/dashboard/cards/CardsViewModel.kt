package com.mmfsin.estereotipia.presentation.dashboard.cards

import androidx.lifecycle.viewModelScope
import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetCardsByDeckIdUseCase
import com.mmfsin.estereotipia.domain.usecases.ObserveFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsByDeckIdUseCase: GetCardsByDeckIdUseCase,
    private val observeFlowUseCase: ObserveFlowUseCase
) : BaseViewModel<CardsEvent>() {

    private val qrJob = Job()

    init {
        observeFlow()
    }

    private fun observeFlow() = viewModelScope.launch(qrJob) {
        observeFlowUseCase.execute().collect() {
            if (it.second.isNotEmpty()) _event.value = CardsEvent.UpdateCard(it.second)
        }
    }

    override fun onCleared() = qrJob.cancel()

    fun getCards(deckId: String) {
        executeUseCase(
            { getCardsByDeckIdUseCase.execute(GetCardsByDeckIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { CardsEvent.GetCards(it) }
                    ?: run { CardsEvent.SomethingWentWrong }
            },
            { _event.value = CardsEvent.SomethingWentWrong }
        )
    }
}