package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.ICardsRepository
import com.mmfsin.estereotipia.domain.models.Card
import javax.inject.Inject

class GetMenuCardsUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCaseNoParams<Card?>() {

    override suspend fun execute(): Card? {
        val cards = repository.getAllCards()
        return cards?.shuffled()?.first() ?: run { null }
    }
}