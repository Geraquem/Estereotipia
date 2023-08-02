package com.mmfsin.whoami.presentation.dashboard.people

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
class PeopleViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getCardsUseCase: GetCardsUseCase,
    private val discardCardUseCase: DiscardCardUseCase,
    private val observeFlowUseCase: ObserveFlowUseCase
) : BaseViewModel<PeopleEvent>() {

    private val qrJob = Job()

    init {
        observeFlow()
    }

    private fun observeFlow() = viewModelScope.launch(qrJob) {
        observeFlowUseCase.execute().collect() {
            _event.value = PeopleEvent.UpdateCard(it.second)
        }
    }

    override fun onCleared() = qrJob.cancel()

    fun getActualDeck(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { PeopleEvent.GetActualDeck(it) }
                    ?: run { PeopleEvent.SomethingWentWrong }
            },
            { _event.value = PeopleEvent.SomethingWentWrong }
        )
    }

    fun getCards(deckId: String) {
        executeUseCase(
            { getCardsUseCase.execute(GetCardsUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { PeopleEvent.GetCards(it) }
                    ?: run { PeopleEvent.SomethingWentWrong }
            },
            { _event.value = PeopleEvent.SomethingWentWrong }
        )
    }

    fun discardCard(cardId: String, updateFlow: Boolean) {
        executeUseCase(
            { discardCardUseCase.execute(DiscardCardUseCase.Params(cardId, updateFlow)) },
            { /** No need for response */ },
            { _event.value = PeopleEvent.SomethingWentWrong }
        )
    }
}