package pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter

import androidx.recyclerview.widget.RecyclerView
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.databinding.PokemonItemBinding
import pt.brunoponte.pokemonappkotlin.utils.Helpers.Companion.capitalizeFirstLetter
import pt.brunoponte.pokemonappkotlin.utils.Helpers.Companion.fillImageFromUrl

class PokemonViewHolder(
    private val binding: PokemonItemBinding,
    private val interaction: Interaction
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon, position: Int) {
        setTitle(capitalizeFirstLetter(pokemon.name))
        fillImageFromUrl(binding.imgPhoto, pokemon.sprites.frontUrl)

        binding.root.setOnClickListener {
            interaction.onItemSelected(position, pokemon)
        }
    }

    private fun setTitle(title: String) {
        binding.textName.text = title
    }

}