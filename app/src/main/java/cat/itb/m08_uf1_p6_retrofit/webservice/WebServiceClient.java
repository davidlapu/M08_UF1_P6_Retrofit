package cat.itb.m08_uf1_p6_retrofit.webservice;

import cat.itb.m08_uf1_p6_retrofit.models.DataPeople;
import cat.itb.m08_uf1_p6_retrofit.models.Film;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WebServiceClient {
    @GET("people")
    Call<DataPeople> getPeople();

    @GET()
    Call<DataPeople> getPeople(@Url String url);

    @GET()
    Call<Film> getFilm(@Url String url);
}
