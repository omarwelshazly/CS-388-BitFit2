package com.example.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class FoodDashboardFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val foods = arrayListOf<FoodEntity>()
        val avgCalories = view.findViewById<TextView>(R.id.avg_value)
        val minCalories = view.findViewById<TextView>(R.id.minimum_value)
        val maxCalories = view.findViewById<TextView>(R.id.maximum_value)

        var avg = 0
        var min: Int
        var max: Int
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
                    min = mappedList[0].calories.toInt()
                    max = mappedList[0].calories.toInt()
                    for (i in mappedList.indices) {
                        avg += mappedList[i].calories.toInt()
                        if (mappedList[i].calories.toInt() < min) {
                            min = mappedList[i].calories.toInt()
                        }
                        if (mappedList[i].calories.toInt() > max) {
                            max = mappedList[i].calories.toInt()
                        }
                    }
                    avg /= mappedList.size
                    avgCalories.text = avg.toString()
                    minCalories.text = min.toString()
                    maxCalories.text = max.toString()
                }
            }
        }



    }

        companion object {
            fun newInstance(): FoodDashboardFragment{
                return FoodDashboardFragment()
            }
        }
}