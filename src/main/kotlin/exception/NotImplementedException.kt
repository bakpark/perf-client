package exception

/**
 * should implement
 */
class NotImplementedException(
    override val message: String
) : RuntimeException(message)