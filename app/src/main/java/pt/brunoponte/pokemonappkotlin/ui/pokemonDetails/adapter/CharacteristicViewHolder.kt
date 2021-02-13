package pt.brunoponte.pokemonappkotlin.ui.pokemonDetails.adapter

import androidx.recyclerview.widget.RecyclerView
import pt.brunoponte.pokemonappkotlin.databinding.CharacteristicItemBinding

class CharacteristicViewHolder(
        private val binding: CharacteristicItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(string: String) {
        binding.textCharacteristic.text = string
    }

}