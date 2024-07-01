package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
import com.mmfsin.estereotipia.domain.models.Deck
import javax.inject.Inject

class GetCustomDecksUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCaseNoParams<List<Deck>>() {

    override suspend fun execute(): List<Deck> = repository.getCustomDecks()
}