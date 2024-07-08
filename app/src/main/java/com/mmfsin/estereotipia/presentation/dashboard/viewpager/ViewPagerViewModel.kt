package com.mmfsin.estereotipia.presentation.dashboard.viewpager

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.estereotipia.domain.usecases.GetQuestionsUseCase
import com.mmfsin.estereotipia.domain.usecases.GetRandomCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getRandomCardUseCase: GetRandomCardUseCase,
) : BaseViewModel<ViewPagerEvent>() {

    fun getDeck(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { ViewPagerEvent.GetDeck(it) }
                    ?: run { ViewPagerEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerEvent.SomethingWentWrong }
        )
    }

    fun getRandomSelectedCard(cards: String) {
        executeUseCase(
            { getRandomCardUseCase.execute(GetRandomCardUseCase.Params(cards)) },
            { result ->
                _event.value = result?.let { ViewPagerEvent.SelectedCard(it) }
                    ?: run { ViewPagerEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerEvent.SomethingWentWrong }
        )
    }
}