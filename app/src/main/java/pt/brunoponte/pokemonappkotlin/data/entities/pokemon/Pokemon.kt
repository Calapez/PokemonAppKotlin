package pt.brunoponte.pokemonappkotlin.data.entities.pokemon

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import pt.brunoponte.pokemonappkotlin.data.entities.Sprites
import pt.brunoponte.pokemonappkotlin.data.entities.ability.AbilityWrapper
import pt.brunoponte.pokemonappkotlin.data.entities.move.MoveWrapper

data class Pokemon (

    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = -1,

    @Expose
    @SerializedName("name")
    var name: String = "",

    @Expose
    @SerializedName("weight")
    var weight: Int = -1,

    @Expose
    @SerializedName("moves")
    var moveWrappers: List<MoveWrapper> = listOf(),

    @Expose
    @SerializedName("abilities")
    var abilityWrappers: List<AbilityWrapper> = listOf(),

    @Expose
    @SerializedName("sprites")
    var sprites: Sprites? = null,

    )
