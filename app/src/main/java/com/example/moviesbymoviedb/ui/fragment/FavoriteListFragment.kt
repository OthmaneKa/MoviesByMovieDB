package com.example.moviesbymoviedb.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesbymoviedb.R
import com.example.moviesbymoviedb.adapter.FavoriteRecyclerViewAdapter
import com.example.moviesbymoviedb.adapter.RecyclerViewAdapter
import com.example.moviesbymoviedb.model.Movie


private const val ARG_PARAM1 = "param1"

class FavoriteListFragment : Fragment(), FavoriteRecyclerViewAdapter.MyClickListener {
    private var param1: Int? = null
    private lateinit var viewModel: FavoriteListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteRecyclerViewAdapter
    private var list: ArrayList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite_list, container, false)
        init(root)
        initViewModel()
        return root
    }

    private fun initViewModel() {
        requireActivity().application?.let {
            viewModel = FavoriteListViewModel(it)
        }

        viewModel.setObserver().observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                if (list.isNotEmpty()) {
                    list.clear()
                }
                list.addAll(it)
                adapter.updateList(it)
            } else {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.favorite_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(parentFragment?.context, 2)
        recyclerView.addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))
        adapter = FavoriteRecyclerViewAdapter(this)
        recyclerView.adapter = adapter

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            FavoriteListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }

    override fun onContainerClicked(position: Int) {
        val movie = list[position]
        val action =
            FavoriteListFragmentDirections.actionFavoriteListFragmentToMovieDetailsFragment(movie)
        findNavController().navigate(action)
    }
}