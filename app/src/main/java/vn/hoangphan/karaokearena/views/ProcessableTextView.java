package vn.hoangphan.karaokearena.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import vn.hoangphan.karaokearena.R;

/**
 * Created by Hoang Phan on 2/14/2016.
 */
public class ProcessableTextView extends TextView {
    private ForegroundColorSpan mProcessedSpan;
    private ForegroundColorSpan mUnprocessedSpan;
    private int mProcessedColor;

    public ProcessableTextView(Context context) {
        this(context, null);
    }

    public ProcessableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProcessableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ProcessableTextView,
                0, 0
        );

        try {
            mProcessedColor = typedArray.getColor(R.styleable.ProcessableTextView_processed_color, 0xff000000);
            mUnprocessedSpan = new ForegroundColorSpan(getCurrentTextColor());
            mProcessedSpan = new ForegroundColorSpan(mProcessedColor);
        } finally {
            typedArray.recycle();
        }
    }

    public void setProcessedPosition(int position) {
        CharSequence sequence = getText();
        Spannable text = new SpannableStringBuilder(sequence);
        text.setSpan(mProcessedSpan, 0, position, 0);
        text.setSpan(mUnprocessedSpan, position, sequence.length(), 0);
        setText(text);
    }
}
