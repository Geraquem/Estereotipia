package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.models.MyDeck
import javax.inject.Inject

class CreateDeckUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<CreateDeckUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.createDeck(params.myDeck)

    data class Params(
        val myDeck: MyDeck
    )
}