package com.mmfsin.whoami.domain.interfaces

import com.mmfsin.whoami.domain.models.Card

interface IDashboardRepository {
    fun getCards(): List<Card>
}