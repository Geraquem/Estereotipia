package com.mmfsin.whoami.domain.usecases

import com.mmfsin.whoami.base.BaseUseCase
import com.mmfsin.whoami.domain.interfaces.IDeckRepository
import javax.inject.Inject

class EditCustomDeckCardsUseCase @Inject constructor(private val repository: IDeckRepository) :
    BaseUseCase<EditCustomDeckCardsUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.editCustomDeckCards(params.id, params.cards)

    data class Params(
        val id: String,
        val cards: List<String>
    )
}