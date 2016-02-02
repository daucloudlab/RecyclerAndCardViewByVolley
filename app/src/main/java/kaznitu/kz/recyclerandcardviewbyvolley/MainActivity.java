package kaznitu.kz.recyclerandcardviewbyvolley;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kaznitu.kz.recyclerandcardviewbyvolley.adapters.RVAdapter;
import kaznitu.kz.recyclerandcardviewbyvolley.apps.AppController;
import kaznitu.kz.recyclerandcardviewbyvolley.models.Movie;

public class MainActivity extends AppCompatActivity {
    private static final String url = "http://api.androidhive.info/json/movies.json";
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();

    private RecyclerView recyclerView ;
    private RVAdapter rvAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.rv) ;

        pDialog = new ProgressDialog(MainActivity.this) ;
        pDialog.setMessage("Күтеміз...");
        pDialog.setCancelable(true);

        showDialog();

//        initData();

        rvAdapter = new RVAdapter(movieList) ;
        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this) ;
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(rvAdapter);

        Log.d("MOVIELIST2", movieList.toString()) ;

        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pDialog.dismiss();
        pDialog = null ;
    }

    public void showDialog(){
        if(!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog(){
        if(pDialog.isShowing())
            pDialog.hide();
    }

    public void initData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString()) ;
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating(obj.getDouble("rating")) ;
                                movie.setYear(obj.getInt("releaseYear"));

                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);

                                // adding movie to movies array
                                movieList.add(movie);
                                Log.d("MOVIELIST1", movieList.toString()) ;
                                hideDialog();
                                rvAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse (VolleyError error){
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            hideDialog();
                    }

            }) ;
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);


    }
}
