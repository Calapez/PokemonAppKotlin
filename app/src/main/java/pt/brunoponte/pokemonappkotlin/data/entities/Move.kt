package pt.brunoponte.pokemonappkotlin.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Format:
 *  {
 *      "move": MoveDetails
 *  }
 */

data class Move (

    @Expose
    @SerializedName("move")
    var details: MoveDetails

) {

    /**
     * Format:
     * {
     *      "name": "razor-wind"
     * }
     */

    data class MoveDetails (
        @Expose
        @SerializedName("name")
        var name: String = ""
    )
}