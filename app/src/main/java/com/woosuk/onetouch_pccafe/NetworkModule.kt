package com.woosuk.onetouch_pccafe

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val BASE_URL = "https://dapi.kakao.com/"
    @Provides
    @Singleton
    //okHttp 의존성 주입 (아래 retrofit 의존성 주입에 사용)
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    //retrofit 의존성 주입 (아래 LoveCalculatorApi 의존성 주입에 사용)
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }



    @Provides
    @Singleton
    fun provideSearchAddressApiService(retrofit: Retrofit):SearchAddressApi{
        return retrofit.create(SearchAddressApi::class.java)
    }

    //Logcat에 okhttp입력하면 통신기록 확인 가능
    private fun getLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

}