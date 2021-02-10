package pt.brunoponte.pokemonappkotlin.data.entities.pokemon

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SimplePokemon (

    @Expose
    @SerializedName("name")
    var name: String = "",

    @Expose
    @SerializedName("url")
    var url: String = "",

    @Transient
    var photoUrl: String = ""

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(photoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimplePokemon> {
        override fun createFromParcel(parcel: Parcel): SimplePokemon {
            return SimplePokemon(
                parcel
            )
        }

        override fun newArray(size: Int): Array<SimplePokemon?> {
            return arrayOfNulls(size)
        }
    }
}