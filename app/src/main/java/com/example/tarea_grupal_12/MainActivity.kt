package com.example.tarea_grupal_12

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.first_activity.configuration.conexion
import com.example.first_activity.configuration.transacciones

class MainActivity : AppCompatActivity() {

    private var monto: EditText?=null;
    private var unidad: EditText?=null;
    private var total: EditText?=null;

    private var btn_result: Button?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        fillSpinner();

        val spinner: Spinner = findViewById(R.id.paises)
        unidad = findViewById<View?>(R.id.unidad) as EditText
        monto = findViewById<View?>(R.id.monto) as EditText
        total = findViewById<View?>(R.id.total) as EditText
        btn_result = findViewById<Button?>(R.id.btn_convertir)


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val itemSeleccionado = parent.getItemAtPosition(position).toString()

                val conexion =
                    conexion(this@MainActivity, transacciones.dbName, null, transacciones.version)
                val db = conexion.getWritableDatabase()

                val cursor = db.rawQuery(
                    "SELECT rate FROM ratesTable WHERE from_code = '$itemSeleccionado'",null
                )

                if (cursor.moveToFirst()) {
                    val rate = cursor.getDouble(cursor.getColumnIndexOrThrow("rate"))

                   //envia la equivalencia P/D al View (unidad)
                   unidad?.setText(rate.toString());

                    //obtiene el monto total
                    val montoDouble = monto?.text.toString().toDoubleOrNull();

                    //divide el monto entre la equivalencia P/D
                    val tot = montoDouble?.div(rate);

                    //envia el total al View (total)
                    total?.setText(tot.toString())
                }
                cursor.close()
                db.close()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        btn_result?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showResult();
            }
        })
    }

    private fun fillSpinner() {
        val spPaises: Spinner = findViewById(R.id.paises)

        val ListaPais = arrayOf("Seleccione un lenguaje","Honduras","Nicaragua",
            "Panama","El Salvador","Guatemala","Costa Rica","Belice");

        var adaptador : ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,ListaPais);
        spPaises.adapter = adaptador;
    }

    private fun showResult() {
        Toast.makeText(this, "estas ejecutando este boton", Toast.LENGTH_LONG)
    }

}
