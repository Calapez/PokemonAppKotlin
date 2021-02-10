package pt.brunoponte.pokemonappkotlin.data.entities;

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Ability (

    @Expose
    @SerializedName("ability")
    var details: AbilityDetails

) {

    data class AbilityDetails (
        @SerializedName("name")
        @Expose
        var name: String = ""
    )

}