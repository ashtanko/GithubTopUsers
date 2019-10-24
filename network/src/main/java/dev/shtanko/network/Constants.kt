package dev.shtanko.network

const val GSON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
const val GITHUB_API_URL = BuildConfig.GITHUB_API_URL
const val CLIENT_ID = BuildConfig.GITHUB_CLIENT_ID
const val CLIENT_SECRET = BuildConfig.GITHUB_SECRET
const val REDIRECT_URL = BuildConfig.GITHUB_REDIRECT_URL
val SCOPES = arrayOf("user", "repo", "gist", "notifications", "read:org")