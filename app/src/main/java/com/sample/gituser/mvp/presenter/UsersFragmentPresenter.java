package com.sample.gituser.mvp.presenter;

import com.sample.gituser.api.Response.ApiRespUsers;
import com.sample.gituser.mvp.contract.UsersFragmentContract;
import com.sample.gituser.mvp.repository.UsersFragmentRepository;

import java.util.List;

public class UsersFragmentPresenter implements UsersFragmentContract.UsersPresenterImpl {
    private UsersFragmentContract.UsersView mView;
    private UsersFragmentRepository usrRepository;

    public UsersFragmentPresenter(UsersFragmentRepository model) {
        this.usrRepository = model;
    }

    public void attachView(UsersFragmentContract.UsersView view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
    public void loadUsers(){
        mView.showProgress();
        usrRepository.loadUsers(new LoadUserCallback() {
            @Override
            public void onLoadSuccess(List<ApiRespUsers> users) {
                mView.hideProgress();
                mView.setUpList(users);
            }

            @Override
            public void onLoadError() {
                mView.hideProgress();
                mView.showError();
            }
        });
    }
    public interface LoadUserCallback{
        void onLoadSuccess(List<ApiRespUsers> users);
        void onLoadError();
    }
}
