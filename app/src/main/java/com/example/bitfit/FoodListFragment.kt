package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FoodListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lookup the recyclerview in activity layout
        val rvFoods = view.findViewById<View>(R.id.recyclerView) as? RecyclerView
        // Initialize contacts
        val foods = arrayListOf<FoodEntity>()
        // Create adapter passing in the sample user data
        val adapter = FoodAdapter(foods)
        // Attach the adapter to the recyclerview to populate items
        if (rvFoods != null) {
            rvFoods.adapter = adapter
        }
        // Set layout manager to position the items
        if (rvFoods != null) {
            rvFoods.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false ).also {
                val dividerItemDecoration = DividerItemDecoration(activity, it.orientation)
                rvFoods.addItemDecoration(dividerItemDecoration)

            }
        }

        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                databaseList.map() { entity ->
                    FoodEntity(
                        entity.food,
                        entity.calories,
                    )
                }.also { mappedList ->
                    foods.clear()
                    foods.addAll(mappedList)
                    adapter.notifyDataSetChanged()

                }
            }
        }
    }

    companion object {
        fun newInstance(): FoodListFragment {
            return FoodListFragment()
        }
    }
}