package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.models.Deck
import javax.inject.Inject

class GetDeckByIdUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<GetDeckByIdUseCase.Params, Deck?>() {

    override suspend fun execute(params: Params): Deck? = repository.getDeckById(params.id)

    data class Params(
        val id: String
    )
}