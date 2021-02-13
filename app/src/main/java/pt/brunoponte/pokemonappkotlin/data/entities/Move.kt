package pt.brunoponte.pokemonappkotlin.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Move (

    @Expose
    @SerializedName("move")
    var details: MoveDetails

) : Characteristic("move", details.name) {

    data class MoveDetails (
        @Expose
        @SerializedName("name")
        var name: String = ""
    )
}