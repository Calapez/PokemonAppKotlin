package pt.brunoponte.pokemonappkotlin.ui.pokemonDetails

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.databinding.FragmentPokemonDetailsBinding
import pt.brunoponte.pokemonappkotlin.utils.Constants
import pt.brunoponte.pokemonappkotlin.utils.Constants.Companion.fillImageFromUrl
import pt.brunoponte.pokemonappkotlin.viewmodels.PokemonsViewModel
import java.util.*

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment() {

    private val viewModel: PokemonsViewModel by activityViewModels()
    private lateinit var binding: FragmentPokemonDetailsBinding  // Using view binding
    private lateinit var descriptionsAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        descriptionsAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_expandable_list_item_1,
            mutableListOf()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.getSelectedPokemon().observe(viewLifecycleOwner) { pokemon ->
            fillPokemonUi(pokemon)
        }
    }

    private fun fillPokemonUi(pokemon: Pokemon) {
        with(binding) {

            textName.text = Constants.capitalizeFirstLetter(pokemon.name)
            descriptionsAdapter.clear()
            descriptionsAdapter.addAll(getDescriptionsFromPokemon(pokemon))

            pokemon.sprites?.let { sprites ->
                fillImageFromUrl(imgPhotoFront, sprites.frontUrl)
                fillImageFromUrl(imgPhotoBack, sprites.backUrl)
            }
        }
    }

    // Returns a long description of the pokemon: weight, abilities and moves
    private fun getDescriptionsFromPokemon(pokemon: Pokemon): List<String> {
        val descriptions: MutableList<String> = ArrayList()

        descriptions.add("Weight = " + pokemon.weight)

        for (i in pokemon.abilities.indices) {
            pokemon.abilities[i].details.let { abilityDetails ->
                descriptions.add(
                    java.lang.String.format(
                        Locale.US, "Ability #%d - %s",
                        i + 1, abilityDetails.name
                    )
                )
            }
        }

        for (i in pokemon.moves.indices) {
            pokemon.moves[i].details.let { moveDetails ->
                descriptions.add(
                    java.lang.String.format(
                        Locale.US, "Move #%d - %s",
                        i + 1, moveDetails.name
                    )
                )
            }
        }

        return descriptions
    }
}