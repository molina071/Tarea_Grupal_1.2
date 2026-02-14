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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistorialActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var conexionDB: conexion
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        recyclerView = findViewById(R.id.recyclerViewHistorial)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            loadHistory()
        }
    }

    private suspend fun loadHistory() {
        val conversiones = withContext(Dispatchers.IO) {
            conexionDB = conexion(this@HistorialActivity, transacciones.dbName, null, transacciones.version)
            db = conexionDB.readableDatabase

            val cursor: Cursor = db.rawQuery(transacciones.selectTableConversion, null)
            val historyList = mutableListOf<Conversion>()

            while (cursor.moveToNext()) {
                historyList.add(
                    Conversion(
                        cursor.getString(cursor.getColumnIndexOrThrow(transacciones.from_code)),
                        cursor.getString(cursor.getColumnIndexOrThrow(transacciones.to_code)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(transacciones.amount)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(transacciones.result)),
                        cursor.getString(cursor.getColumnIndexOrThrow(transacciones.date))
                    )
                )
            }
            cursor.close()
            db.close()
            historyList
        }

        recyclerView.adapter = HistorialAdapter(conversiones)
    }
}