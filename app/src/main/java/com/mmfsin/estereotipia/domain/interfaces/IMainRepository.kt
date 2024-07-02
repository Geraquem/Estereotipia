package com.mmfsin.estereotipia.domain.interfaces

interface IMainRepository {
    suspend fun checkVersion()
    suspend fun checkIfFirstTimeInApp(): Boolean
}