package pt.brunoponte.pokemonappkotlin.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Format:
 *  {
 *      "back_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
 *      "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
 *  }
 */

data class Sprites (

    @Expose
    @SerializedName("front_default")
    var frontUrl: String = "",

    @Expose
    @SerializedName("back_default")
    var backUrl: String = ""

)