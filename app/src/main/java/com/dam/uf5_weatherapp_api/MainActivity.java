package com.dam.uf5_weatherapp_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.uf5_weatherapp_api.data.Location;

public class MainActivity extends AppCompatActivity {

    public static final String CLAVE_LOCATION = "LOCATION";

    EditText etLatitud;
    EditText etLongitud;
    Button btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        btnConsultar = findViewById(R.id.btnConsultar);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitud = etLatitud.getText().toString();
                String longitud = etLongitud.getText().toString();

                if (latitud.trim().isEmpty() || longitud.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.no_data, Toast.LENGTH_LONG).show();
                } else {
                    Location location = new Location(Double.valueOf(latitud), Double.valueOf(longitud));
                    Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                    intent.putExtra(CLAVE_LOCATION, location);
                    startActivity(intent);
                }
            }
        });
    }
}