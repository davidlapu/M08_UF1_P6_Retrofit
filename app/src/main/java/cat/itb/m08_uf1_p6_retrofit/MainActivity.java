package cat.itb.m08_uf1_p6_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cat.itb.m08_uf1_p6_retrofit.models.Data;
import cat.itb.m08_uf1_p6_retrofit.models.People;
import cat.itb.m08_uf1_p6_retrofit.recycler.PeopleAdapter;
import cat.itb.m08_uf1_p6_retrofit.webservice.WebServiceClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;
    private List<People> peopleList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lanzarPeticion();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void sout(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    private void lanzarPeticion() {
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder().baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();


        WebServiceClient client = retrofit.create(WebServiceClient.class);
        Call<Data> call = client.getPeople();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                peopleList = response.body().getResults();
                recyclerView.setAdapter(new PeopleAdapter(peopleList));

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("TAG1", "ERROR: " + t.getMessage());
                sout("no");
            }
        });
    }
}