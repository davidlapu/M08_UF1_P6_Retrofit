package cat.itb.m08_uf1_p6_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cat.itb.m08_uf1_p6_retrofit.models.Film;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMovie extends AppCompatActivity {

    private TextView textViewTitle, textViewSummary;
    private Button buttonNext, buttonPrevious;
    private int index = 0;
    private final List<Film> films = new ArrayList<>();
    boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewSummary = findViewById(R.id.textViewSummary);
        buttonNext = findViewById(R.id.buttonNextMovie);
        buttonPrevious = findViewById(R.id.buttonPreviousMovie);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            loadMovies(bundle.getStringArray("films"));
        }

        buttonNext.setOnClickListener(this::nextClicked);
        buttonPrevious.setOnClickListener(this::previousClicked);
    }

    private void previousClicked(View view) {
        try {
            loadFilm(index - 1);
        } catch (FilmOutOfBonds ignored) {}
    }

    private void nextClicked(View view) {
        try {
            loadFilm(index + 1);
        } catch (FilmOutOfBonds ignored) {}

    }

    private void loadMovies(String[] movies) {
        for (String m :
                movies) {
            getFilmFromApi(m);
        }

    }

    private void loadFilm(int index) throws FilmOutOfBonds {
        Film f;

        if (index == films.size() || index < 0) {
            throw new FilmOutOfBonds();
        }

        f = films.get(index);
        this.index = index;

        textViewTitle.setText(f.getTitle());
        textViewSummary.setText(f.getOpeningCrawl());

        if (films.size() - 1 == index) {
            buttonNext.setVisibility(View.INVISIBLE);
        } else {
            buttonNext.setVisibility(View.VISIBLE);
        }

        if (index == 0) {
            buttonPrevious.setVisibility(View.INVISIBLE);
        } else {
            buttonPrevious.setVisibility(View.VISIBLE);
        }

    }

    private void getFilmFromApi(String url) {
        Call<Film> call = MainActivity.client.getFilm(url);
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                films.add(response.body());
                if (firstTime) {
                    try {
                        loadFilm(0);
                    } catch (FilmOutOfBonds ignored) {}
                    firstTime = false;
                }
                if (films.size() - 1 != index) {
                    buttonNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.d("TAG1", "ERROR: " + t.getMessage());
            }
        });
    }

    private class FilmOutOfBonds extends Exception {
    }
}
