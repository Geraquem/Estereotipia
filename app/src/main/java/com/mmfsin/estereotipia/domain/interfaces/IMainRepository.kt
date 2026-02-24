package com.mmfsin.estereotipia.domain.interfaces

import com.mmfsin.estereotipia.domain.models.GameInfo

interface IMainRepository {
    suspend fun checkVersion()
    suspend fun getGameInfo(): GameInfo?
}