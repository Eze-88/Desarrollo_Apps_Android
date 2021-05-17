package com.framus.BaseDeDatos

import androidx.room.*
import com.framus.Entidades.Discos

@Dao
public interface discosDAO {

    @Query("SELECT * FROM discos ORDER BY id")
    fun loadAllPersons(): MutableList<Discos?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(disco: Discos?)

    @Delete
    fun delete(disco: Discos?)

    @Query("SELECT * FROM discos WHERE titulo = :titulo")
    fun loadPersonaByName(titulo: String): Discos?

    @Update
    fun updatePerson(disco: Discos?)
}