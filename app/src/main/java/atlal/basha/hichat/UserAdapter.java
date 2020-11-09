package atlal.basha.hichat;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    private ArrayList<NewUser> newUsers;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }



    public static class UserViewHolder extends RecyclerView.ViewHolder{

        public ImageView userImageView;
        public TextView userNameText;
        public TextView userMSGText;

        public UserViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.user_image);
            userNameText = itemView.findViewById(R.id.user_name);
            userMSGText = itemView.findViewById(R.id.user_msg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //int color = Color.parseColor("#242627");
                    //v.setBackgroundColor(color);

                    if (listener != null){
                        if (position != RecyclerView.NO_POSITION){
                            listener.OnItemClicked(position, v);
                        }
                    }
                }
            });

        }
    }


    interface OnItemClickListener{
        void OnItemClicked(int position, View view);
    }


    public UserAdapter (ArrayList<NewUser> newUsers ){
        this.newUsers = newUsers;
    }


    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list, parent, false);
        UserAdapter.UserViewHolder userViewHolder = new UserAdapter.UserViewHolder(view, onItemClickListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        NewUser currentItem = newUsers.get(position);
        holder.userNameText.setText(currentItem.getName());

    }

    @Override
    public int getItemCount() {
        return newUsers.size();

    }

}

