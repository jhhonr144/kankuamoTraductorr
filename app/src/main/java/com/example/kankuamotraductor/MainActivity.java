package com.example.kankuamotraductor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


     private static final int REQ_CODE_SPEECH_INPUT=100;
     private TextView mEntradaVoz, mSalida;
     private ImageButton mBotonHablar;
     private ImageButton mTraducir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEntradaVoz = (TextView) findViewById(R.id.textoEntrada);
        mSalida =(TextView)findViewById(R.id.textoSalida);
        mBotonHablar= findViewById(R.id.botonTraducir);
        mTraducir=findViewById(R.id.btntraducir);

        mBotonHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarEntradaVoz();
            }
        });


        mTraducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Traducir();
            }
        });




    }

    private void iniciarEntradaVoz() {
        Intent intent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "HOLA,¿QUE DESEAS TRADUCIR ?");

        try {
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){

            Toast.makeText(this,"Lo siento no puedo escucharte",Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        mEntradaVoz.setText(result.get(0));

    }
    private String[]  palabras={
            "MANO","amiérna",
            "MANSO","sinaka",
            "MAÑANA","shigi",
            "MAYOR","mamo",
            "MÉDICO","mána",
            "MEDIO","duiku",
            "MES","saga",
            "MALO","nononéka"
    };

    private void Traducir(){
        mSalida.setText(" ");
        Toast.makeText(this,"Traduccion",Toast.LENGTH_LONG ).show();
        String[] palabrasEntrada=mEntradaVoz.getText().toString().toUpperCase().split(" ");
        for (String dato : palabrasEntrada) {
            int bandera=0;
            for (int i = 0; i < palabras.length; i+=2) {
                if(palabras[i].equals(dato)){
                    mSalida.setText(mSalida.getText()+" "+palabras[i+1]+" ");
                    bandera++;break;
                }
            }
            if(bandera==0) mSalida.setText(mSalida.getText()+" '"+dato+"' ");
        }


    }


}
