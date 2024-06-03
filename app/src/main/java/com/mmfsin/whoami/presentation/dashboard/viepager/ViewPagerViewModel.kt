package com.mmfsin.whoami.presentation.dashboard.viepager

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.models.DeckType
import com.mmfsin.whoami.domain.usecases.GetSystemDeckByIdUseCase
import com.mmfsin.whoami.domain.usecases.GetRandomCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(
    private val getSystemDeckByIdUseCase: GetSystemDeckByIdUseCase,
    private val getRandomCardUseCase: GetRandomCardUseCase,
) : BaseViewModel<ViewPagerEvent>() {

    fun getDeck(deckId: String) {
        executeUseCase(
            { getSystemDeckByIdUseCase.execute(GetSystemDeckByIdUseCase.Params(deckId)) },
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