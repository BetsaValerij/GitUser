package com.sample.gituser.mvp.repository;

import android.content.Context;

import com.sample.gituser.api.GitApi;
import com.sample.gituser.api.GitNetworkService;
import com.sample.gituser.api.Response.ApiRespUserDetail;
import com.sample.gituser.api.Response.ApiRespUserRepo;
import com.sample.gituser.mvp.contract.UserDetailFragmentContract;
import com.sample.gituser.mvp.presenter.UserDetailFragmentPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailFragmentRepository implements UserDetailFragmentContract.Repository {
    private Context ctx;
    public UserDetailFragmentRepository(Context context){
        this.ctx = context;
    }
    public void loadUserDetail(String user, UserDetailFragmentPresenter.OnLoadDetailCallback callback){
        GitNetworkService.getInstance(ctx)
                .getGitApi()
                .getUserDetail(user)
                .enqueue(new Callback<ApiRespUserDetail>() {
                    @Override
                    public void onResponse(Call<ApiRespUserDetail> call, Response<ApiRespUserDetail> response) {
                        if (response.code() == 200){
                            ApiRespUserDetail apiRespUserDetail = response.body();
                            callback.onDetailSuccess(apiRespUserDetail);
                        }
                            //ApiRespUserDetail apiRespUserDetail = response.body();
                    }

                    @Override
                    public void onFailure(Call<ApiRespUserDetail> call, Throwable t) {
                        System.out.println(t.getMessage());
                        callback.onLoadError();
                    }
                });
    }
    public void loadUserRepo(String user, UserDetailFragmentPresenter.OnLoadRepoCallback callback){
        GitNetworkService.getInstance(ctx)
                .getGitApi()
                .getUserRepos(user)
                .enqueue(new Callback<List<ApiRespUserRepo>>() {
                    @Override
                    public void onResponse(Call<List<ApiRespUserRepo>> call, Response<List<ApiRespUserRepo>> response) {
                        if (response.code() == 200){
                            callback.onLoadRepoSuccess(response.body());
                        }
                        //ApiRespUserDetail apiRespUserDetail = response.body();
                    }

                    @Override
                    public void onFailure(Call<List<ApiRespUserRepo>> call, Throwable t) {
                        System.out.println(t.getMessage());
                        callback.onLoadError();
                    }
                });
    }
}
