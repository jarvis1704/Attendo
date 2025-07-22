package com.biprangshu.attendo.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
){

    private val dataStore = context.dataStore



    companion object{

        private val IS_FIRST_APP_OPEN = booleanPreferencesKey("first_app_open")
        private val REQUIRED_PERCENTAGE = floatPreferencesKey("required_percentage")

    }

    val isFirstAppOpen: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_FIRST_APP_OPEN] ?: true
    }

    val requiredPercentage: Flow<Float> = dataStore.data.map {
        preferences ->
        preferences[REQUIRED_PERCENTAGE] ?: 75f
    }

    suspend fun firstAppOpen(isFirstAppOpen: Boolean){
        dataStore.edit { preferences ->
            preferences[IS_FIRST_APP_OPEN] = isFirstAppOpen
        }
    }

    suspend fun changeRequiredPercentage(requiredPercentage: Float){
        dataStore.edit {
            preferences ->
            preferences[REQUIRED_PERCENTAGE] = requiredPercentage
        }
    }


    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(IS_FIRST_APP_OPEN)
            preferences.remove(REQUIRED_PERCENTAGE)
        }
    }

    suspend fun nukeAllPreferences(){
        //as it says, use with caution
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

