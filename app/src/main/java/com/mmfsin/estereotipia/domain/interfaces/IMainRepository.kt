package com.mmfsin.estereotipia.domain.interfaces

interface IMainRepository {
    suspend fun checkVersion(): Unit
}