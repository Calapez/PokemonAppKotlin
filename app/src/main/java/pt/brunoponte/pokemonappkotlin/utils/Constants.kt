package pt.brunoponte.pokemonappkotlin.utils

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.util.*

class Constants {

    companion object {

        const val baseUrl = "https://pokeapi.co/api/v2/"
        const val pageSize = 15

        fun fillImageFromUrl(imgView: ImageView, url: String) {
            Log.d("Details", "Start Fill")
            Picasso.get()
                .load(url)
                .into(imgView)
            Log.d("Details", "End Fill")
        }

        fun capitalizeFirstLetter(str: String) =
            if (str.isEmpty()) ""
            else str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1)


    }

}