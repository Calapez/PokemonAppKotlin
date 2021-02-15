package pt.brunoponte.pokemonappkotlin.data.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Format:
 *  {
 *      "ability": AbilityDetails
 *  }
 */

data class Ability (

    @Expose
    @SerializedName("ability")
    var details: AbilityDetails

) {

    /**
     * Format:
     * {
     *      "name": "overgrow"
     * }
     */

    data class AbilityDetails (
        @SerializedName("name")
        @Expose
        var name: String = ""
    )

}