package com.example.moviesbymoviedb.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesbymoviedb.R
import com.example.moviesbymoviedb.adapter.RecyclerViewAdapter
import com.example.moviesbymoviedb.model.Movie


class MoviesListFragment : Fragment(), RecyclerViewAdapter.MyClickListener {
    private var isConnected: Boolean = false
    private var originalList: List<Movie> = ArrayList()
    private var list: ArrayList<Movie> = ArrayList()
    private lateinit var viewModel: MoviesListViewModel
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var nameEditText: EditText
    private lateinit var filterByDate: ImageView
    private lateinit var sortByName: ImageView
    private var isSortedAscending = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isConnected = it.getBoolean("IS_CONNECTED")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_movies_list, container, false)
        init(root)
        initViewModel()
        initListeners()

        return root
    }

    private fun initViewModel() {
        requireActivity().application?.let {
            viewModel = MoviesListViewModel(it)
        }

        if (isConnected) {
            viewModel.setOnlineObserver().observe(viewLifecycleOwner, {
                if (!it.isNullOrEmpty()) {
                    list.addAll(it)
                    originalList = list
                    adapter.updateList(it)
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            viewModel.setLocalObserver().observe(viewLifecycleOwner, {
                if (!it.isNullOrEmpty()) {
                    if (it.size - list.size < 20) {
                        viewModel.setLoadNextPage(false)
                    }
                    list.clear()
                    list.addAll(it)
                    adapter.updateLocalList(it)
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun init(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.my_toolbar)
        nameEditText = toolbar.findViewById(R.id.search_edit_text)
        sortByName = toolbar.findViewById(R.id.sort_by_name_image_view)
        filterByDate = toolbar.findViewById(R.id.sort_by_date_image_view)
        recyclerView = view.findViewById(R.id.movies_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(parentFragment?.context, 2)
        recyclerView.addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))
        adapter = RecyclerViewAdapter(this)
        recyclerView.adapter = adapter

    }

    private fun initListeners() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager

                if ((lm as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    if (viewModel.canLoadNextPage()) {
                        viewModel.loadNextPage(isConnected)
                    }
                }
            }
        })

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    viewModel.setLoadNextPage(true)
                }
                viewModel.setLoadNextPage(false)
                val newList = adapter.filterList(s.toString())
                list.clear()
                list.addAll(newList)

            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        sortByName.setOnClickListener {
            val newList = adapter.sortAscending(isSortedAscending)
            isSortedAscending = !isSortedAscending
            updateToolbar()
            list.clear()
            list.addAll(newList)
        }

        filterByDate.setOnClickListener {
            val newList = adapter.sortByDate()
            list.clear()
            list.addAll(newList)
        }
    }

    private fun updateToolbar() {
        if (isSortedAscending) {
            sortByName.setImageResource(R.drawable.ic_asc)
        } else {
            sortByName.setImageResource(R.drawable.ic_desc)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Boolean) =
            MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("IS_CONNECTED", param1)
                }
            }
    }

    override fun onHeartClicked(position: Int) {
        try {
            val movie = list[position]
            if (!movie.isFavorite) {
                Toast.makeText(
                    context,
                    "Le film a été ajouté à vos favoris avec succés",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Le film a été retiré de vos favoris avec succés",
                    Toast.LENGTH_SHORT
                ).show()
            }
            viewModel.updateDatabase(movie)
            adapter.updateContent(position, movie)
        } catch (e: Exception) {
            Log.e("MoviesListFragment", "Error on fav")
        }
    }

    override fun onContainerClicked(position: Int) {
        val movie = list[position]
        val action =
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(movie)
        findNavController().navigate(action)
    }
}