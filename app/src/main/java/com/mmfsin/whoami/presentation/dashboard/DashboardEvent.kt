package com.mmfsin.whoami.presentation.dashboard

sealed class DashboardEvent {
    object SomethingWentWrong : DashboardEvent()
}