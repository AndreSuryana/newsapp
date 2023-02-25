package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.data.source.local.DBContracts
import com.example.newsapp.data.source.local.NewsAppDatabase
import com.example.newsapp.data.source.remote.NewsApiService
import com.example.newsapp.data.source.remote.interceptor.ErrorInterceptor
import com.example.newsapp.data.source.remote.interceptor.HeaderInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsAppModule {

    @Singleton
    @Provides
    fun provideNewsAppDatabase(@ApplicationContext context: Context): NewsAppDatabase =
        Room.databaseBuilder(context, NewsAppDatabase::class.java, DBContracts.DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideNewsApiService(): NewsApiService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor())
                    .addInterceptor(ErrorInterceptor())
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE)
                        }
                    )
                    .build()
            )
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .create()
                )
            )
            .build()
            .create(NewsApiService::class.java)

    @Singleton
    @Provides
    fun provideRepository(local: NewsAppDatabase, remote: NewsApiService): NewsRepository =
        NewsRepositoryImpl(local, remote)
}