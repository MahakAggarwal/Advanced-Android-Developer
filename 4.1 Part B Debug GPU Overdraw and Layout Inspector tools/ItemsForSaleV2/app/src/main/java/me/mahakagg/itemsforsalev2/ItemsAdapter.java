package me.mahakagg.itemsforsalev2;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private ArrayList<Items> itemsArrayList;
    private Context context;

    ItemsAdapter(Context context, ArrayList<Items> itemsArrayList) {
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        Items currentItem = itemsArrayList.get(position);
        holder.bindTo(currentItem);

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mItemNameTextView;
        private TextView mItemPriceTextView;
        private ImageView imageThumbnail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // find views by ID, set onClick listener
            mItemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            mItemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            imageThumbnail = itemView.findViewById(R.id.itemThumbnail);
            imageThumbnail.setOnClickListener(this);
        }

        void bindTo(Items currentItem){
            // load items' names, thumbnails and prices
            mItemNameTextView.setText(currentItem.getItemName());
            mItemPriceTextView.setText(context.getResources().getString(R.string.price_format, currentItem.getItemPrice()));
            Glide.with(context).load(currentItem.getImageThumbnail()).into(imageThumbnail);
        }

        @Override
        public void onClick(View v) {
            // dialog box setup
            Items currentItem = itemsArrayList.get(getAdapterPosition());
            final Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Light_DialogWhenLarge);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // make dialog use custom layout
            dialog.setContentView(R.layout.dialog_layout);
            ImageView largeImage = dialog.findViewById(R.id.largeImageView);
            largeImage.setImageResource(currentItem.getImageLarge());
            Button button = dialog.findViewById(R.id.okButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
