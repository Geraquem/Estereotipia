package com.mmfsin.estereotipia.domain.usecases

import com.mmfsin.estereotipia.base.BaseUseCaseNoParams
import com.mmfsin.estereotipia.domain.interfaces.IMainRepository
import com.mmfsin.estereotipia.domain.models.GameInfo
import javax.inject.Inject

class GetGameInfoUseCase @Inject constructor(private val repository: IMainRepository) :
    BaseUseCaseNoParams<GameInfo?>() {

    override suspend fun execute(): GameInfo? = repository.getGameInfo()
}