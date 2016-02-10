package vn.hoangphan.karaokearena.playlists;

import com.marvinlabs.widget.slideshow.PlayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hoang Phan on 2/10/2016.
 */
public class CustomRandomPlaylist implements PlayList {
    public static final long DEFAULT_SLIDE_DURATION = 10000;

    private Integer[] slideOrder = null;

    private int currentOrderIndex = -1;

    private boolean isLooping = true;

    private boolean isAutoAdvanceEnabled = true;

    private long slideDuration = DEFAULT_SLIDE_DURATION;

    public CustomRandomPlaylist() {
    }

    @Override
    public int getFirstSlide() {
        return getSlideCount() > 0 ? slideOrder[0] : -1;
    }

    @Override
    public int getCurrentSlide() {
        return getSlideCount() > 0 && currentOrderIndex >= 0 ? slideOrder[currentOrderIndex] : -1;
    }

    @Override
    public int getNextSlide() {
        if (currentOrderIndex < getSlideCount() - 1) return slideOrder[currentOrderIndex + 1];
        else if (isLooping) return slideOrder[0];
        else return -1;
    }

    @Override
    public int getPreviousSlide() {
        if (currentOrderIndex > 0) return slideOrder[currentOrderIndex - 1];
        else if (isLooping) return slideOrder[getSlideCount() - 1];
        else return -1;
    }

    @Override
    public void rewind() {
        currentOrderIndex = -1;
    }

    @Override
    public int next() {
        if (currentOrderIndex < getSlideCount() - 1) {
            currentOrderIndex = currentOrderIndex + 1;
        } else {
            shuffle();
            if (isLooping) {
                return currentOrderIndex = 0;
            } else {
                currentOrderIndex = -1;
            }
        }

        return currentOrderIndex >= 0 ? slideOrder[currentOrderIndex] : -1;
    }

    @Override
    public int previous() {
        if (currentOrderIndex > 0) return currentOrderIndex = currentOrderIndex - 1;
        else if (isLooping) return currentOrderIndex = getSlideCount() - 1;
        else currentOrderIndex = -1;

        return currentOrderIndex >= 0 ? slideOrder[currentOrderIndex] : -1;
    }

    @Override
    public void onSlideCountChanged(int newSlideCount) {
        if (newSlideCount == 0) {
            slideOrder = null;
            return;
        }

        slideOrder = new Integer[newSlideCount];
        for (int i = 0; i < newSlideCount; ++i) {
            slideOrder[i] = i;
        }

        shuffle();
    }

    public boolean isAutoAdvanceEnabled() {
        return isAutoAdvanceEnabled;
    }

    public void setAutoAdvanceEnabled(boolean isAutoAdvanceEnabled) {
        this.isAutoAdvanceEnabled = isAutoAdvanceEnabled;
    }

    public void shuffle() {
        if (slideOrder == null) return;

        // Save current slide
        int previousCurrentSlide = getCurrentSlide();

        // Shuffle slide order
        List<Integer> slideOrderList = Arrays.asList(slideOrder);
        Collections.shuffle(slideOrderList);
        slideOrder = slideOrderList.toArray(slideOrder);

        // Restore current slide as before
        if (previousCurrentSlide >= 0) {
            for (int i = 0; i < slideOrder.length; ++i) {
                if (slideOrder[i] == previousCurrentSlide) {
                    currentOrderIndex = i;
                    break;
                }
            }
        }
    }

    public int getSlideCount() {
        return slideOrder == null ? 0 : slideOrder.length;
    }

    @Override
    public long getSlideDuration(int position) {
        return slideDuration;
    }

    public void setSlideDuration(long slideDuration) {
        this.slideDuration = slideDuration;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }
}
