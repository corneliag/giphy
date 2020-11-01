package com.cjuca.giphy.service.module

import com.cjuca.giphy.BuildConfig
import com.cjuca.giphy.service.api.GiphyClient
import com.cjuca.giphy.service.api.IGiphyApi
import com.cjuca.giphy.service.api.IGiphyClient
import com.cjuca.giphy.service.manager.GiphyManager
import com.cjuca.giphy.service.manager.IGiphyManager
import com.cjuca.giphy.service.repository.GiphyRepository
import com.cjuca.giphy.service.repository.IGiphyRepository
import com.cjuca.giphy.ui.random.RandomGifViewModel
import com.cjuca.giphy.ui.search.SearchGifViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GiphyModule {

    private const val BASE_URL: String = "https://api.giphy.com"

    private val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private val client: OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(59, TimeUnit.SECONDS)
            .readTimeout(59, TimeUnit.SECONDS)
            .dispatcher(Dispatcher().apply {
                maxRequests = 10
                maxRequestsPerHost = 10
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.BASIC
                }
            })
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(CustomConverterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()


    val giphyserviceModule: Module = module {
        single<IGiphyManager> { GiphyManager(get()) }
        single<IGiphyRepository> { GiphyRepository(get()) }
        single<IGiphyClient> {
            GiphyClient(retrofit.create(IGiphyApi::class.java))
        }
        viewModel { RandomGifViewModel(androidApplication(), get()) }
        viewModel { SearchGifViewModel(androidApplication(), get()) }
    }
}