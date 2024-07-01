package com.mmfsin.estereotipia.presentation.customdecks.create

import com.mmfsin.estereotipia.base.BaseViewModel
import com.mmfsin.estereotipia.domain.mappers.toCreateDeckCardList
import com.mmfsin.estereotipia.domain.usecases.GetAllCardsUseCase
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