package com.mmfsin.whoami.presentation.dashboard.tpm

import androidx.lifecycle.viewModelScope
import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.DiscardCardUseCase
import com.mmfsin.whoami.domain.usecases.GetCardsUseCase
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.whoami.domain.usecases.ObserveFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TpmViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val discardCardUseCase: DiscardCardUseCase,
    private val observeFlowUseCase: ObserveFlowUseCase
) : BaseViewModel<TpmEvent>() {

    private val qrJob = Job()

    init {
        observeFlow()
    }

    private fun observeFlow() = viewModelScope.launch(qrJob) {
        observeFlowUseCase.execute().collect() {
            _event.value = TpmEvent.UpdateCard(it.second)
        }
    }

    override fun onCleared() = qrJob.cancel()

    fun getActualDeck(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { TpmEvent.GetActualDeck(it) }
                    ?: run { TpmEvent.SomethingWentWrong }
            },
            { _event.value = TpmEvent.SomethingWentWrong }
        )
    }

    fun getCards(deckId: String) {
        executeUseCase(
            { getCardsUseCase.execute(GetCardsUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { TpmEvent.GetCards(it) }
                    ?: run { TpmEvent.SomethingWentWrong }
            },
            { _event.value = TpmEvent.SomethingWentWrong }
        )
    }

    fun discardCard(cardId: String, updateFlow: Boolean) {
        executeUseCase(
            { discardCardUseCase.execute(DiscardCardUseCase.Params(cardId, updateFlow)) },
            { /** No need for response */ },
            { _event.value = TpmEvent.SomethingWentWrong }
        )
    }
}