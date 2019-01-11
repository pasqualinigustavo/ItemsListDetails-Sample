package br.com.fractal.model

data class Beer(
    var id: String? = null,
    var name: String? = null,
    var nameDisplay: String? = null,
    var description: String? = null,
    var abv: String? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Beer

        return id == other.id
    }
}