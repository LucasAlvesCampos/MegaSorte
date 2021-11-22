package com.example.ac3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val PRIVATE_MODE = 0
    private val PREF_NAME = "Configuracao"
    private val CONTROL_NAME = "primeiraExecucao"
    private val TEXT_NAME = "nome"
    private val TEXT_NUMBER = "numero"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrada)

        controle()
        recuperaConfiguracoes()

        val botaoSorteio = findViewById<Button>(R.id.btn_sortear)
        val campoNome = findViewById<EditText>(R.id.et_name)
        val campoNumero = findViewById<EditText>(R.id.et_number)



        botaoSorteio.setOnClickListener {

            if (campoNome.text.isEmpty() && campoNumero.text.isEmpty()) {
                Toast.makeText(this,"Por favor preencha os campos acima.", Toast.LENGTH_LONG).show()
            }

            else {
                if (campoNome.text.isEmpty()) {
                    Toast.makeText(this,"Por favor digite seu nome.", Toast.LENGTH_LONG).show()
                }
                else {
                    if (campoNumero.text.isEmpty() || campoNumero.text.toString().toInt() < 6 || campoNumero.text.toString().toInt() > 15 ) {
                        Toast.makeText(this,"Por favor digite um número entre 6 e 15.", Toast.LENGTH_LONG).show()
                    }

                }
            }

            if (campoNome.text.isNotEmpty() && campoNumero.text.isNotEmpty() && campoNumero.text.toString().toInt() >= 6 && campoNumero.text.toString().toInt() <= 15 ){

                val numero = campoNumero.text.toString().toInt()
                val nome = campoNome.text.toString()


                val intent = Intent(this,ResultadoSorteio::class.java)
                intent.putExtra("EXTRA_NUMBER", numero)
                intent.putExtra("EXTRA_NAME", nome)

                salvarConfiguracoes()
                startActivity(intent)

            }




        }


    }


    override fun onPause() {
        super.onPause()
        salvarConfiguracoes()
    }


    private fun controle() {

        val preferencia = this.getSharedPreferences(PREF_NAME,PRIVATE_MODE)

        val dado = preferencia.getBoolean(CONTROL_NAME, true)

        if (dado == true) {
            Toast.makeText(this,"Bem vindo!", Toast.LENGTH_LONG).show()
            val editor = preferencia.edit()

            editor.putBoolean(CONTROL_NAME,false)

            editor.apply()

        } else {
            Toast.makeText(this,"Obrigado por retornar!", Toast.LENGTH_LONG).show()
        }

    }

    private fun salvarConfiguracoes() {
        val textNameView : EditText = findViewById(R.id.et_name)
        val textNumberView: EditText = findViewById(R.id.et_number)

        val preferencias = this.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        val editor = preferencias.edit()

        editor.putString(TEXT_NAME, textNameView.text.toString())
        editor.putString(TEXT_NUMBER, textNumberView.text.toString())
        editor.apply()


    }

    private fun recuperaConfiguracoes() {
        val preferencias = this.getSharedPreferences(PREF_NAME,PRIVATE_MODE)

        val textName = preferencias.getString(TEXT_NAME, "")
        val textNumber = preferencias.getString(TEXT_NUMBER, "")


        val textNameView : EditText = findViewById(R.id.et_name)
        val textNumberView : EditText = findViewById(R.id.et_number)


        textNameView.setText(textName)
        textNumberView.setText(textNumber)

    }
}



