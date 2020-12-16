package cat.itb.m08_uf1_p6_retrofit.webservice;

import cat.itb.m08_uf1_p6_retrofit.models.Data;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WebServiceClient {
    @GET("people")
    Call<Data> getPeople();
}
