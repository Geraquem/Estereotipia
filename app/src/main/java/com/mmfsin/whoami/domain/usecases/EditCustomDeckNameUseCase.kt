package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import javax.inject.Inject

class EditCustomDeckNameUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<EditCustomDeckNameUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.editCustomDeckNameById(params.id, params.name)

    data class Params(
        val id: String, val name: String
    )
}