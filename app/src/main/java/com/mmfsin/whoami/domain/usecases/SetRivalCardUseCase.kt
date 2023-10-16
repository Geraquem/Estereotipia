package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.ICardsRepository
import javax.inject.Inject

class SetRivalCardUseCase @Inject constructor(private val repository: ICardsRepository) :
    BaseUseCase<SetRivalCardUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.setRivalCard(params.cardId)

    class Params(
        val cardId: String
    )
}