package android.mobile.micmen.nicedayforecasting.widget;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.mobile.micmen.nicedayforecasting.R;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class HomeMoodCardView extends CardView{

    private ImageView imageView;
    private AnimatorSet set;

    public HomeMoodCardView(@NonNull Context context) {
        super(context);
        init();
    }

    public HomeMoodCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeMoodCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.rotation_fetching);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = findViewById(R.id.image);
    }

    public void setLoadingState() {
        if(set!=null) {
            set.removeAllListeners();
            set.end();
            set.cancel();
            invalidate();
            requestLayout();
        }
        set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.rotation_fetching);
        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.unknown));
        set.setTarget(imageView);
        set.start();
    }

    public void setNormalState() {
        set.removeAllListeners();
        set.end();
        set.cancel();
        invalidate();
        requestLayout();
        set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.rotation_fecthed);
        set.setTarget(imageView);
        set.start();
    }

}
