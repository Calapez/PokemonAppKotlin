package pt.brunoponte.pokemonappkotlin.data.entities

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Format:
 *  {
 *      "id": 1,
 *      "name": "pikachu",
 *      "weight": 12,
 *      "moves": [Move, ...],
 *      "abilities": [Ability, ...],
 *      "sprites": Sprites
 *  }
 */

data class Pokemon (

    @Expose
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    var id: Long = -1,

    @Expose
    @SerializedName("name")
    var name: String = "",

    @Expose
    @SerializedName("weight")
    var weight: Int = -1,

    @Expose
    @SerializedName("moves")
    var moves: List<Move> = listOf(),

    @Expose
    @SerializedName("abilities")
    var abilities: List<Ability> = listOf(),

    @Expose
    @SerializedName("sprites")
    var sprites: Sprites,
)
