class IdGenerator(private val prefix: String) {
    fun generate(): String = prefix.addUUID(16)
}