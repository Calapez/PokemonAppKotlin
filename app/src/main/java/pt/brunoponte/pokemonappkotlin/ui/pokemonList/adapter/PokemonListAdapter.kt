package  pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import dagger.hilt.EntryPoint
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.databinding.PokemonItemBinding

class PokemonListAdapter(
    private val pokemonList: MutableList<Pokemon>,
    private val interaction: Interaction
) : RecyclerView.Adapter<PokemonViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemBinding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(itemBinding, interaction)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position], position)
    }

    override fun getItemCount() = pokemonList.size

    fun setPokemons(pokemons: List<Pokemon>) {
        pokemonList.apply {
            clear()
            addAll(pokemons)
        }
        notifyDataSetChanged()
    }
}