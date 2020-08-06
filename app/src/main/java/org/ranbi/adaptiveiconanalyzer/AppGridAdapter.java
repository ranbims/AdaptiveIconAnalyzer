package org.ranbi.adaptiveiconanalyzer;

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

    public void setData(List<AppEntry> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IconViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_icon_text, null));
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        AppEntry entry = data.get(position);
        holder.textView.setText(entry.getLabel());
        holder.imageView.setImageDrawable(entry.getIcon());
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
    public static class IconViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ImageView imageView;
        public IconViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.text);
            imageView = v.findViewById(R.id.icon);
        }
    }
}
