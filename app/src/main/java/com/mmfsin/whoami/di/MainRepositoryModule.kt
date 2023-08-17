package com.mmfsin.whoami.di

import com.mmfsin.whoami.data.repository.MainRepository
import com.mmfsin.whoami.domain.interfaces.IMainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface MainRepositoryModule {
    @Binds
    fun bind(repository: MainRepository): IMainRepository
}