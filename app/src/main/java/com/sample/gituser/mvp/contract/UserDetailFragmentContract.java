package com.sample.gituser.mvp.contract;

import com.sample.gituser.api.Response.ApiRespUserDetail;
import com.sample.gituser.api.Response.ApiRespUserRepo;

import java.util.List;

public interface UserDetailFragmentContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showError();
        void fillUserDetail(ApiRespUserDetail userDetail);
        void fillUserRepo(List<ApiRespUserRepo> repoList);
    }
    interface Presenter {}
    interface Repository {}
}
