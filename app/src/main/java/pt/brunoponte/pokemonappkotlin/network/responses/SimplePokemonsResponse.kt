package pt.brunoponte.pokemonappkotlin.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SimplePokemonsResponse (

    @Expose
    @SerializedName("results")
    var simplePokemons: List<SimplePokemon> = listOf()

) {

    inner class SimplePokemon (
        @Expose
        @SerializedName("name")
        var name: String = ""
    )

}