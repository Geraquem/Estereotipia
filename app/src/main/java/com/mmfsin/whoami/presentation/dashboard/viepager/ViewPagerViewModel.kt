package com.mmfsin.whoami.presentation.dashboard.viepager

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.whoami.domain.usecases.GetRandomSelectedCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getRandomSelectedCardUseCase: GetRandomSelectedCardUseCase,
) : BaseViewModel<ViewPagerEvent>() {

    fun getDeckById(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { ViewPagerEvent.ActualDeck(it) }
                    ?: run { ViewPagerEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerEvent.SomethingWentWrong }
        )
    }

    fun getRandomSelectedCard(deckId: String) {
        executeUseCase(
            { getRandomSelectedCardUseCase.execute(GetRandomSelectedCardUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { ViewPagerEvent.SelectedCard(it) }
                    ?: run { ViewPagerEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerEvent.SomethingWentWrong }
        )
    }
}