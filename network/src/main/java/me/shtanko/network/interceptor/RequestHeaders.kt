package me.shtanko.network.interceptor

object RequestHeaders {
    var accessToken: String = ""
    var optCode: String = ""

    const val USER_AGENT = "User-Agent"
    const val AUTHORIZATION_NAME = "Authorization"
    const val OTP_HEADER_NAME = "X-GitHub-OTP"

}