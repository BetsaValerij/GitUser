package com.sample.gituser.mvp.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sample.gituser.R;
import com.sample.gituser.adapter.UsersAdapter;
import com.sample.gituser.api.Response.ApiRespUsers;
import com.sample.gituser.mvp.contract.UsersFragmentContract;
import com.sample.gituser.mvp.presenter.UsersFragmentPresenter;
import com.sample.gituser.mvp.repository.UsersFragmentRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UsersFragment extends Fragment implements UsersFragmentContract.UsersView, UsersAdapter.UserClickListener {
    Unbinder mUnbinder;
    UsersAdapter usersAdapter;
    List<ApiRespUsers> usersArrayList = new ArrayList<>();
    @BindView(R.id.edPersonName)
    EditText edPersonName;
    @BindView(R.id.rv_git_user)
    RecyclerView rvGitUser;
    UsersFragmentPresenter usersPresenter;
    @BindView(R.id.pb_load_users)
    ProgressBar pbLoadUsers;
    private UserClickedCallback userClickedCallback;
    public interface UserClickedCallback {
        public void openUserFragment(String user);
        //public void openMainActivity();
    }

    public void setOnUserClicked(UserClickedCallback callback) {
        userClickedCallback = callback;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_list, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        UsersFragmentRepository usrRepository = new UsersFragmentRepository(getActivity());
        usersPresenter = new UsersFragmentPresenter(usrRepository);
        usersPresenter.attachView(this);
        edPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usersAdapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usersAdapter = new UsersAdapter(getContext(), this);

        usersPresenter.loadUsers();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        usersPresenter.detachView();
    }

    @Override
    public void userItemClicked(int position) {
        Log.i("Item Clicked", "");
        userClickedCallback.openUserFragment(usersAdapter.getItem(position).getLogin());
    }

    @Override
    public void showProgress() {
        if (pbLoadUsers.getVisibility() == View.GONE)
            pbLoadUsers.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (pbLoadUsers.getVisibility() == View.VISIBLE)
            pbLoadUsers.setVisibility(View.GONE);
    }

    @Override
    public void setUpList(List<ApiRespUsers> users) {
        usersArrayList = users;
        usersAdapter.setUsersList(usersArrayList);
        rvGitUser.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGitUser.setAdapter(usersAdapter);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "List not loaded", Toast.LENGTH_SHORT);
    }
}
