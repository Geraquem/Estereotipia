package com.mmfsin.estereotipia.presentation.dashboard.whoiswho.viewpager

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.estereotipia.domain.usecases.GetRandomCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerWWViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getRandomCardUseCase: GetRandomCardUseCase,
) : BaseViewModel<ViewPagerWWEvent>() {

    fun getDeck(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { ViewPagerWWEvent.GetDeck(it) }
                    ?: run { ViewPagerWWEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerWWEvent.SomethingWentWrong }
        )
    }

    fun getRandomSelectedCard(cards: String) {
        executeUseCase(
            { getRandomCardUseCase.execute(GetRandomCardUseCase.Params(cards)) },
            { result ->
                _event.value = result?.let { ViewPagerWWEvent.SelectedCard(it) }
                    ?: run { ViewPagerWWEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerWWEvent.SomethingWentWrong }
        )
    }
}