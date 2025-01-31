package com.rb.core.network

import android.util.Log
import com.rb.core.shared.di.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(environment: Environment): String {
        return environment.baseUrl
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor { Log.d("K", it) }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        builder.callTimeout(30, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(true)
//        builder.cache(CoilUtils.createDefaultCache(ContextHolder.instance.context))
//        builder.addInterceptor(HeaderInterceptor())
//        builder.addInterceptor(SignatureInterceptor())
//        builder.addInterceptor(TokenInterceptor())
//        builder.addInterceptor(InvalidTokenInterceptor())
//        if (Log.isLoggable(TAG, Log.DEBUG) or BuildConfig.DEBUG) {
//            builder.addInterceptor(ChuckerInterceptor.Builder(ContextHolder.instance.context).build())
//        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient, json: Json): Retrofit {
        val converterFactory = json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }
}
