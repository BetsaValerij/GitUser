package com.sample.gituser.mvp.contract;

import com.sample.gituser.api.Response.ApiRespUsers;
import java.util.List;

public interface UsersFragmentContract {
    interface UsersView{
        void showProgress();
        void hideProgress();
        void setUpList(List<ApiRespUsers> users);
        void showError();
    }
    interface UsersPresenterImpl{

    }
    interface UsersRepositoryImpl {}
}
