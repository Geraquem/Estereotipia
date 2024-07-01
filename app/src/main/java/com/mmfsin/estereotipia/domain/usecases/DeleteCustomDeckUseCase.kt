package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCase
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
import javax.inject.Inject

class DeleteCustomDeckUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<DeleteCustomDeckUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.deleteCustomDeck(params.id)

    data class Params(
        val id: String
    )
}