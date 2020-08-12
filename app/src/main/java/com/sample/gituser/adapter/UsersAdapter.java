package com.sample.gituser.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.gituser.R;
import com.sample.gituser.api.Response.ApiRespUsers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersVH> implements Filterable {

    List<ApiRespUsers> usersArrayList = new ArrayList<>();
    public List<ApiRespUsers> orig;
    private Context ctx;
    private static UserClickListener itemListener;


    public interface UserClickListener {
        void userItemClicked(int position);
    }

    public UsersAdapter(Context context, UserClickListener listener) {
        this.ctx = context;
        this.itemListener = listener;
    }

    @NonNull
    @Override
    public UsersVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_list_item, parent, false);

        UsersVH viewHolder = new UsersVH(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersVH holder, int position) {
        final ApiRespUsers item = getUser(position);
        holder.usrName.setText(item.getLogin() != null ? item.getLogin() : "");
        Picasso.with(ctx)
                .load(item.getAvatarUrl())
                .into(holder.usrImg);
    }

    private ApiRespUsers getUser(int position) {
        return (ApiRespUsers) usersArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    public ApiRespUsers getItem(int position) {
        return usersArrayList.get(position);
    }
    public void setUsersList(List<ApiRespUsers> list) {
        this.usersArrayList = list;
    }

    static class UsersVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.usr_img)
        ImageView usrImg;
        @BindView(R.id.usr_name)
        TextView usrName;
        @BindView(R.id.usr_repo)
        TextView usrRepo;
        UsersVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("Item Clicked", "");
            itemListener.userItemClicked(getAdapterPosition());
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<ApiRespUsers> results = new ArrayList<>();
                if (orig == null)
                    orig = usersArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final ApiRespUsers user : orig) {
                            if (user.getLogin().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(user);
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
                usersArrayList = (List<ApiRespUsers>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
