package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import com.mmfsin.whoami.domain.models.Card
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCaseNoParams<List<Card>>() {

    override suspend fun execute(): List<Card> = repository.getAllCards() ?: emptyList()

}