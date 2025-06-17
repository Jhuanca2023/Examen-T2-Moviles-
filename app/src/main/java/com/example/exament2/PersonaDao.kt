package com.example.exament2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface PersonaDao {
    @Query("SELECT * FROM personas")
    fun getAll(): LiveData<List<Persona>>

    @Query("SELECT * FROM personas WHERE nombre LIKE :filtro OR documento LIKE :filtro")
    fun filtrar(filtro: String): LiveData<List<Persona>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(persona: Persona)

    @Query("SELECT COUNT(*) FROM personas WHERE documento = :documento")
    suspend fun existeDocumento(documento: String): Int

    @Delete
    suspend fun eliminar(persona: Persona)

    @Update
    suspend fun actualizar(persona: Persona)
} 