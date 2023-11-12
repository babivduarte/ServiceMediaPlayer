package com.example.exemploservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart, buttonStop;
    private TextView textViewNumeros, textViewConfere;
    private EditText editTextResultado;
    private int resultadoSorteio;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        textViewNumeros = findViewById(R.id.textViewNumeros);
        textViewConfere = findViewById(R.id.textViewConfere);
        editTextResultado = findViewById(R.id.editTextResultado);

        it = new Intent(MainActivity.this,MyService.class);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();
                sortearNumeros();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parar();
                conferirResultado(Integer.parseInt(editTextResultado.getText().toString()));
            }
        });

    }

    private void parar() {
        stopService(it);
    }

    private void iniciar() {
        Log.e("thread1",Thread.currentThread().getName());
        startService(it);
    }

    public void sortearNumeros(){
        resultadoSorteio = 0;
        Random random = new Random();
        Handler handler = new Handler();
        editTextResultado.setVisibility(View.VISIBLE);
        buttonStop.setVisibility(View.VISIBLE);

        for(int i=0;i<5;i++){
            final int valor = random.nextInt(20);
            resultadoSorteio += valor;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textViewNumeros.setText(String.valueOf(valor));
                }
            }, i * 3000);
        }
    }

    public void conferirResultado(int valor){
        textViewConfere.setText("resultado sorteio: "+ resultadoSorteio + "   // valor inserido: "+ valor);
        if (valor == resultadoSorteio){
            Toast.makeText(this,"Aeee!! Acertou!!!",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Não foi dessa vez! :(",Toast.LENGTH_LONG).show();
        }
    }


}