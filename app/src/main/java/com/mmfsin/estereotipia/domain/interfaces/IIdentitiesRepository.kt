package com.mmfsin.estereotipia.domain.interfaces

import com.mmfsin.estereotipia.domain.models.Identity

interface IIdentitiesRepository {
    suspend fun getIdentities(): List<Identity>?
}