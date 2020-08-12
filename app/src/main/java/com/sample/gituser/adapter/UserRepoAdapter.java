package com.sample.gituser.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sample.gituser.R;
import com.sample.gituser.api.Response.ApiRespUserRepo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRepoAdapter extends RecyclerView.Adapter<UserRepoAdapter.RepoVH> implements Filterable {
    private Context ctx;
    private List<ApiRespUserRepo> repoList;
    public List<ApiRespUserRepo> orig;

    public UserRepoAdapter(Context context){
        this.ctx = context;
    }
    @NonNull
    @Override
    public RepoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_detail_list_item, parent, false);

        RepoVH viewHolder = new RepoVH(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoVH holder, int position) {
        final ApiRespUserRepo item = getDetail(position);
        holder.repoName.setText(item.getName() != null? item.getName() : "");
        holder.usrForks.setText(item.getForks() != 0 ? ctx.getString(R.string.forks_title)+" "+String.valueOf(item.getForks()) : ctx.getString(R.string.forks_title));
        holder.usrStars.setText(item.getStargazersCount() != 0 ? ctx.getString(R.string.stars_title) +" "+String.valueOf(item.getStargazersCount()) : ctx.getString(R.string.stars_title));
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    private ApiRespUserRepo getDetail(int position) {
        return (ApiRespUserRepo) repoList.get(position);
    }

    public void setRepoList(List<ApiRespUserRepo> list) {
        this.repoList = list;
    }

    static class RepoVH extends RecyclerView.ViewHolder {
        @BindView(R.id.repo_name)
        TextView repoName;
        @BindView(R.id.usr_forks)
        TextView usrForks;
        @BindView(R.id.usr_stars)
        TextView usrStars;
        RepoVH(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<ApiRespUserRepo> results = new ArrayList<>();
                if (orig == null)
                    orig = repoList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final ApiRespUserRepo repo : orig) {
                            if (repo.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(repo);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                repoList = (List<ApiRespUserRepo>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
