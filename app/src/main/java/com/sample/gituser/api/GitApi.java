package com.sample.gituser.api;
import com.sample.gituser.api.Response.ApiRespUserDetail;
import com.sample.gituser.api.Response.ApiRespUserRepo;
import com.sample.gituser.api.Response.ApiRespUsers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface GitApi {
    @GET("/users")
    public Call<List<ApiRespUsers>> getUsers();
    @GET("/users/{username}")
    public Call<ApiRespUserDetail> getUserDetail(@Path("username") String username);
    @GET("/users/{username}/repos")
    public Call<List<ApiRespUserRepo>> getUserRepos(@Path("username") String username);

}
