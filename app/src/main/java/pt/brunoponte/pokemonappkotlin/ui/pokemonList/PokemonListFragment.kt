package pt.brunoponte.pokemonappkotlin.ui.pokemonList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pt.brunoponte.pokemonappkotlin.data.entities.pokemon.SimplePokemon
import pt.brunoponte.pokemonappkotlin.databinding.FragmentPokemonListBinding
import pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter.Interaction
import pt.brunoponte.pokemonappkotlin.ui.pokemonList.adapter.PokemonListAdapter
import pt.brunoponte.pokemonappkotlin.utils.Constants
import pt.brunoponte.pokemonappkotlin.viewmodels.PokemonListViewModel

@AndroidEntryPoint
class PokemonListFragment : Fragment(), Interaction {

    val viewModel: PokemonListViewModel by viewModels()

    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var listAdapter: PokemonListAdapter
    lateinit var binding: FragmentPokemonListBinding

    /* Scroll Listener for handling pagination with Recycler View */
    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (viewModel!!.getIsFetching() == null
                    || viewModel!!.getIsFetching().value == null
                ) {
                    return
                }
                val visibleItemCount: Int = mLayoutManager!!.childCount
                val totalItemCount: Int = mLayoutManager.itemCount
                val firstVisibleItemPosition: Int = mLayoutManager.findFirstVisibleItemPosition()

                // Scrolled to the bottom, fetch more pokemons
                if (!viewModel!!.getIsFetching().value!!
                    && visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= Constants.pageSize
                ) {
                    // Load new page of pokemons
                    viewModel!!.fetchMorePokemons()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        /* Recycler View */
        // Set adapter value
        listAdapter = PokemonListAdapter (
            requireContext(),
            viewModel.getSimplePokemons().value!!,
            this
        )

        // Set layout manager
        mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL

        // Set recycler view
        with(binding) {
            with(recyclerPokemon) {
                layoutManager = mLayoutManager
                adapter = listAdapter
                addOnScrollListener(recyclerViewOnScrollListener)
            }
        }

        setObservers()
        viewModel.fetchMorePokemons()

        return binding.root
    }


    override fun onItemSelected(position: Int, item: SimplePokemon) {
        Toast.makeText(context, "item clicked", LENGTH_SHORT).show()
    }

    private fun setObservers() {
        viewModel.getSimplePokemons().observe(viewLifecycleOwner) {
            listAdapter.setPokemons(it)
        }

        viewModel.getIsFetching().observe(viewLifecycleOwner) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        }
    }

}