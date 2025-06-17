package com.example.exament2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonaViewModel(application: Application) : AndroidViewModel(application) {
    private val db = PadronDatabase.getDatabase(application)
    val personas: LiveData<List<Persona>> = db.personaDao().getAll()

    fun filtrar(filtro: String): LiveData<List<Persona>> = db.personaDao().filtrar("%$filtro%")

    fun insertar(persona: Persona, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val existe = db.personaDao().existeDocumento(persona.documento) > 0
            if (existe) {
                onResult(false, "El documento ya existe")
            } else {
                db.personaDao().insert(persona)
                onResult(true, "Persona registrada correctamente")
            }
        }
    }

    fun eliminar(persona: Persona) {
        viewModelScope.launch(Dispatchers.IO) {
            db.personaDao().eliminar(persona)
        }
    }

    fun actualizar(persona: Persona, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            db.personaDao().actualizar(persona)
            onResult(true, "Persona actualizada correctamente")
        }
    }
} 