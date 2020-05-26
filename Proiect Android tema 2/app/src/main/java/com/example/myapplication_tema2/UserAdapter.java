package com.example.myapplication_tema2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    List<User> userList;

    public UserAdapter(List <User> list){

        userList = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView userView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userView = itemView.findViewById(R.id.user_view);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_row_layout, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        holder.itemView.setTag(userList.get(position));
        holder.userView.setText(userList.get(position).getFullName());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
