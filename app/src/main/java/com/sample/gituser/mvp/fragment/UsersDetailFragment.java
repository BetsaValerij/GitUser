package com.sample.gituser.mvp.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.gituser.R;
import com.sample.gituser.adapter.UserRepoAdapter;
import com.sample.gituser.api.Response.ApiRespUserDetail;
import com.sample.gituser.api.Response.ApiRespUserRepo;
import com.sample.gituser.mvp.contract.UserDetailFragmentContract;
import com.sample.gituser.mvp.presenter.UserDetailFragmentPresenter;
import com.sample.gituser.mvp.repository.UserDetailFragmentRepository;
import com.squareup.picasso.Picasso;

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

public class UsersDetailFragment extends Fragment implements UserDetailFragmentContract.View {
    private static String ARG_USER = "user";
    private static String mUser;
    @BindView(R.id.im_user)
    ImageView imUser;
    @BindView(R.id.usr_name)
    TextView usrName;
    @BindView(R.id.usr_email)
    TextView usrEmail;
    @BindView(R.id.usr_location)
    TextView usrLocation;
    @BindView(R.id.usr_joid_date)
    TextView usrJoidDate;
    @BindView(R.id.usr_followers)
    TextView usrFollowers;
    @BindView(R.id.usr_following)
    TextView usrFollowing;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.usr_biography)
    TextView usrBiography;
    @BindView(R.id.ed_usr_repo)
    EditText edUsrRepo;
    @BindView(R.id.rv_user_repo)
    RecyclerView rvUserRepo;
    @BindView(R.id.pb_load_detail)
    ProgressBar pbLoadDetail;
    private Unbinder mUnbinder;
    UserDetailFragmentPresenter detailFragmentPresenter;
    private List<ApiRespUserRepo> usersRepoList = new ArrayList<>();
    private UserRepoAdapter userRepoAdapter;


    public static UsersDetailFragment newInstance(String user) {
        UsersDetailFragment fragment = new UsersDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER, user);
        fragment.setArguments(args);
        fragment.mUser = user;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        edUsrRepo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userRepoAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        UserDetailFragmentRepository detailFragmentRepository = new UserDetailFragmentRepository(getContext());
        detailFragmentPresenter = new UserDetailFragmentPresenter(detailFragmentRepository);
        detailFragmentPresenter.attachView(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailFragmentPresenter.loadUserDetail(mUser);
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
        detailFragmentPresenter.detachView();
    }



    @Override
    public void showProgress() {
        if (pbLoadDetail.getVisibility() != View.VISIBLE)
            pbLoadDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (pbLoadDetail.getVisibility() == View.VISIBLE)
            pbLoadDetail.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), getString(R.string.error_load_user_detail), Toast.LENGTH_SHORT);
    }

    @Override
    public void fillUserDetail(ApiRespUserDetail userDetail) {
        Picasso.with(getContext())
                .load(userDetail.getAvatarUrl())
                .into(imUser);

        if (!TextUtils.isEmpty(userDetail.getName()))
            usrName.setText(usrName.getText() + " " + userDetail.getName());
        if (!TextUtils.isEmpty(userDetail.getEmail()))
            usrEmail.setText(usrEmail.getText()+" "+userDetail.getEmail());
        if (!TextUtils.isEmpty(userDetail.getLocation()))
            usrLocation.setText(usrLocation.getText()+" "+userDetail.getLocation());
        if (userDetail.getFollowers() > 0)
            usrFollowers.setText(usrFollowers.getText()+" "+String.valueOf(userDetail.getFollowers()));
        if (userDetail.getFollowing()>0)
            usrFollowing.setText(usrFollowing.getText()+" "+String.valueOf(userDetail.getFollowing()));
        detailFragmentPresenter.loadUserRepo(userDetail.getLogin());

    }

    @Override
    public void fillUserRepo(List<ApiRespUserRepo> repoList) {
        usersRepoList = repoList;
        userRepoAdapter = new UserRepoAdapter(getContext());
        userRepoAdapter.setRepoList(usersRepoList);
        rvUserRepo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUserRepo.setAdapter(userRepoAdapter);
    }
}
