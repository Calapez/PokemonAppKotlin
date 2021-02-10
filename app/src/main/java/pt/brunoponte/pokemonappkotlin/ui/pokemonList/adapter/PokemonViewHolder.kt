package pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import pt.brunoponte.pokemonappkotlin.R
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.Pokemon
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemon
import pt.brunoponte.pokemonappkotlin.databinding.PokemonItemBinding
import pt.brunoponte.pokemonappkotlin.utils.Constants.Companion.capitalizeFirstLetter
import pt.brunoponte.pokemonappkotlin.utils.Constants.Companion.fillImageFromUrl

class PokemonViewHolder(
    private val context: Context,
    private val binding: PokemonItemBinding,
    private val interaction: Interaction
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(simplePokemon: SimplePokemon, position: Int) {

        setTitle(capitalizeFirstLetter(simplePokemon.name))

        if (simplePokemon.photoUrl.isNullOrEmpty()) {
            binding.imgPhoto.setImageDrawable(context.getDrawable(R.drawable.ic_launcher_background))
        } else {
            fillImageFromUrl(binding.imgPhoto, simplePokemon.photoUrl)
        }

        binding.root.setOnClickListener {
            interaction.onItemSelected(position, simplePokemon)
        }
    }

    private fun setTitle(title: String) {
        binding.textName.text = title
    }

}