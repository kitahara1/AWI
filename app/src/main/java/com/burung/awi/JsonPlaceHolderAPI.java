package com.burung.awi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {
    @GET("initialize-system")
    Call<Initialize> startInitialize();
    @GET("/posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);
    @PATCH("posts/{id}")
    Call<Post> patchtPost(@Path("id") int id, @Body Post post);

}
