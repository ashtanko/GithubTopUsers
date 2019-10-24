package dev.shtanko.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()

        val authToken = RequestHeaders.accessToken

        val otpCode = RequestHeaders.optCode

        if (authToken.isNotEmpty()) {
            builder.addHeader(
                RequestHeaders.AUTHORIZATION_NAME,
                if (authToken.startsWith("Basic")) authToken else "token $authToken"
            )
        }

        if (otpCode.isNotEmpty()) {
            builder.addHeader(
                RequestHeaders.OTP_HEADER_NAME,
                otpCode.trim()
            )
        }

        val request = builder.build()
        return chain.proceed(request)
    }
}