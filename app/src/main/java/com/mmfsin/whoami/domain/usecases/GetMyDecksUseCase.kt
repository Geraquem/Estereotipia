package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.models.MyDeck
import javax.inject.Inject

class GetMyDecksUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCaseNoParams<List<MyDeck>>() {

    override suspend fun execute(): List<MyDeck> = repository.getMyDecks()
}