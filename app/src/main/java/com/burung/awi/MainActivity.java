package com.burung.awi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    private Button btnInitialize;

    private boolean initializeState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        btnInitialize = findViewById(R.id.btn_initialize);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.169/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        btnInitialize.setOnClickListener(view -> {
            initialize();

        });
//        initialize();
//        getComments();
//        createPost();
//        updatePost();
    }
    public void initialize() {
        Call<Initialize> call = jsonPlaceHolderAPI.startInitialize();
        call.enqueue(new Callback<Initialize>() {
            @Override
            public void onResponse(Call<Initialize> call, Response<Initialize> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code : " + response.code());
                    return;
                }

                if (initializeState) {
                    btnInitialize.setText(R.string.initialize);
                } else {
                    btnInitialize.setText(R.string.stop_initialize);
                }
                initializeState = !initializeState;

            }

            @Override
            public void onFailure(Call<Initialize> call, Throwable t) {
                return;
            }
        });
    }
    private void getComments() {
        Call<List<Comment>> call = jsonPlaceHolderAPI.getComments(3);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void createPost() {
        Post post = new Post(3, "New Title", "New Text");
        Call<Post> call = jsonPlaceHolderAPI.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void updatePost() {
        Post post = new Post(12,null, "new Text");
        Call<Post> call = jsonPlaceHolderAPI.patchtPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code : " + response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n\n";
                textViewResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}