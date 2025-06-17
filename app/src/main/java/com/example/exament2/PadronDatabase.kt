package com.example.exament2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Persona::class], version = 1, exportSchema = false)
abstract class PadronDatabase : RoomDatabase() {
    abstract fun personaDao(): PersonaDao

    companion object {
        @Volatile
        private var INSTANCE: PadronDatabase? = null

        fun getDatabase(context: Context): PadronDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PadronDatabase::class.java,
                    "padron_personas.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 