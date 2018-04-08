package oktomeme

import com.fasterxml.jackson.annotation.JsonAutoDetect

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class Request(
    private val description: String,
    private val phone: String,
    private val author: String
) {
    override fun toString(): String {
        return "$description, call $phone and ask $author to find out more"
    }
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class Provider(
    private val description: String,
    private val phone: String,
    private val author: String
) {
    override fun toString(): String {
        return "$description, call $phone and ask $author to find out more"
    }
}
