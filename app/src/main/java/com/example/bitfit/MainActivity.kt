package com.example.bitfit


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val foods = arrayListOf<FoodEntity>()
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("FOOD_LIST", foods)
            startActivity(intent)
        }

        val fragmentManager: FragmentManager = supportFragmentManager

        // define your fragments here
        val foodListFragment: Fragment = FoodListFragment()
        val foodDashboardFragment: Fragment = FoodDashboardFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.action_favorites -> fragment = foodListFragment
                R.id.action_schedules -> fragment = foodDashboardFragment
            }
            fragmentManager.beginTransaction().replace(R.id.article_frame_layout, fragment).commit()
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_favorites
    }

    private fun replaceFragment(foodListFragment: FoodListFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.article_frame_layout, foodListFragment)
        fragmentTransaction.commit()
    }
}
