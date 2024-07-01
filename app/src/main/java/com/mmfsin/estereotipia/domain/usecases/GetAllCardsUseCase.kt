package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.ICardsRepository
import com.mmfsin.estereotipia.domain.models.Card
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCaseNoParams<List<Card>>() {

    override suspend fun execute(): List<Card> = repository.getAllCards() ?: emptyList()

}