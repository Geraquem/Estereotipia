package com.mmfsin.whoami.di

import com.mmfsin.whoami.data.repository.DashboardRepository
import com.mmfsin.whoami.domain.interfaces.IDashboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DashboardRepositoryModule {
    @Binds
    fun bind(repository: DashboardRepository): IDashboardRepository
}