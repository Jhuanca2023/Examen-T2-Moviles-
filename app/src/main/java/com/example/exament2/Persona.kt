package com.example.exament2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class Persona(
    @PrimaryKey val documento: String,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val estadoCivil: String?,
    val distrito: String?,
    val generoMasculino: Boolean,
    val generoFemenino: Boolean
) 