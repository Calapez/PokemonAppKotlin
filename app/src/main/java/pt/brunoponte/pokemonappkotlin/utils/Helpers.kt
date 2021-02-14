package pt.brunoponte.pokemonappkotlin.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.util.*

class Helpers {

    companion object {

        fun fillImageFromUrl(imgView: ImageView, url: String) {
            Picasso.get()
                .load(url)
                .into(imgView)
        }

        fun capitalizeFirstLetter(str: String) =
            if (str.isEmpty()) ""
            else str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1)
    }

}