package dev.shtanko.common

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    data class ServerException(val failure: Exception) : Failure()
    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
