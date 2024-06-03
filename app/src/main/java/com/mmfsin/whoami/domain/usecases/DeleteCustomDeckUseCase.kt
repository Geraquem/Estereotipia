package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import javax.inject.Inject

class DeleteCustomDeckUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<DeleteCustomDeckUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteCustomDeck(params.id)

    data class Params(
        val id: String
    )
}