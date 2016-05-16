package android.courses.realmretrofit.activities;

import android.app.ProgressDialog;
import android.courses.realmretrofit.R;
import android.courses.realmretrofit.adapters.ContentAdapter;
import android.courses.realmretrofit.models.Content;
import android.courses.realmretrofit.rest.RestClient;
import android.courses.realmretrofit.rest.models.ContentResponse;
import android.courses.realmretrofit.rest.services.appAPI;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private RecyclerView recyclerView;
    private TextView mTvTitle;
    private ArrayList<Content> mContentsArray = new ArrayList<>();
    private ContentAdapter adapter;

    private Realm realm;
    private appAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progress = new ProgressDialog(this);
        progress.setMessage("Loading data...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        Fresco.initialize(this);

        // Init Realm
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new ContentAdapter(mContentsArray);
        recyclerView.setAdapter(adapter);

        api = RestClient.getApi();
        Call<ContentResponse> call = api.loadContents();

        call.enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {
                mContentsArray.clear();

                if(response.isSuccessful()) {
                    progress.dismiss();
                    // Set title
                    String title = response.body().getTitle() + " " + response.body().getType() +
                            " - " + response.body().getVersion();
                    mTvTitle.setText(title);
                    mContentsArray.addAll(response.body().getContents());

                    // Add content to the realm DB
                    // Open a transaction to store items into the realm
                    // Use copyToRealm() to convert the objects into proper RealmObjects managed by Realm.
                    realm.beginTransaction();
                    realm.copyToRealm(mContentsArray);
                    realm.commitTransaction();

                    Toast.makeText(MainActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
                } else {
                    progress.dismiss();
                    Toast.makeText(MainActivity.this, getString(R.string.error) + " CODE: " +response.code(), Toast.LENGTH_LONG).show();
                    RealmResults<Content> results = realm.where(Content.class).findAll();
                    mContentsArray.addAll(results);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.failed), Toast.LENGTH_LONG).show();
                RealmResults<Content> results = realm.where(Content.class).findAll();
                mContentsArray.addAll(results);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
