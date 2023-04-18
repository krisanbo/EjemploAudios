package com.example.ejemploaudios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button play_pause, bt_repetir;
    MediaPlayer mp;
    ImageView iv;

    private MediaRecorder grabacion;
    private String archivoSalida = null;
    private Button bt_grabar,bt_reproducir;
    int repetir = 1, posicion = 0;

    MediaPlayer vectormp[] = new MediaPlayer[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_pause = (Button) findViewById(R.id.bt_play);
        bt_repetir = (Button) findViewById(R.id.bt_repetir);
        iv = (ImageView) findViewById(R.id.iv1);
        bt_grabar = (Button) findViewById(R.id.bt_grabar);
        bt_reproducir=(Button)findViewById(R.id.bt_reproducir);

        vectormp[0] = MediaPlayer.create(this, R.raw.race);
        vectormp[1] = MediaPlayer.create(this, R.raw.tea);
        vectormp[2] = MediaPlayer.create(this, R.raw.sound);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }

    //metodo botn play_pause

    public void PlayPause(View view) {

        if (vectormp[posicion].isPlaying()) {
            vectormp[posicion].pause();
            play_pause.setBackgroundResource(R.drawable.reproducir);
            Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
        } else {
            vectormp[posicion].start();
            play_pause.setBackgroundResource(R.drawable.pausa);
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        }
    }

    //boton stop

    public void Stop(View view) {

        if (vectormp[posicion] != null) {
            vectormp[posicion].stop();

            vectormp[0] = MediaPlayer.create(this, R.raw.race);
            vectormp[1] = MediaPlayer.create(this, R.raw.tea);
            vectormp[2] = MediaPlayer.create(this, R.raw.sound);
            posicion = 0;
            play_pause.setBackgroundResource(R.drawable.reproducir);
            iv.setImageResource(R.drawable.portada1);
            Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo repetir pista

    public void Repetir(View view) {
        if (repetir == 1) {
            bt_repetir.setBackgroundResource(R.drawable.repetir);
            Toast.makeText(this, "no repetir", Toast.LENGTH_SHORT).show();
            // para que no se repita
            vectormp[posicion].setLooping(false);
            repetir = 2;

        } else {
            bt_repetir.setBackgroundResource(R.drawable.no_repetir);
            Toast.makeText(this, "repetir", Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(true);
            repetir = 1;

        }
    }
    // metodo siguiente

    public void siguiente(View view) {
        if (posicion < vectormp.length - 1) {
            if (vectormp[posicion].isPlaying()) {
                vectormp[posicion].stop();
                vectormp[0] = MediaPlayer.create(this, R.raw.race);
                vectormp[1] = MediaPlayer.create(this, R.raw.tea);
                vectormp[2] = MediaPlayer.create(this, R.raw.sound);
                posicion++;
                vectormp[posicion].start();
                //portada
                if (posicion == 0) {
                    iv.setImageResource(R.drawable.portada1);
                } else if (posicion == 1) {
                    iv.setImageResource(R.drawable.portada2);
                } else if (posicion == 2) {
                    iv.setImageResource(R.drawable.portada3);
                }

            } else {
                posicion++;
                if (posicion == 0) {
                    iv.setImageResource(R.drawable.portada1);
                } else if (posicion == 1) {
                    iv.setImageResource(R.drawable.portada2);
                } else if (posicion == 2) {
                    iv.setImageResource(R.drawable.portada3);
                }
            }
        } else {
            Toast.makeText(this, "no hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo boton anterior
    public void Anterior(View view) {
        if (posicion >= 1) {
            if (vectormp[posicion].isPlaying()) {
                vectormp[posicion].stop();
                vectormp[0] = MediaPlayer.create(this, R.raw.race);
                vectormp[1] = MediaPlayer.create(this, R.raw.tea);
                vectormp[2] = MediaPlayer.create(this, R.raw.sound);
                posicion++;
                vectormp[posicion].start();
                //portada
                if (posicion == 0) {
                    iv.setImageResource(R.drawable.portada1);
                } else if (posicion == 1) {
                    iv.setImageResource(R.drawable.portada2);
                } else if (posicion == 2) {
                    iv.setImageResource(R.drawable.portada3);
                }

            } else {
                posicion++;
                if (posicion == 0) {
                    iv.setImageResource(R.drawable.portada1);
                } else if (posicion == 1) {
                    iv.setImageResource(R.drawable.portada2);
                } else if (posicion == 2) {
                    iv.setImageResource(R.drawable.portada3);
                }
            }

        } else {
            Toast.makeText(this, "no hay mas canciones", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo Recorder
    public void Recorder(View view) {
        if (grabacion==null){
            File ruta= new File(Environment.getExternalStorageDirectory() + "/Music/files/1/");
            if(!ruta.exists()){
                ruta.mkdir();
            }
            archivoSalida= Environment.getExternalStorageDirectory().getAbsolutePath() + "grabacion.mp3";
            grabacion=new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            grabacion.setOutputFile(archivoSalida);

            try{
                grabacion.prepare();
                grabacion.start();


            }catch (IOException e){}
            bt_grabar.setBackgroundResource(R.drawable.rec);



            Toast.makeText(this, "grabando", Toast.LENGTH_SHORT).show();
        }else{
            grabacion.stop();
            grabacion.release();
            grabacion=null;
            bt_grabar.setBackgroundResource(R.drawable.stop_rec);
            Toast.makeText(this, "grabacion finalizada", Toast.LENGTH_SHORT).show();

        }
    }

    //metodo reproducir
    public void Reproducir(View view){
        MediaPlayer r= new MediaPlayer();
        try{
            r.setDataSource(archivoSalida);
            r.prepare();


        }catch (IOException e){}
        r.start();

        Toast.makeText(this, "Reproduciendo audio", Toast.LENGTH_SHORT).show();

    }

}


