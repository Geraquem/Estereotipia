package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCase
import com.mmfsin.estereotipia.domain.interfaces.ICardsRepository
import javax.inject.Inject

class SelectCardUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<SelectCardUseCase.Params, Unit>() {

    override suspend fun execute(params: Params): Unit = repository.selectCard(params.cardId)

    class Params(
        val cardId: String,
    )
}