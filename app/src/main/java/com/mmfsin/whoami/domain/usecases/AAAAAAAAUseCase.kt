package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import javax.inject.Inject

class AAAAAAAAUseCase @Inject constructor() : BaseUseCase<AAAAAAAAUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
    }

    data class Params(
        val answer: String
    )
}