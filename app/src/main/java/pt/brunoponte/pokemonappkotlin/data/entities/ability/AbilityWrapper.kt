package pt.brunoponte.pokemonappkotlin.data.entities.ability

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AbilityWrapper (

    @Expose
    @SerializedName("ability") 
    var ability: Ability? = null
    
)