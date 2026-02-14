package com.example.tarea_grupal_12

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tarea_grupal_12.configuration.conexion
import com.example.tarea_grupal_12.configuration.transacciones
import com.example.tarea_grupal_12.configuration.transaccionesRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var etMonto: EditText
    private lateinit var etUnidad: EditText
    private lateinit var etTotal: EditText
    private lateinit var spPaises: Spinner
    private lateinit var btnConvertir: Button
    private lateinit var btnHistorial: Button
    private lateinit var btnTasas: Button

    private lateinit var db: SQLiteDatabase
    private lateinit var conexionDB: conexion

    private var tasaSeleccionada: Double = 0.0
    private var paisSeleccionado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarVistas()
        configurarBotones()

        lifecycleScope.launch {
            initDatabaseAndLoadData()
        }
    }

    private suspend fun initDatabaseAndLoadData() {
        withContext(Dispatchers.IO) {
            conexionDB = conexion(this@MainActivity, transacciones.dbName, null, transacciones.version)
            db = conexionDB.writableDatabase
        }

        cargarPaisesEnSpinner()
        configurarSpinner()
    }

    private fun inicializarVistas() {
        etMonto = findViewById(R.id.etMonto)
        etUnidad = findViewById(R.id.etUnidad)
        etTotal = findViewById(R.id.etTotal)
        spPaises = findViewById(R.id.spPaises)
        btnConvertir = findViewById(R.id.btnConvertir)
        btnHistorial = findViewById(R.id.btnHistorial)
        btnTasas = findViewById(R.id.btnTasas)

        etUnidad.isEnabled = false
        etTotal.isEnabled = false
    }

    private fun configurarBotones() {
        btnConvertir.setOnClickListener {
            realizarConversion()
        }

        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java)
            startActivity(intent)
        }

        btnTasas.setOnClickListener {
            val intent = Intent(this, TasasPersonalizadasActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarPaisesEnSpinner() {
        lifecycleScope.launch {
            val paises = withContext(Dispatchers.IO) {
                val cursor: Cursor = db.rawQuery(
                    "SELECT DISTINCT ${transaccionesRates.from_code} FROM ${transaccionesRates.ratesTable}",
                    null
                )

                val paisesList = mutableListOf("Seleccione un país")
                while (cursor.moveToNext()) {
                    paisesList.add(cursor.getString(0))
                }
                cursor.close()
                paisesList
            }

            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, paises)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spPaises.adapter = adapter
        }
    }

    private fun configurarSpinner() {
        spPaises.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position > 0) {
                    paisSeleccionado = parent?.getItemAtPosition(position).toString()
                    lifecycleScope.launch {
                        cargarTasa(paisSeleccionado)
                    }
                } else {
                    paisSeleccionado = ""
                    tasaSeleccionada = 0.0
                    etUnidad.text.clear()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }
        }
    }

    private suspend fun cargarTasa(pais: String) {
        val rate = withContext(Dispatchers.IO) {
            val cursor = db.rawQuery(
                "SELECT ${transaccionesRates.rate} FROM ${transaccionesRates.ratesTable} WHERE ${transaccionesRates.from_code} = ?",
                arrayOf(pais)
            )
            var loadedRate = 0.0
            if (cursor.moveToFirst()) {
                loadedRate = cursor.getDouble(0)
            }
            cursor.close()
            loadedRate
        }
        tasaSeleccionada = rate
        etUnidad.setText(tasaSeleccionada.toString())
    }

    private fun realizarConversion() {
        if (paisSeleccionado.isEmpty()) {
            Toast.makeText(this, "Seleccione un país", Toast.LENGTH_SHORT).show()
            return
        }

        val montoStr = etMonto.text.toString()
        if (montoStr.isEmpty()) {
            Toast.makeText(this, "Ingrese un monto", Toast.LENGTH_SHORT).show()
            return
        }

        val monto = montoStr.toDoubleOrNull()
        if (monto == null || monto <= 0) {
            Toast.makeText(this, "Ingrese un monto válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (tasaSeleccionada == 0.0) {
            Toast.makeText(this, "La tasa de cambio no puede ser cero.", Toast.LENGTH_SHORT).show()
            return
        }

        val resultado = monto / tasaSeleccionada
        etTotal.setText(String.format("%.2f", resultado))

        lifecycleScope.launch(Dispatchers.IO) {
            guardarEnHistorial(paisSeleccionado, "USD", monto, resultado)
        }

        mostrarDesglose(paisSeleccionado, monto, tasaSeleccionada, resultado)
    }

    private fun guardarEnHistorial(from: String, to: String, amount: Double, result: Double) {
        val values = ContentValues().apply {
            put(transacciones.from_code, from)
            put(transacciones.to_code, to)
            put(transacciones.amount, amount)
            put(transacciones.result, result)
        }

        db.insert(transacciones.conversions, null, values)
    }

    private fun mostrarDesglose(pais: String, monto: Double, tasa: Double, resultado: Double) {
        val mensaje = """
            DESGLOSE DE LA CONVERSIÓN:
            
            País: $pais
            Monto ingresado: $monto
            Tasa aplicada: 1 USD = $tasa $pais
            
            Cálculo:
            $monto $pais ÷ $tasa = $resultado USD
            
            Resultado final: $$resultado
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Detalle de Conversión")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::db.isInitialized && db.isOpen) {
            db.close()
        }
        if (::conexionDB.isInitialized) {
            conexionDB.close()
        }
    }
}