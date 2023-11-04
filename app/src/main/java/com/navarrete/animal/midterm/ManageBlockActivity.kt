package com.navarrete.animal.midterm

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.content.Context
import android.widget.ListView

class ManageBlockActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_block)

        val blockedAnimals = getBlockedAnimals()

        val blockedAnimalsListView = findViewById<ListView>(R.id.blockedAnimalsListView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, blockedAnimals.toList())

        blockedAnimalsListView.adapter = adapter

        blockedAnimalsListView.setOnItemClickListener { _, _, position, _ ->
            val unblockedAnimalName = blockedAnimals.toTypedArray()[position]
            unblockAnimal(unblockedAnimalName)
        }
    }

        private fun getBlockedAnimals(): Set<String> {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet("blockedAnimals", emptySet()) ?: emptySet()
    }

    private fun unblockAnimal(animalName: String) {
        val blockedAnimals = getBlockedAnimals().toMutableSet()
        blockedAnimals.remove(animalName)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putStringSet("blockedAnimals", blockedAnimals).apply()

        setResult(Activity.RESULT_OK)
        finish()
    }

}