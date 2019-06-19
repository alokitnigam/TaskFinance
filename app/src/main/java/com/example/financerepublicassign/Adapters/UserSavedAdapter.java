package com.example.financerepublicassign.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.financerepublicassign.HomeActivity;
import com.example.financerepublicassign.Model.UserModel;
import com.example.financerepublicassign.R;
import com.example.financerepublicassign.StockDetailsActivity;

import java.util.List;

public class UserSavedAdapter extends RecyclerView.Adapter<UserSavedAdapter.UserviewHolder> {
    List<UserModel> list;
    Context context;
    public UserSavedAdapter(Context context,List<UserModel> list){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public UserviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.useradapter,viewGroup,false);
        return new UserSavedAdapter.UserviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserviewHolder userviewHolder, int i) {
        userviewHolder.name.setText(list.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserviewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public UserviewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, StockDetailsActivity.class)
                            .putExtra("id",(long) list.get(getAdapterPosition()).getId()).putExtra("viewType",true));
                }
            });
        }

    }
}
