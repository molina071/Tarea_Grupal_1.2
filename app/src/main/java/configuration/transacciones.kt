package com.example.first_activity.configuration
//AQUI LLENO LA TABLA DE RESULTADO, QUE SE LLENA CUANDO EL USUARIO HACE LAS CONVERSIONES, QUEDA REGISTRADO EL HISTORIAL.
object transacciones {
    //DB NAME
    const val dbName: String = "converter"

    const val version: Int = 1

    //table
    const val conversions: String = "conversions"

    //tb persons field
    const val id: String = "id" //id de las conversiones
    const val from_code: String = "from_code " //moneda a comparar
    const val to_code: String = "to_code" //moneda de usa
    const val amount: String = "amount" //cantidad comparada
    const val result: String = "result" //resultado de la comparacion
    const val date: String = "date" //fecha del registro


    val createTableCoversions: String = "CREATE TABLE " + conversions + " ( " +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            from_code + " TEXT, " + //pais centroamericano
            to_code + " TEXT, " + //USA
            amount + " NUMERIC, " + //cantidad a convertir
            result + " REAL " + //resultado final
            date + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);" //fecha de registro automatica

    //DDL DROP
    val dropConversions: String = "DROP TABLE IF Exist   " + conversions

    val selectTableConversion: String = " SELECT * FROM " + conversions
}