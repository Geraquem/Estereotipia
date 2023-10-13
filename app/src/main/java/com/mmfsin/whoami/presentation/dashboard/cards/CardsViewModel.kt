package com.mmfsin.whoami.presentation.dashboard.cards

import androidx.lifecycle.viewModelScope
import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DiscardCardUseCase
import com.mmfsin.whoami.domain.usecases.GetCardsByDeckIdUseCase
import com.mmfsin.whoami.domain.usecases.ObserveFlowUseCase
import com.mmfsin.whoami.presentation.models.DeckType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val getCardsByDeckIdUseCase: GetCardsByDeckIdUseCase,
    private val discardCardUseCase: DiscardCardUseCase,
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

    fun getCards(deckId: String, deckType: DeckType) {
        executeUseCase(
            { getCardsByDeckIdUseCase.execute(GetCardsByDeckIdUseCase.Params(deckId, deckType)) },
            { result ->
                _event.value = result?.let { CardsEvent.GetCards(it) }
                    ?: run { CardsEvent.SomethingWentWrong }
            },
            { _event.value = CardsEvent.SomethingWentWrong }
        )
    }

    fun discardCard(cardId: String) {
        executeUseCase(
            { discardCardUseCase.execute(DiscardCardUseCase.Params(cardId)) },
            { /** Flow do the rest */ },
            { _event.value = CardsEvent.SomethingWentWrong }
        )
    }
}