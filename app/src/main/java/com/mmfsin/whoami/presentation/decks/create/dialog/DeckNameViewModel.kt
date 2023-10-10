package com.mmfsin.whoami.presentation.decks.create.dialog

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.mappers.toCreateDeckCardList
import com.mmfsin.whoami.domain.models.MyDeck
import com.mmfsin.whoami.domain.usecases.CreateDeckUseCase
import com.mmfsin.whoami.domain.usecases.GetAllCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeckNameViewModel @Inject constructor(
    private val createDeckUseCase: CreateDeckUseCase
) : BaseViewModel<DeckNameEvent>() {

    fun createDeck(myDeck: MyDeck) {
        executeUseCase(
            { createDeckUseCase.execute(CreateDeckUseCase.Params(myDeck)) },
            { _event.value = DeckNameEvent.CreatedCompleted },
            { _event.value = DeckNameEvent.SomethingWentWrong }
        )
    }
}