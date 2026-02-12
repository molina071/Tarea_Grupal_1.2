package com.example.first_activity.configuration
//AQUI LLENO LA TABLA DE RESULTADO, QUE SE LLENA CUANDO EL USUARIO HACE LAS CONVERSIONES, QUEDA REGISTRADO EL HISTORIAL.
object transaccionesRates {
    //DB NAME
    const val dbName: String = "converter"

    const val version: Int = 1

    //table
    const val ratesTable: String = "ratesTable"

    //tb persons field
    const val id: String = "id" //id de las conversiones
    const val from_code: String = "from_code " //moneda a comparar
    const val to_code: String = "to_code" //moneda de usa
    const val rate: String = "rate" //equivalencia
    val createTableRates: String = "CREATE TABLE " + ratesTable + " ( " +
            id + " TEXT PRIMARY KEY, " +
            from_code + " TEXT, " + //pais centroamericano
            to_code + " TEXT, " + //USA
            rate + " NUMERIC); "  //tasa de cambio

    val Honduras: String = "INSERT INTO ratesTable (id, from_code, to_code, rate) VALUES ('504', 'Honduras', 'USA', 26.50)"
    val Belice: String = "INSERT INTO ratesTable (id, from_code, to_code, rate) VALUES ('501', 'Belice', 'USA', 2.01)"
    val Guatemala: String = "INSERT INTO ratesTable (id, from_code, to_code, rate) VALUES ('502', 'Guatemela', 'USA', 7.75)"
    val Elsalvador : String = "INSERT INTO ratesTable (id, from_code, to_code, rate) VALUES ('503', 'El Salvador', 'USA', 1)"
    val Nicaragua: String = "INSERT INTO ratesTable (id, from_code, to_code, rate) VALUES ('505', 'Nicaragua', 'USA', 36.70)"
    val CostaRica: String = "INSERT INTO ratesTable (id, from_code, to_code, rate) VALUES ('506', 'Costa Rica', 'USA', 520)"
    val Panama: String = "INSERT INTO ratesTable (id, from_code, to_code, rate) VALUES ('507', 'Panama', 'USA', 1)"

    //DDL DROP
    val dropConversions: String = "DROP TABLE IF Exist   " + ratesTable

    val selectTableConversion: String = " SELECT * FROM " + ratesTable
}