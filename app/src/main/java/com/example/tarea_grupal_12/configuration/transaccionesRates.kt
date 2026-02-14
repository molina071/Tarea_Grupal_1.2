package com.example.tarea_grupal_12.configuration

object transaccionesRates {
    const val ratesTable: String = "ratesTable"

    // Fields
    const val id: String = "id"
    const val from_code: String = "from_code"
    const val to_code: String = "to_code"
    const val rate: String = "rate"
    const val is_favorite: String = "is_favorite"
    const val is_custom: String = "is_custom"

    val createTableRates: String = "CREATE TABLE " + ratesTable + " ( " +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            from_code + " TEXT, " +
            to_code + " TEXT, " +
            rate + " REAL, " +
            is_favorite + " INTEGER DEFAULT 0, " +
            is_custom + " INTEGER DEFAULT 0);"

    val Honduras: String = "INSERT INTO ratesTable (from_code, to_code, rate) VALUES ('Honduras', 'USD', 26.50)"
    val Belice: String = "INSERT INTO ratesTable (from_code, to_code, rate) VALUES ('Belice', 'USD', 2.01)"
    val Guatemala: String = "INSERT INTO ratesTable (from_code, to_code, rate) VALUES ('Guatemala', 'USD', 7.75)"
    val ElSalvador: String = "INSERT INTO ratesTable (from_code, to_code, rate) VALUES ('El Salvador', 'USD', 1.00)"
    val Nicaragua: String = "INSERT INTO ratesTable (from_code, to_code, rate) VALUES ('Nicaragua', 'USD', 36.70)"
    val CostaRica: String = "INSERT INTO ratesTable (from_code, to_code, rate) VALUES ('Costa Rica', 'USD', 520.00)"
    val Panama: String = "INSERT INTO ratesTable (from_code, to_code, rate) VALUES ('Panama', 'USD', 1.00)"

    val dropRates: String = "DROP TABLE IF EXISTS " + ratesTable
    val selectAllRates: String = "SELECT * FROM " + ratesTable
}