package com.dam.uf5_weatherapp_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.uf5_weatherapp_api.data.Location;
import com.dam.uf5_weatherapp_api.data.WeatherRes;
import com.dam.uf5_weatherapp_api.util.APIRestServiceWeather;
import com.dam.uf5_weatherapp_api.util.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherActivity extends AppCompatActivity {

    public static final String CLAVE_KEY = "11ce4328111023379e0fdc9d28c24a02";
    public static final String CLAVE_EXCLUDE = "?exclude=minutely,hourly,daily,alerts,flags&lang=";
    public static final String CLAVE_LANG = "es";

    TextView tvCiudad;
    ImageView ivIcono;
    TextView tvHora;
    TextView tvTemperatura;
    TextView tvHumedad;
    TextView tvLluvia;
    TextView tvPrediccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Location location = getIntent().getParcelableExtra(MainActivity.CLAVE_LOCATION);

        tvCiudad = findViewById(R.id.tvCity);
        ivIcono = findViewById(R.id.ivIcon);
        tvHora = findViewById(R.id.tvHora);
        tvTemperatura = findViewById(R.id.tvTemp);
        tvHumedad = findViewById(R.id.tvHumedad);
        tvLluvia = findViewById(R.id.tvLluvia);
        tvPrediccion = findViewById(R.id.tvPrediccion);

        // Llamar al método que consume la API con los parámetros necesarios
        consumeAPI(location.getLat(), location.getLon());
    }

    private void consumeAPI(Double latitud, Double longitud) {
        // Crear el cliente de Retrofit
        Retrofit r = RetrofitClient.getClient(APIRestServiceWeather.BASE_URL);
        // Crear el servicio
        APIRestServiceWeather ars = r.create(APIRestServiceWeather.class);
        // Crear la llamada a la API con los parámetros necesarios
        Call<WeatherRes> call = ars.obtenerTiempo(CLAVE_KEY, latitud, longitud, CLAVE_EXCLUDE, CLAVE_LANG);

        // Ejecutar la llamada
        call.enqueue(new Callback<WeatherRes>() {
            @Override
            // onResponse: se ejecuta cuando la llamada es correcta
            public void onResponse(Call<WeatherRes> call, Response<WeatherRes> response) {
                // Comprobar que la respuesta es correcta
                if (response.isSuccessful()) {
                    // Obtener el objeto WeatherRes
                    WeatherRes weatherRes = response.body();
                    // Asignar los valores
                    tvCiudad.setText(weatherRes.getTimezone());
                    // Obtener el recurso del drawable con el nombre del icono de la API y asignarlo al ImageView
                    int resourceId = getResources().getIdentifier(weatherRes.getCurrently().getIcon(), "drawable", getPackageName());
                    ivIcono.setImageResource(resourceId);
                    // Convertir el timestamp a fecha
                    Date date = new Date(weatherRes.getCurrently().getTime() * 1000);
                    // Formatear la fecha y asignarla al TextView
                    tvHora.setText(new SimpleDateFormat("hh:mm a").format(date));
                    // Convertir la temperatura de Fahrenheit a Celsius y asignarla al TextView con 1 decimal
                    double tempCelcius = (weatherRes.getCurrently().getTemperature() - 32) * 5 / 9;
                    tvTemperatura.setText(Math.round(tempCelcius*10.0)/10.0 + "ºC");
                    tvHumedad.setText((weatherRes.getCurrently().getHumidity()*100) + "%");
                    tvLluvia.setText((weatherRes.getCurrently().getPrecipProbability()*100) + "%");
                    tvPrediccion.setText(weatherRes.getCurrently().getSummary());
                }
            }

            @Override
            public void onFailure(Call<WeatherRes> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }
}