package me.vistark.fastdroid_lib.core.api

import me.vistark.fastdroid_lib.core.api.JwtAuth.AuthorizationKey
import me.vistark.fastdroid_lib.core.api.JwtAuth.CurrentToken
import me.vistark.fastdroid_lib.core.api.JwtAuth.CurrentTokenType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class AuthIntercepter {
    companion object {
        fun Retrofit.Builder.addToken() {
            if (CurrentToken.isEmpty())
                return

            client(OkHttpClient.Builder().apply {
                addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader(
                        AuthorizationKey,
                        "$CurrentTokenType $CurrentToken"
                    ).build()
                    chain.proceed(request)
                }
                connectTimeout(JwtAuth.ConnectTimeout, TimeUnit.SECONDS)
                readTimeout(JwtAuth.ReadTimeout, TimeUnit.SECONDS)
            }.build())
        }
    }
}