package app.storytel.candidate.com.common

/**
 * Retry callback implementation. (Liskov Substitution Principle) from S.O.L.I.D
 */
interface RetryCallback {
    fun retry()
}