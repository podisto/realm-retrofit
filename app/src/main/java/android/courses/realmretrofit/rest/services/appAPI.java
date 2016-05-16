package android.courses.realmretrofit.rest.services;

import android.courses.realmretrofit.rest.models.ContentResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Podisto on 15/05/2016.
 */
public interface appAPI {

    public static final String ENDPOINT = "http://test-project-json.appspot.com";

    @GET("/")
    Call<ContentResponse> loadContents();
}
