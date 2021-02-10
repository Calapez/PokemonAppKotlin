package pt.brunoponte.pokemonappkotlin.data.entities.pokemon

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SimplePokemonsWrapper (

    @Expose
    @SerializedName("results")
    var pokemons: List<SimplePokemon> = listOf()

)