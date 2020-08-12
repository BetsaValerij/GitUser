package com.sample.gituser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.UserManager;

import com.sample.gituser.adapter.UsersAdapter;
import com.sample.gituser.mvp.fragment.UsersDetailFragment;
import com.sample.gituser.mvp.fragment.UsersFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements UsersFragment.UserClickedCallback {
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUserFragment();
    }
    private void setUpUserFragment(){
        ft = getSupportFragmentManager().beginTransaction();
        UsersFragment usersFragment = new UsersFragment();
        usersFragment.setOnUserClicked(this);
        ft.add(R.id.frag_container, usersFragment, UsersFragment.class.getName())
                .commit();
    }

    @Override
    public void openUserFragment(String user) {
        ft = getSupportFragmentManager().beginTransaction();
        UsersDetailFragment usersDetailFragment = UsersDetailFragment.newInstance(user);
        ft.replace(R.id.frag_container, usersDetailFragment, UsersDetailFragment.class.getName())
                .commit();
    }
}