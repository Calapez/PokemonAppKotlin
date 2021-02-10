package pt.brunoponte.pokemonappkotlin.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sprites (

    @Expose
    @SerializedName("front_default")
    var frontUrl: String = "",

    @SerializedName("back_default")
    @Expose
    var backUrl: String = ""

)