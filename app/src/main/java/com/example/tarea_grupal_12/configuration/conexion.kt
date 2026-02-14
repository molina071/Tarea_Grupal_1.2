package com.example.tarea_grupal_12.configuration

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class conexion(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(transacciones.createTableConversions)
        db.execSQL(transaccionesRates.createTableRates)

        db.execSQL(transaccionesRates.Honduras)
        db.execSQL(transaccionesRates.Belice)
        db.execSQL(transaccionesRates.Guatemala)
        db.execSQL(transaccionesRates.ElSalvador)
        db.execSQL(transaccionesRates.Nicaragua)
        db.execSQL(transaccionesRates.CostaRica)
        db.execSQL(transaccionesRates.Panama)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(transacciones.dropConversions)
        db.execSQL(transaccionesRates.dropRates)
        onCreate(db)
    }
}