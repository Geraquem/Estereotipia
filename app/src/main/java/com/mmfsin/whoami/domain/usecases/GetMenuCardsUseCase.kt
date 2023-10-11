package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import com.mmfsin.whoami.domain.models.Card
import javax.inject.Inject

class GetMenuCardsUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCaseNoParams<List<Card>>() {

    override suspend fun execute(): List<Card> {
        val cards = repository.getAllCards()
        return cards?.shuffled()?.take(5) ?: run { emptyList() }
    }
}