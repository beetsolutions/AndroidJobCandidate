package app.storytel.candidate.com.api

data class ApiError(val errorCode: Int, val errorMessage: String) : Exception()