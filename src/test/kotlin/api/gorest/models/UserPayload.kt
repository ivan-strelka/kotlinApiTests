package api.gorest.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserPayload(

    @field:JsonProperty("gender")
    val gender: String? = null,

    @field:JsonProperty("name")
    val name: String? = null,

    @field:JsonProperty("id")
    val id: Int? = null,

    @field:JsonProperty("message")
    val message: String? = null,

    @field:JsonProperty("email")
    val email: String? = null,

    @field:JsonProperty("status")
    val status: String? = null
) {
    override fun toString(): String {
        return "UserPayload(gender=$gender, name=$name, id=$id, message=$message, email=$email, status=$status)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPayload

        if (gender != other.gender) return false
        if (name != other.name) return false
        if (id != other.id) return false
        if (message != other.message) return false
        if (email != other.email) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gender?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (id ?: 0)
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        return result
    }


}
