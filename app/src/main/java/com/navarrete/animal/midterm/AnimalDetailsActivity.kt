package com.navarrete.animal.midterm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import android.widget.TextView

class AnimalDetailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_details)

        val animalName = intent.getStringExtra("animalName")
        val animalDescription = intent.getStringExtra("animalDescription")

        val animalNameTextView = findViewById<TextView>(R.id.animalNameTextView)
        val animalDescriptionTextView = findViewById<TextView>(R.id.animalDescriptionTextView)

        animalNameTextView.text = animalName
        animalDescriptionTextView.text = animalDescription

        val blockAnimalButton = findViewById<Button>(R.id.blockAnimalButton)
        blockAnimalButton.setOnClickListener {

            blockAnimal()
        }
    }

    private fun blockAnimal() {

        val blockedAnimals = getBlockedAnimals().toMutableSet()
        val animalName = intent.getStringExtra("animalName") ?: ""
        blockedAnimals.add(animalName)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putStringSet("blockedAnimals", blockedAnimals).apply()

        // Set the result to indicate success
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun getBlockedAnimals(): Set<String> {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet("blockedAnimals", emptySet()) ?: emptySet()
    }


}