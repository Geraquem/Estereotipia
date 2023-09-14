package com.mmfsin.whoami.presentation.dashboard.viepager

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.usecases.GetCardsUseCase
import com.mmfsin.whoami.domain.usecases.GetRandomSelectedCardUseCase
import com.mmfsin.whoami.presentation.dashboard.cards.CardsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewPagerViewModel @Inject constructor(
    private val getRandomSelectedCardUseCase: GetRandomSelectedCardUseCase,
) : BaseViewModel<ViewPagerEvent>() {

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