package com.example.news;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{

    ArrayList<News> news = new ArrayList<News>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date date = new Date();
        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Request request = new Request.Builder()
                .url("https://newsapi.org/v2/everything?q=Россия&from="+strDate+"&sortBy=popularity&apiKey=f10aabd510984d7591d01605a99fbc5f")
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {

                assert response.body() != null;
                final String responseData = response.body().string();


                MainActivity.this.runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        JSONObject Jobject = null;
                        JSONArray Jarray = null;
                        try {
                            Jobject = new JSONObject(responseData);
                            Jarray = Jobject.getJSONArray("articles");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < Jarray.length(); i++) {
                            try {
                                JSONObject object = Jarray.getJSONObject(i);
                                String source = object.getJSONObject("source").getString("name");
                                String author = object.getString("author");
                                if (author.equals("null")){
                                    author = "";
                                }
                                String title = object.getString("title");
                                String description = object.getString("description");
                                String date = object.getString("publishedAt").substring(0, 10);
                                String imageUrl = object.getString("urlToImage");
                                news.add(new News(source, author, title, description, date, imageUrl));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        RecyclerView recyclerView = findViewById(R.id.list);

                        NewsAdapter adapter = new NewsAdapter(MainActivity.this, news);
                        recyclerView.setAdapter(adapter);
                    }

                });
            }
        });

    }

}

