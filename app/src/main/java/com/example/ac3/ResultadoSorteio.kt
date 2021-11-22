package com.example.ac3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultadoSorteio : AppCompatActivity() {

    private val PRIVATE_MODE = 0
    private val PREF_NAME = "Configuracao"
    private val TEXT_RESULTADO = "Resultado"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_sorteio)

        val ultimoSorteado = findViewById<TextView>(R.id.ultimoResultado)

        val preferencias = this.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        val textResultado = preferencias.getString(TEXT_RESULTADO, "Ainda não foi sorteado nenhum jogo.")
        ultimoSorteado.setText(textResultado)

        val nome = intent.getStringExtra("EXTRA_NAME")
        val nomeUsuario = findViewById<TextView>(R.id.nome)
        nomeUsuario.text = "Boa sorte, " + nome + "!"
        val botaoSorteio: Button = findViewById(R.id.botaoSortear)
        val botaoSair: Button = findViewById(R.id.botaoSair)
        val botaoVoltar : Button = findViewById(R.id.botaoVoltar)

        botaoSorteio.setOnClickListener {
            sorteio()
        }

        botaoSair.setOnClickListener {
            finishAffinity()
        }

        botaoVoltar.setOnClickListener {
            finish()
        }

    }

    private fun sorteio() {
        val number = intent.getIntExtra("EXTRA_NUMBER", 0)
        val resultado = findViewById<TextView>(R.id.resultado)
        val ultimoSorteado = findViewById<TextView>(R.id.ultimoResultado)
        val botaoSorteio: Button = findViewById(R.id.botaoSortear)



        val numSorteados:MutableList<Int> = mutableListOf()

        while (numSorteados.size < number){
            val numSort = (1..60).random()
            if (numSort !in numSorteados){
                numSorteados.add(numSort)}
        }
        resultado.text = numSorteados.sorted().toString().drop(1).dropLast(1)

        val preferencias = this.getSharedPreferences(PREF_NAME,PRIVATE_MODE)

        val textResultado = preferencias.getString(TEXT_RESULTADO, numSorteados.sorted().toString().drop(1).dropLast(1))
        ultimoSorteado.setText(textResultado)
        salvarConfiguracoes()
        botaoSorteio.text = "Sortear de novo"




    }

    private fun salvarConfiguracoes() {
        val textResultadoView : TextView = findViewById(R.id.resultado)


        val preferencias = this.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        val editor = preferencias.edit()

        editor.putString(TEXT_RESULTADO, textResultadoView.text.toString())

        editor.apply()
    }
}