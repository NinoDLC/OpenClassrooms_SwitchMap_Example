package fr.delcey.switchmap.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import fr.delcey.switchmap.R;
import fr.delcey.switchmap.data.user.UserEntity;

public class UserAdapter extends ListAdapter<UserEntity, UserAdapter.UserViewHolder> {
    @NonNull
    private final OnUserClickedListener listener;

    public UserAdapter(@NonNull OnUserClickedListener listener) {
        super(new UserDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.user_item_tv_name);
        }

        public void bind(@NonNull UserEntity userEntity, @NonNull OnUserClickedListener listener) {
            nameTextView.setText(userEntity.getUserName());
            nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onUserClicked(userEntity.getId());
                }
            });
        }
    }

    public static class UserDiffCallback extends DiffUtil.ItemCallback<UserEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserEntity oldItem, @NonNull UserEntity newItem) {
            return oldItem.equals(newItem);
        }
    }

    public interface OnUserClickedListener {
        void onUserClicked(long userId);
    }
}
