package android.courses.realmretrofit.rest;

import android.courses.realmretrofit.rest.services.appAPI;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Podisto on 15/05/2016.
 */
public class RestClient {

    private static appAPI api;

    public static appAPI getApi() {

        if(api == null) {

            // create a converter compatible with Realm
            // GSON can parse the data.
            // Note there is a bug in GSON 2.5 that can cause it to StackOverflow when working with RealmObjects.
            // To work around this, use the ExclusionStrategy below or downgrade to 1.7.1
            // See more here: https://code.google.com/p/google-gson/issues/detail?id=440
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(appAPI.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            // prepare call in Retrofit
            api = retrofit.create(appAPI.class);
        }

        return api;
    }
}
