package org.ranbi.adaptiveiconanalyzer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class IconWithBgPreviewDialogFragment extends DialogFragment {

    private @ColorInt int mBgColor;
    private Drawable mIconDrawable;

    public void setParameters(@ColorInt int bgColor, Drawable imageDrawable) {
        mBgColor = bgColor;
        mIconDrawable = imageDrawable;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.icon_with_bg_preview, null);
        view.findViewById(R.id.preview_bg).setBackgroundColor(mBgColor);
        ((ImageView) view.findViewById(R.id.preview_icon)).setImageDrawable(mIconDrawable);
        return view;
    }
}
