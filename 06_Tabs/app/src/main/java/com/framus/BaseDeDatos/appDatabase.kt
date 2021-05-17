package com.framus.BaseDeDatos

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.framus.Entidades.Discos
import com.framus.Entidades.Persona


@Database(entities = [Persona::class, Discos::class], version = 1, exportSchema = false)

public  abstract class appDatabase : RoomDatabase() {

    abstract fun usuarioDao(): usuarioDao
    abstract fun discosDAO(): discosDAO

    companion object {
        var INSTANCE: appDatabase? = null

        fun getAppDataBase(context: Context): appDatabase? {
            if (INSTANCE == null) {
                synchronized(appDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        appDatabase::class.java,
                        "miDB"
                    ).allowMainThreadQueries().build() // No es lo mas recomendable que se ejecute en el mainthread
                }
            }
            return INSTANCE
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}