package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCase
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
import com.mmfsin.estereotipia.domain.models.Deck
import javax.inject.Inject

class GetDeckByIdUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<GetDeckByIdUseCase.Params, Deck?>() {

    override suspend fun execute(params: Params): Deck? = repository.getDeckById(params.id)

    data class Params(
        val id: String
    )
}