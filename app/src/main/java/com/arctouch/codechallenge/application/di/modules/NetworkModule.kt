package com.arctouch.codechallenge.application.di.modules

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.application.di.scopes.ApplicationScope
import com.arctouch.codechallenge.base.common.exception.HttpError
import com.arctouch.codechallenge.home.data.GenreService
import com.arctouch.codechallenge.home.data.MoviesService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val API_KEY_NAMED = "apiKey"
        const val DEFAULT_LANGUAGE = "pt-BR"
        const val DEFAULT_REGION = "BR"
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(
        httpClient: OkHttpClient,
        baseUrl: String,
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converter)
            .client(httpClient)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideNetworkTimeout(): Long = 60L

    @Provides
    @ApplicationScope
    fun provideBaseUrl(): String = BuildConfig.API_URL

    @Provides
    @Named(API_KEY_NAMED)
    @ApplicationScope
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @ApplicationScope
    fun provideConverter(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @Provides
    @ApplicationScope
    fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d(message); })
    }

    @Provides
    @ApplicationScope
    fun provideGenreService(retrofit: Retrofit): GenreService = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideMoviesService(retrofit: Retrofit): MoviesService = retrofit.create()

    @Provides
    @ApplicationScope
    fun provideClient(
        networkTimeoutSecond: Long,
        logger: HttpLoggingInterceptor,
        auth: AuthenticationInterceptor
    ): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.readTimeout(networkTimeoutSecond, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(networkTimeoutSecond, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            logger.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logger)
        }

        okHttpClientBuilder.addInterceptor(auth)

        return okHttpClientBuilder.build()
    }

    /**
     * Custom interceptor
     */
    @Singleton
    class AuthenticationInterceptor @Inject constructor() : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val builder = request.newBuilder()
            request = builder.build()

            val response = chain.proceed(request)
            when (response.code()) {
                in 200..206 -> Timber.d("----------> Request is successful!")
                400 -> { // Validations from backend
                    val body = response.body()?.string()
                    catchServerValidations(body)
                }
                else -> { // Something that we are not know
                    val body = response.body()?.string()
                    catchServerValidations(body)
                    Timber.e("RESPONSE_CODE:${response.code()}; BODY: ${response.body()?.string().toString()} ")
                    throw HttpError
                }
            }
            return response
        }

        private fun catchServerValidations(body: String?) {
            Timber.d(body)
            throw HttpError
        }

    }
}