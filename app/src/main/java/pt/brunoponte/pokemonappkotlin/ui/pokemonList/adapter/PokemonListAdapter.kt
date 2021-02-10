package  pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.Pokemon
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemon
import pt.brunoponte.pokemonappkotlin.databinding.PokemonItemBinding
import pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter.Interaction

class PokemonListAdapter(
    private val context: Context,
    private val pokemonList: MutableList<SimplePokemon>,
    private val interaction: Interaction
) : RecyclerView.Adapter<PokemonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemBinding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(context, itemBinding, interaction)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position], position)
    }

    override fun getItemCount() = pokemonList.size

    fun setPokemons(newPokemons: List<SimplePokemon>) {
        pokemonList.apply {
            clear()
            addAll(newPokemons)
        }
        notifyDataSetChanged()
    }
}