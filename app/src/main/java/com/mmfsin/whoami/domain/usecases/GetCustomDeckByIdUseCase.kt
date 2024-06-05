package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.base.BaseUseCaseNoParams
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.models.Deck
import javax.inject.Inject

class GetCustomDeckByIdUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<GetCustomDeckByIdUseCase.Params, Deck?>() {

    override suspend fun execute(params: Params): Deck? =
        repository.getCustomDeckById(params.deckId)

    data class Params(
        val deckId: String
    )
}