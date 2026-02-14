package com.example.tarea_grupal_12.configuration

object transacciones {
    const val dbName: String = "converter"
    const val version: Int = 2 // <--- VERSIÓN ACTUALIZADA

    // Table name
    const val conversions: String = "conversions"

    // Fields
    const val id: String = "id"
    const val from_code: String = "from_code"
    const val to_code: String = "to_code"
    const val amount: String = "amount"
    const val result: String = "result"
    const val date: String = "date"
    const val is_favorite: String = "is_favorite"

    val createTableConversions: String = "CREATE TABLE " + conversions + " ( " +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            from_code + " TEXT, " +
            to_code + " TEXT, " +
            amount + " REAL, " +
            result + " REAL, " +
            is_favorite + " INTEGER DEFAULT 0, " +
            date + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);"

    val dropConversions: String = "DROP TABLE IF EXISTS " + conversions
    val selectTableConversion: String = "SELECT * FROM " + conversions + " ORDER BY " + date + " DESC"
}