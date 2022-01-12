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
import fr.delcey.switchmap.data.pet.PetEntity;
import fr.delcey.switchmap.data.user.UserEntity;

public class PetAdapter extends ListAdapter<PetEntity, PetAdapter.PetViewHolder> {

    public PetAdapter() {
        super(new PetDiffCallback());
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.pet_item_tv_name);
        }

        public void bind(@NonNull PetEntity petEntity) {
            nameTextView.setText(petEntity.getPetName());
        }
    }

    public static class PetDiffCallback extends DiffUtil.ItemCallback<PetEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull PetEntity oldItem, @NonNull PetEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull PetEntity oldItem, @NonNull PetEntity newItem) {
            return oldItem.equals(newItem);
        }
    }
}
