package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.models.Deck
import javax.inject.Inject

class GetSystemDeckByIdUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<GetSystemDeckByIdUseCase.Params, Deck?>() {

    override suspend fun execute(params: Params): Deck? = repository.getSystemDeckById(params.id)

    data class Params(
        val id: String
    )
}