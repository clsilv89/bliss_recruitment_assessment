package com.caio.bliss.data.network

import com.caio.bliss.data.model.Emoji
import com.caio.bliss.data.api.EmojiConverterFactory
import com.caio.bliss.data.model.ErrorResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

object RestConfig {

    private const val TIME_OUT = 20L

    internal inline fun <reified S> service(url: String): S {
        return Retrofit
            .Builder()
            .baseUrl(url)
            .extras()
            .client()
            .build()
            .create(S::class.java)
    }

    private fun Retrofit.Builder.client(): Retrofit.Builder {
        val okHttpClient = OkHttpClient.Builder()
            .timeout()
            .interceptor()
            .build()
        return client(okHttpClient)
    }

    private fun Retrofit.Builder.extras(): Retrofit.Builder {
        val listType: Type = object : TypeToken<MutableList<Emoji>>() {}.type
        val gsonEmojiDeserializer = GsonBuilder()
            .registerTypeAdapter(listType,
                EmojiConverterFactory()
            )
            .create()

        val gsonConverter = GsonConverterFactory.create(gsonEmojiDeserializer)
        val rxAdapter = RxJava2CallAdapterFactory.create()

        return addConverterFactory(gsonConverter)
            .addCallAdapterFactory(rxAdapter)
    }

    private fun OkHttpClient.Builder.timeout(): OkHttpClient.Builder {
        return readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .callTimeout(TIME_OUT, TimeUnit.SECONDS)
    }

    private fun OkHttpClient.Builder.interceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        addInterceptor(logging)
        return this
    }

    suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse =
                            convertErrorBody(
                                throwable
                            )
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}