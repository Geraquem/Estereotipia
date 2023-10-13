package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import com.mmfsin.whoami.domain.models.MyDeck
import javax.inject.Inject

class GetMyDeckByIdUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<GetMyDeckByIdUseCase.Params, MyDeck?>() {

    override suspend fun execute(params: Params): MyDeck? = repository.getMyDeckById(params.id)

    data class Params(
        val id: String
    )
}