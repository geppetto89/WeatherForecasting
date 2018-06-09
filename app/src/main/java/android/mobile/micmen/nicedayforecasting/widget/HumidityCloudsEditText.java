package android.mobile.micmen.nicedayforecasting.widget;

import android.content.Context;
import android.graphics.Rect;
import android.mobile.micmen.nicedayforecasting.R;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class HumidityCloudsEditText extends AppCompatEditText implements TextWatcher {

    final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.wiggle_animation);

    private boolean isInError = true;

    public boolean isInError() {
        return isInError;
    }

    public void setInError(boolean inError) {
        isInError = inError;
    }

    public HumidityCloudsEditText(Context context) {
        super(context);
    }

    public HumidityCloudsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HumidityCloudsEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            try {

                int i = Integer.parseInt(s.toString());
                if (i < 0 || i > 100) {
                    isInError = true;
                } else {
                    isInError = false;
                }
            } catch (Exception e) {
                isInError = true;
            }
        } else {
            isInError = true;
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (!focused && isInError && getText().toString().length() > 0) {
            Snackbar snack = Snackbar.make(this, "the range must be between 0 and 100%", Snackbar.LENGTH_LONG);
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.CENTER;
            view.setLayoutParams(params);
            snack.show();
            startAnimation(animShake);
            setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corner_background_error_weather_editext));
        } else {
            setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corner_background_weather_edittext));
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }


    @Override
    public void afterTextChanged(Editable s) {

    }
}
