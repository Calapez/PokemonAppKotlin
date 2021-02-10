package pt.brunoponte.pokemonappkotlin.data.entities.move

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoveWrapper (

    @Expose
    @SerializedName("move")
    var move: Move

)