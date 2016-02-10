package vn.hoangphan.karaokearena.transitions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Interpolator;

import com.marvinlabs.widget.slideshow.SlideShowView;
import com.marvinlabs.widget.slideshow.transition.BaseTransitionFactory;

/**
 * Created by Hoang Phan on 2/10/2016.
 */
public class CustomTransitionFactory extends BaseTransitionFactory {
    public CustomTransitionFactory() {
        super();
    }

    public CustomTransitionFactory(long duration) {
        super(duration);
    }

    public CustomTransitionFactory(long duration, Interpolator interpolator) {
        super(duration, interpolator);
    }

    @Override
    public Animator getInAnimator(View target, SlideShowView parent, int fromSlide, int toSlide) {
        target.setAlpha(0);
        target.setScaleX(1);
        target.setScaleY(1);
        target.setTranslationX(0);
        target.setTranslationY(0);
        target.setRotationX(0);
        target.setRotationY(0);

        ObjectAnimator animator = ObjectAnimator.ofFloat(target, View.ALPHA, 1);
        animator.setDuration(getDuration());
        animator.setInterpolator(getInterpolator());
        return animator;
    }

    @Override
    public Animator getOutAnimator(View target, SlideShowView parent, int fromSlide, int toSlide) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, View.ALPHA, 0);
        animator.setDuration(getDuration() * 2);
        animator.setInterpolator(getInterpolator());
        return animator;
    }
}
