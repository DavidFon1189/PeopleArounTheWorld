package com.example.peoplearoundtheworld.di

import com.example.peoplearoundtheworld.repository.UserRepository
import com.example.peoplearoundtheworld.repository.UserRespositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //No se usa provide por que es una funcion abtracta
    @Singleton
    @Binds
    abstract fun providerBaeUrl(repo: UserRespositoryImp) : UserRepository
}