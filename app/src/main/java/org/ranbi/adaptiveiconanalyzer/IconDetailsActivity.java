package org.ranbi.adaptiveiconanalyzer;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IconDetailsActivity extends AppCompatActivity {

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
            }
            mRawIcon.setImageDrawable(mIconDrawable);
        }
    }
}
