package com.framus.BaseDeDatos

import androidx.room.*
import com.framus.Entidades.Persona

@Dao
public interface usuarioDao {

    @Query("SELECT * FROM users ORDER BY id")
    fun loadAllPersons(): MutableList<Persona?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(user: Persona?)

    @Delete
    fun delete(user: Persona?)

    @Query("SELECT * FROM users WHERE usuario = :usuario")
    fun loadPersonaByName(usuario: String): Persona?

    @Update
    fun updatePerson(user: Persona?)
}