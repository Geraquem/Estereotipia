package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
import com.mmfsin.estereotipia.domain.models.AllDecks
import javax.inject.Inject

class GetAllDecksUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCaseNoParams<AllDecks>() {

    override suspend fun execute(): AllDecks = repository.getAllDecks()
}