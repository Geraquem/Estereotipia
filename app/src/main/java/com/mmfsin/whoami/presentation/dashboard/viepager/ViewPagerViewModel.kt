package com.mmfsin.whoami.presentation.dashboard.viepager

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetDeckByIdUseCase
import com.mmfsin.whoami.domain.usecases.GetMyDeckByIdUseCase
import com.mmfsin.whoami.domain.usecases.GetRandomSelectedCardUseCase
import com.mmfsin.whoami.presentation.models.DeckType
import com.mmfsin.whoami.presentation.models.DeckType.CUSTOM_DECK
import com.mmfsin.whoami.presentation.models.DeckType.SYSTEM_DECK
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(
    private val getDeckByIdUseCase: GetDeckByIdUseCase,
    private val getMyDeckByIdUseCase: GetMyDeckByIdUseCase,
    private val getRandomSelectedCardUseCase: GetRandomSelectedCardUseCase,
) : BaseViewModel<ViewPagerEvent>() {

    fun getDeck(deckId: String, type: DeckType) {
        when (type) {
            SYSTEM_DECK -> getSystemDeckById(deckId)
            CUSTOM_DECK -> getCustomDeckById(deckId)
        }
    }

    private fun getSystemDeckById(deckId: String) {
        executeUseCase(
            { getDeckByIdUseCase.execute(GetDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { ViewPagerEvent.SystemDeck(it) }
                    ?: run { ViewPagerEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerEvent.SomethingWentWrong }
        )
    }

    private fun getCustomDeckById(deckId: String) {
        executeUseCase(
            { getMyDeckByIdUseCase.execute(GetMyDeckByIdUseCase.Params(deckId)) },
            { result ->
                _event.value = result?.let { ViewPagerEvent.CustomDeck(it) }
                    ?: run { ViewPagerEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerEvent.SomethingWentWrong }
        )
    }

    fun getRandomSelectedCard(deckId: String, deckType: DeckType) {
        executeUseCase(
            {
                getRandomSelectedCardUseCase.execute(
                    GetRandomSelectedCardUseCase.Params(deckId, deckType)
                )
            },
            { result ->
                _event.value = result?.let { ViewPagerEvent.SelectedCard(it) }
                    ?: run { ViewPagerEvent.SomethingWentWrong }
            },
            { _event.value = ViewPagerEvent.SomethingWentWrong }
        )
    }
}