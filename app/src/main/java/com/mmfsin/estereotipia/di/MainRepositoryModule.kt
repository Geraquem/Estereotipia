package com.mmfsin.estereotipia.di

import com.mmfsin.estereotipia.data.repository.MainRepository
import com.mmfsin.estereotipia.domain.interfaces.IMainRepository
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