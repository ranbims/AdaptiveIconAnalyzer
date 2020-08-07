package org.ranbi.adaptiveiconanalyzer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppGridAdapter extends RecyclerView.Adapter<AppGridAdapter.IconViewHolder> {

    List<AppEntry> data;
    ItemClickListener itemClickListener = (viewHolder, position) -> {
        AppEntry entry = data.get(position);
        Context context = viewHolder.itemView.getContext();
        Intent intent = new Intent(context, IconDetailsActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_APP_INDEX, position);
        intent.putExtra(Constants.INTENT_EXTRA_APP_INFO, entry.getApplicationInfo());
        context.startActivity(intent);
    };

    public void setData(List<AppEntry> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IconViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_icon_text, null), itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        AppEntry entry = data.get(position);
        holder.textView.setText(entry.getLabel());
        holder.imageView.setImageDrawable(entry.getIcon());
        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class IconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;

        private ItemClickListener itemClickListener;

        public IconViewHolder(View v, ItemClickListener itemClickListener) {
            super(v);
            this.itemClickListener = itemClickListener;
            textView = v.findViewById(R.id.text);
            imageView = v.findViewById(R.id.icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(this, getAdapterPosition());
        }
    }

    private interface ItemClickListener {
        void onClick(IconViewHolder viewHolder, int position);
    }
}
