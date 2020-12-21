package cat.itb.m08_uf1_p6_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cat.itb.m08_uf1_p6_retrofit.models.DataPeople;
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

    private RecyclerView recyclerView;
    public static WebServiceClient client;
    private Button buttonPrevious, buttonNext;
    private ImageButton imageButtonSearch;
    private EditText editTextSearch;
    private DataPeople dataPeople;
    private PeopleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        buttonPrevious = findViewById(R.id.buttonPreviousMain);
        buttonNext = findViewById(R.id.buttonNextMain);
        editTextSearch = findViewById(R.id.editTextSearch);
        imageButtonSearch = findViewById(R.id.imageButtonSearch);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();
        client = retrofit.create(WebServiceClient.class);
        updateList("people/?page=1");

        adapter = new PeopleAdapter(new ArrayList<>(), (people, position) -> {
            Intent intent = new Intent(MainActivity.this, ActivityMovie.class);
            intent.putExtra("films", people.getFilms());
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        buttonNext.setOnClickListener(this::nextClicked);
        buttonPrevious.setOnClickListener(this::previousClicked);
        imageButtonSearch.setOnClickListener(this::searchClicked);
    }

    private void searchClicked(View view) {
        updateList("people/?search=" + editTextSearch.getText().toString());
    }

    private void previousClicked(View view) {
        updateList(dataPeople.getPrevious());
    }

    private void nextClicked(View view) {
        updateList(dataPeople.getNext());
    }

    private void sout(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    private void updateList(String url) {
        Call<DataPeople> call = client.getPeople(url);
        call.enqueue(new Callback<DataPeople>() {
            @Override
            public void onResponse(Call<DataPeople> call, Response<DataPeople> response) {
                dataPeople = response.body();

                adapter.resetPeople(dataPeople.getResults());
                if (dataPeople.getNext() == null) {
                    buttonNext.setVisibility(View.INVISIBLE);
                } else {
                    buttonNext.setVisibility(View.VISIBLE);
                }

                if (dataPeople.getPrevious() == null) {
                    buttonPrevious.setVisibility(View.INVISIBLE);
                } else {
                    buttonPrevious.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DataPeople> call, Throwable t) {
                Log.d("TAG1", "ERROR: " + t.getMessage());
            }
        });
    }
}