package com.fuadhev.mytayqatask.di

import com.fuadhev.mytayqatask.common.utils.Constants.BASE_URL
import com.fuadhev.mytayqatask.data.network.api.PersonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PersonApiService =
        retrofit.create(PersonApiService::class.java)

}