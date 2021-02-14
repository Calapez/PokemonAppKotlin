package  pt.brunoponte.pokemonappkotlin.ui.pokemonDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pt.brunoponte.pokemonappkotlin.databinding.CharacteristicItemBinding

class CharacteristicsAdapter(
    private val stringList: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<CharacteristicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacteristicViewHolder {
        val itemBinding = CharacteristicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacteristicViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CharacteristicViewHolder, position: Int) {
        holder.bind(stringList[position])
    }

    override fun getItemCount()
        = stringList.size

    fun setStrings(strings: List<String>) {
        stringList.apply {
            clear()
            addAll(strings)
        }
    }

}