package com.kogicodes.sokoni.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author kogi
 */
object RequestService {


    private val BASE_URL = "http://192.168.0.101:8080/tricom/system-api/api/"

    var gson = GsonBuilder()
            .setLenient()
            .create()
    private//.client(getClient(token))
    val retrofit: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(BASE_URL)

                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

    val service: EndPoints
        get() = retrofit.create(EndPoints::class.java)

    private fun getRetrofit(token: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)

                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))

                .client(getClient(token))
                .build()
    }

    private fun getClient(token: String): OkHttpClient {

        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build()
            chain.proceed(newRequest)
        }.build()
    }


    fun getService(token: String): EndPoints {
        return getRetrofit(token).create(EndPoints::class.java)

    }
}
