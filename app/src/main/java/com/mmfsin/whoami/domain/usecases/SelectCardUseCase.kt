package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDashboardRepository
import javax.inject.Inject

class SelectCardUseCase @Inject constructor(private val repository: IDashboardRepository) :
    BaseUseCase<SelectCardUseCase.Params, Unit>() {

    override suspend fun execute(params: Params): Unit = repository.selectCard(params.cardId)

    class Params(
        val cardId: String,
    )
}