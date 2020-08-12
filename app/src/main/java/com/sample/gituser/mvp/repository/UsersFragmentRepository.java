package com.sample.gituser.mvp.repository;

import android.content.Context;

import com.sample.gituser.api.GitNetworkService;
import com.sample.gituser.api.Response.ApiRespUsers;
import com.sample.gituser.mvp.contract.UsersFragmentContract;
import com.sample.gituser.mvp.presenter.UsersFragmentPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragmentRepository implements UsersFragmentContract.UsersRepositoryImpl {
    private Context ctx;
    public UsersFragmentRepository(Context context){
        this.ctx = context;
    }
    public void loadUsers(UsersFragmentPresenter.LoadUserCallback callback){
        GitNetworkService.getInstance(ctx)
                .getGitApi()
                .getUsers()
                .enqueue(new Callback<List<ApiRespUsers>>() {
                    @Override
                    public void onResponse(Call<List<ApiRespUsers>> call, Response<List<ApiRespUsers>> response) {
                        if (response.isSuccessful() || response.code() == 200){
                            callback.onLoadSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ApiRespUsers>> call, Throwable t) {
                        System.out.println(t.getMessage());
                        callback.onLoadError();
                    }
                });
    }
}
