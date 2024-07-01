package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCase
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
import javax.inject.Inject

class CreateCustomDeckUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<CreateCustomDeckUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.createDeck(params.name, params.cards)

    data class Params(
        val name: String,
        val cards: List<String>
    )
}