package com.mmfsin.whoami.domain.interfaces

interface IMainRepository {
    suspend fun checkVersion(): Boolean
}