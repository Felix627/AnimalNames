package com.navarrete.animal.midterm

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Context
import android.widget.Button

class AnimalNamesActivity : AppCompatActivity() {
    private val animalList = mutableListOf<Animal>()
    private lateinit var adapter: AnimalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.animalnamesactivity)

        val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AnimalAdapter(animalList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { animal ->
            val intent = Intent(this, AnimalDetailsActivity::class.java)

            intent.putExtra("animalName", animal.animalName)
            intent.putExtra("animalDescription", animal.animalDetail)

            startActivityForResult(intent, REQUEST_CODE_ANIMAL_DETAILS)
        }

        val manageBlockButton = findViewById<Button>(R.id.manageBlockButton)
        manageBlockButton.setOnClickListener {
            val intent = Intent(this, ManageBlockActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_MANAGE_BLOCK)
        }

        refreshData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MANAGE_BLOCK && resultCode == Activity.RESULT_OK) {
            // Refresh the displayed animals when returning from ManageBlockActivity
            refreshData()
        } else if (requestCode == REQUEST_CODE_ANIMAL_DETAILS && resultCode == Activity.RESULT_OK) {
            // Refresh the displayed animals when returning from AnimalDetailsActivity
            refreshData()
        }
    }

    private fun refreshData() {
        val unblockedAnimals = getUnblockedAnimals()
        animalList.clear()
        animalList.addAll(unblockedAnimals)
        adapter.notifyDataSetChanged()
    }

    private fun getBlockedAnimals(): Set<String> {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet("blockedAnimals", emptySet()) ?: emptySet()
    }

    private fun getUnblockedAnimals(): List<Animal> {
        val blockedAnimals = getBlockedAnimals()
        return getAllAnimals().filter { it.animalName !in blockedAnimals }
    }

    private fun getAllAnimals(): List<Animal> {
        // Replace this with your data retrieval logic to get the complete list of animals
        return listOf(
            Animal("Anteater", "Eats Ants."),
            Animal("Cat", "Annoying cute fluff"),
            Animal("Dog", "The thing that won't stop barking"),
            Animal("Snake", "Not the main character of MGS"),
            Animal("Penguin", "Not the villain from Batman"),
            Animal("Tiger", "Not so annoying but will eat you")
        )
    }

    companion object {
        const val REQUEST_CODE_MANAGE_BLOCK = 123
        const val REQUEST_CODE_ANIMAL_DETAILS = 456
    }

}