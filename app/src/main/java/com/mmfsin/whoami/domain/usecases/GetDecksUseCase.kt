package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.models.Deck
import javax.inject.Inject

class GetDecksUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCaseNoParams<List<Deck>>() {

    override suspend fun execute(): List<Deck> = repository.getDecks()
}