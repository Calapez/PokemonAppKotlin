package pt.brunoponte.pokemonappkotlin.ui.pokemonList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pt.brunoponte.pokemonappkotlin.R
import pt.brunoponte.pokemonappkotlin.data.entities.Pokemon
import pt.brunoponte.pokemonappkotlin.databinding.FragmentPokemonListBinding
import pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter.Interaction
import pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter.PokemonListAdapter
import pt.brunoponte.pokemonappkotlin.utils.Constants
import pt.brunoponte.pokemonappkotlin.viewmodels.PokemonsViewModel

@AndroidEntryPoint
class PokemonListFragment : Fragment(), Interaction {

    private val viewModel: PokemonsViewModel by activityViewModels()
    private lateinit var listAdapter: PokemonListAdapter
    private lateinit var binding: FragmentPokemonListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listAdapter = PokemonListAdapter(interaction = this)
        listAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setObservers()

        if (savedInstanceState == null) {
            viewModel.fetchMorePokemons()
        }
    }

    override fun onItemSelected(position: Int, item: Pokemon) {
        // Set selected pokemon in ViewModel and navigate to Details Fragment
        viewModel.selectPokemon(item)
        findNavController()
            .navigate(R.id.action_galleryFragment_to_detailsFragment)
    }

    private fun initRecyclerView() {
        with(binding.recyclerPokemons) {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            this.layoutManager = layoutManager
            this.adapter = listAdapter

            this.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (viewModel.getIsFetching().value == null ) {
                        return
                    }

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    // Scrolled to the bottom, fetch more pokemons
                    if (!viewModel.getIsFetching().value!!
                        && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= Constants.pageSize
                    ) {
                        // Load new page of pokemons
                        viewModel.fetchMorePokemons()
                    }
                }
            })
        }
    }

    private fun setObservers() {
        viewModel.getPokemons().observe(viewLifecycleOwner) { pokemons ->
            // New pokemons, update list adapter
            listAdapter.setPokemons(pokemons)
        }

        viewModel.getIsFetching().observe(viewLifecycleOwner) { isLoading ->
            // Fetching state changed, change progress bar
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        }
    }

}