package com.fuadhev.mytayqatask.di

import android.content.Context
import androidx.room.Room
import com.fuadhev.mytayqatask.common.utils.Constants.BASE_URL
import com.fuadhev.mytayqatask.data.local.PersonDB
import com.fuadhev.mytayqatask.data.network.api.PersonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).client(clientBuilder).build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PersonApiService =
        retrofit.create(PersonApiService::class.java)


    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): PersonDB =
        Room.databaseBuilder(
            context,
            PersonDB::class.java,
            "PersonDB"
        ).build()


}