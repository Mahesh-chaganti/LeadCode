package com.example.leadcode.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "MongoDBLastIDs")

class DataStorePersistence(val context: Context){
    private val searchLastId = stringPreferencesKey("searchLastId")
    val searchLastIdFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[searchLastId] ?: ""
        }
    suspend fun updateId() {

        context.dataStore.edit { MongoDBLastIDs ->
            val currentCounterValue = MongoDBLastIDs[searchLastId] ?: ""
            MongoDBLastIDs[searchLastId] = currentCounterValue
        }
    }
}


