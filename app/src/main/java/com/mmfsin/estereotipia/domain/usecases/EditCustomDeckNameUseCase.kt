package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCase
import com.mmfsin.estereotipia.domain.interfaces.IDeckRepository
import javax.inject.Inject

class EditCustomDeckNameUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<EditCustomDeckNameUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.editCustomDeckName(params.id, params.name)

    data class Params(
        val id: String,
        val name: String
    )
}