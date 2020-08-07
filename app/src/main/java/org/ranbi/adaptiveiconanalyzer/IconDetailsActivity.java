package org.ranbi.adaptiveiconanalyzer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

public class IconDetailsActivity extends AppCompatActivity {

    private static final int BITMAP_SIZE = 48;

    private ImageView mRawIcon;
    private ImageView mForeground;
    private ImageView mBackground;

    private ApplicationInfo mApplicationInfo;
    private Drawable mIconDrawable;
    private Drawable mForegroundDrawable;
    private Drawable mBackgroundDrawable;
    private TextView mNonAdaptiveWarning;

    private ViewGroup mForegroundDisplayArea;
    private ViewGroup mBackgroundDisplayArea;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_detail_layout);
        mRawIcon = findViewById(R.id.raw_icon);
        mForegroundDisplayArea = findViewById(R.id.foreground_display_area);
        mBackgroundDisplayArea = findViewById(R.id.background_display_area);
        mForeground = findViewById(R.id.fore_ground);
        mBackground = findViewById(R.id.background);
        mNonAdaptiveWarning = findViewById(R.id.non_adaptive_warning);

        Intent intent = getIntent();
        if (intent != null) {
            mApplicationInfo = intent.getParcelableExtra(Constants.INTENT_EXTRA_APP_INFO);
            mIconDrawable = mApplicationInfo.loadIcon(getPackageManager()).mutate();
            if (mIconDrawable instanceof AdaptiveIconDrawable) {
                // load separately because they will affect each other
                mForegroundDrawable = ((AdaptiveIconDrawable) mApplicationInfo.loadIcon(
                        getPackageManager())).getForeground().mutate();
                mBackgroundDrawable = ((AdaptiveIconDrawable) mApplicationInfo.loadIcon(
                        getPackageManager())).getBackground().mutate();
                mForeground.setImageDrawable(mForegroundDrawable);
                mBackground.setImageDrawable(mBackgroundDrawable);
            } else {
                mForegroundDisplayArea.setVisibility(View.GONE);
                mBackgroundDisplayArea.setVisibility(View.GONE);
                mNonAdaptiveWarning.setVisibility(View.VISIBLE);

                // regard both foreground and background as the raw icon
                mForegroundDrawable = mIconDrawable;
                mBackgroundDrawable = mIconDrawable;
            }
            mRawIcon.setImageDrawable(mIconDrawable);
        }

//        int bitmapSize = dip2px(this, BITMAP_SIZE);
        int bitmapSize = 120;

        Bitmap paletteBackgroundBitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas backgroundCanvas = new Canvas(paletteBackgroundBitmap);
        Rect bgBounds = mBackgroundDrawable.getBounds();
        mBackgroundDrawable.setBounds(0, 0, bitmapSize, bitmapSize);
        mBackgroundDrawable.draw(backgroundCanvas);
        mBackgroundDrawable.setBounds(bgBounds);

        Bitmap paletteForegroundBitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas foregroundCanvas = new Canvas(paletteForegroundBitmap);
        Rect fgBounds = mForegroundDrawable.getBounds();
        mForegroundDrawable.setBounds(0, 0, bitmapSize, bitmapSize);
        mForegroundDrawable.draw(foregroundCanvas);
        mForegroundDrawable.setBounds(fgBounds);

        Bitmap paletteRawBitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888);
        Canvas rawCanvas = new Canvas(paletteRawBitmap);
        Rect rawBounds = mIconDrawable.getBounds();
        mIconDrawable.setBounds(0, 0, bitmapSize, bitmapSize);
        mIconDrawable.draw(rawCanvas);
        mIconDrawable.setBounds(rawBounds);

        initializeColorContents((LinearLayout) findViewById(R.id.icon_background_color_list), paletteBackgroundBitmap);
        initializeColorContents((LinearLayout) findViewById(R.id.icon_foreground_color_list), paletteForegroundBitmap);
        initializeColorContents((LinearLayout) findViewById(R.id.icon_raw_icon_color_list), paletteRawBitmap);
    }

    private void initializeColorContents(LinearLayout linearLayout, Bitmap bitmap) {
        Palette.Builder builder = Palette.from(bitmap);
        Palette palette = builder.generate();
        linearLayout.addView(initializeColorInfo("Dominent:", palette.getDominantColor(Color.WHITE)));
        linearLayout.addView(initializeColorInfo("Light muted:", palette.getLightMutedColor(Color.WHITE)));
        linearLayout.addView(initializeColorInfo("Light vibrant:", palette.getLightVibrantColor(Color.WHITE)));
    }

    private View initializeColorInfo(String title, int color) {
        View view = LayoutInflater.from(this).inflate(R.layout.color_info, null);
        ((TextView) view.findViewById(R.id.color_title)).setText(title);
        ((TextView) view.findViewById(R.id.color_value)).setText("#" + Integer.toHexString(color));
        ImageView imageView = view.findViewById(R.id.color_view);
        imageView.setImageDrawable(new ColorDrawable(color));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        view.setLayoutParams(layoutParams);
        return view;
    }

    public static int dip2px(Context context, float dpValue) {
        return (int) (dpValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
