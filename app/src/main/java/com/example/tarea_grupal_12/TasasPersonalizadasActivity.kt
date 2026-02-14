package com.example.tarea_grupal_12

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea_grupal_12.configuration.conexion
import com.example.tarea_grupal_12.configuration.transacciones
import com.example.tarea_grupal_12.configuration.transaccionesRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasasPersonalizadasActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var conexionDB: conexion
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasas_personalizadas)

        recyclerView = findViewById(R.id.recyclerViewTasas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            loadRates()
        }
    }

    private suspend fun loadRates() {
        val tasas = withContext(Dispatchers.IO) {
            conexionDB = conexion(this@TasasPersonalizadasActivity, transacciones.dbName, null, transacciones.version)
            db = conexionDB.readableDatabase

            val cursor: Cursor = db.rawQuery(transaccionesRates.selectAllRates, null)
            val ratesList = mutableListOf<Tasa>()

            while (cursor.moveToNext()) {
                ratesList.add(
                    Tasa(
                        cursor.getString(cursor.getColumnIndexOrThrow(transaccionesRates.from_code)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(transaccionesRates.rate))
                    )
                )
            }
            cursor.close()
            db.close()
            ratesList
        }

        recyclerView.adapter = TasasAdapter(tasas)
    }
}