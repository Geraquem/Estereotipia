package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.IIdentitiesRepository
import com.mmfsin.estereotipia.domain.models.Identity
import javax.inject.Inject

class GetIdentitiesUseCase @Inject constructor(private val repository: IIdentitiesRepository) :
    BaseUseCaseNoParams<List<Identity>?>() {

    override suspend fun execute(): List<Identity>? = repository.getIdentities()?.shuffled()
}