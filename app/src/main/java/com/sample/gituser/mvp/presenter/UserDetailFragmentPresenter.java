package com.sample.gituser.mvp.presenter;

import com.sample.gituser.api.Response.ApiRespUserDetail;
import com.sample.gituser.api.Response.ApiRespUserRepo;
import com.sample.gituser.mvp.contract.UserDetailFragmentContract;
import com.sample.gituser.mvp.contract.UsersFragmentContract;
import com.sample.gituser.mvp.repository.UserDetailFragmentRepository;
import com.sample.gituser.mvp.repository.UsersFragmentRepository;

import java.util.List;

public class UserDetailFragmentPresenter implements UserDetailFragmentContract.Presenter {
    private UserDetailFragmentRepository userDetailFragmentRepository;
    private UserDetailFragmentContract.View mView;

    public UserDetailFragmentPresenter(UserDetailFragmentRepository model) {
        this.userDetailFragmentRepository = model;
    }

    public void attachView(UserDetailFragmentContract.View view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
    public void loadUserDetail(String user){
        mView.showProgress();
        userDetailFragmentRepository.loadUserDetail(user, new OnLoadDetailCallback() {
            @Override
            public void onDetailSuccess(ApiRespUserDetail apiRespUserDetail) {
                mView.hideProgress();
                mView.fillUserDetail(apiRespUserDetail);
            }

            @Override
            public void onLoadError() {
                mView.hideProgress();
                mView.showError();
            }
        });
    }
    public void loadUserRepo(String user){
        userDetailFragmentRepository.loadUserRepo(user, new OnLoadRepoCallback() {
            @Override
            public void onLoadRepoSuccess(List<ApiRespUserRepo> apiRespUserRepos) {
                mView.hideProgress();
                mView.fillUserRepo(apiRespUserRepos);
            }

            @Override
            public void onLoadError() {
                mView.hideProgress();
                mView.showError();
            }
        });

    }
    public interface OnLoadDetailCallback{
        void onDetailSuccess(ApiRespUserDetail apiRespUserDetail);
        void onLoadError();
    }
    public interface OnLoadRepoCallback{
        void onLoadRepoSuccess(List<ApiRespUserRepo> apiRespUserRepos);
        void onLoadError();
    }
}
