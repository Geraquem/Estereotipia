package com.mmfsin.whoami.presentation.dashboard

import com.mmfsin.whoami.domain.models.Card

sealed class DashboardEvent {
    class GetCards(val result: List<Card>): DashboardEvent()
    object SomethingWentWrong : DashboardEvent()
}