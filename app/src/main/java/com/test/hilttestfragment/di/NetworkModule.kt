package com.test.hilttestfragment.di

import com.test.hilttestfragment.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkService(): NetworkService {
        return NetworkService().apply {
            fakeResponse = "$fakeResponse generated by NetworkService through Hilt"
        }
    }

}