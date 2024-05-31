package com.mmfsin.whoami.presentation.customdecks.create

import com.mmfsin.whoami.base.BaseViewModel
import com.mmfsin.whoami.domain.mappers.toCreateDeckCardList
import com.mmfsin.whoami.domain.usecases.GetAllCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateDeckViewModel @Inject constructor(
    private val getAllCardsUseCase: GetAllCardsUseCase
) : BaseViewModel<CreateDeckEvent>() {

    fun getCards() {
        executeUseCase(
            { getAllCardsUseCase.execute() },
            { result ->
                _event.value = if (result.isEmpty()) CreateDeckEvent.SomethingWentWrong
                else CreateDeckEvent.AllCards(result.toCreateDeckCardList())
            },
            { _event.value = CreateDeckEvent.SomethingWentWrong }
        )
    }
}