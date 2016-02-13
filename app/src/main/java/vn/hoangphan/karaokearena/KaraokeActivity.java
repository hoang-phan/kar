package vn.hoangphan.karaokearena;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UpdateAppearance;
import android.view.WindowManager;
import android.widget.TextView;

import com.marvinlabs.widget.slideshow.SlideShowView;
import com.marvinlabs.widget.slideshow.adapter.ResourceBitmapAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import vn.hoangphan.karaokearena.models.Line;
import vn.hoangphan.karaokearena.models.Word;
import vn.hoangphan.karaokearena.models.WordWithIndex;
import vn.hoangphan.karaokearena.playlists.CustomRandomPlaylist;
import vn.hoangphan.karaokearena.transitions.CustomTransitionFactory;

public class KaraokeActivity extends Activity {
    public static final String URL = "https://www.dropbox.com/s/dnrop9jldn61zem/%5BKaraoke%20beat%5D%20Xe%20%C4%91%E1%BA%A1p%20-%20Th%C3%B9y%20Chi%20ft.%20M4U%20%28h%E1%BA%A1%201%20note%29.mp4?dl=1";
    public static final int UNPROCESSED_COLOR = 0xffff0000;
    public static final int PROCESSED_COLOR = 0xff0000ff;
    public static final String FILE_NAME = "sample.mp4";
    public static final String FILE_PATH = "/sdcard/" + FILE_NAME;

    private SlideShowView mMainSlides;
    private MediaPlayer mBeatPlayer;
    private ArrayList<Line> mLyric = new ArrayList<>();
    private int processing;
    private TextView mLine1Tv;
    private TextView mLine2Tv;
    ForegroundColorSpan processedSpan;
    ForegroundColorSpan unprocessedSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karaoke);
        initComponents();
        bindComponents();
    }



    private void bindComponents() {
        playFile();
    }

    private void initComponents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("xedap.lyr")));
                    String buf;
                    Line line = null;
                    while ((buf = reader.readLine()) != null) {
                        if (buf.startsWith("-")) {
                            if (line != null) {
                                mLyric.add(line);
                            }
                            line = new Line();
                            buf = buf.substring(1);
                        }
                        String[] components = buf.split(",");
                        if (line != null) {
                            line.addWord(new Word(components[0], components[1], Integer.valueOf(components[2]), Integer.valueOf(components[3])));
                        }
                    }
                    if (line != null) {
                        mLyric.add(line);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        processedSpan = new ForegroundColorSpan(PROCESSED_COLOR);
        unprocessedSpan = new ForegroundColorSpan(UNPROCESSED_COLOR);
        mMainSlides = (SlideShowView) findViewById(R.id.slides_main);
        mLine1Tv = (TextView) findViewById(R.id.tv_line_1);
        mLine2Tv = (TextView) findViewById(R.id.tv_line_2);
        mBeatPlayer = new MediaPlayer();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void playFile() {
        startSlides();
        startBeat();
        startLyricDisplayer();
    }

    private void startLyricDisplayer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mLyric.size() >= 2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLine1Tv.setText(mLyric.get(0).toString());
                            mLine2Tv.setText(mLyric.get(1).toString());
                        }
                    });
                    int duration = mBeatPlayer.getDuration();
                    int current;
                    int size;
                    processing = 0;

                    long start = System.currentTimeMillis();
                    TextView processingTv = mLine1Tv;
                    while ((current = (int)(System.currentTimeMillis() - start)) < duration) {
                        size = mLyric.size();
                        final Line currentLine = mLyric.get(processing);
                        WordWithIndex wordWithIndex = currentLine.getWordAt(current);
                        Word word = wordWithIndex.getWord();
                        int index = wordWithIndex.getIndex();

                        int timespan = current - word.getProcessedAt();
                        if (timespan < 0) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            continue;
                        }

                        String wholeString = currentLine.toString();
                        final Spannable text = new SpannableStringBuilder(wholeString);

                        if (timespan <= word.getDuration()) {
                            String processed = currentLine.toString(0, index - 1);
                            int wordLen = word.getContent().length();
                            int processedEnd = processed.length() + wordLen * timespan / word.getDuration();
                            text.setSpan(processedSpan, 0, processedEnd, 0);
                            text.setSpan(unprocessedSpan, processedEnd, wholeString.length(), 0);
                            final TextView finalProcessingTv = processingTv;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finalProcessingTv.setText(text);
                                }
                            });
                        } else {
                            final TextView finalProcessingTv = processingTv;
                            text.setSpan(processedSpan, 0, wholeString.length(), 0);
                            final CountDownLatch countDownLatch = new CountDownLatch(1);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    finalProcessingTv.setText(text);
                                    countDownLatch.countDown();
                                }
                            });
                            countDownLatch.countDown();
                            processing += 1;
                            if (size <= processing) {
                                break;
                            }
                            if (size > processing + 1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finalProcessingTv.setText(mLyric.get(processing + 1).toString());
                                    }
                                });
                            }

                            processingTv = processingTv == mLine1Tv ? mLine2Tv : mLine1Tv;
                        }
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void startBeat() {
        try {
            AssetFileDescriptor descriptor = getAssets().openFd("Xedap-beat.mp3");
            mBeatPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mBeatPlayer.prepare();
            mBeatPlayer.setVolume(1f, 1f);
            mBeatPlayer.setLooping(false);
            mBeatPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startSlides() {
        mMainSlides.setAdapter(new ResourceBitmapAdapter(this, new int[] { R.drawable.schoolhood1, R.drawable.schoolhood2, R.drawable.schoolhood3, R.drawable.schoolhood4, R.drawable.schoolhood5, R.drawable.schoolhood6, R.drawable.schoolhood7, R.drawable.schoolhood8, R.drawable.schoolhood9, R.drawable.schoolhood10 }));
        mMainSlides.setPlaylist(new CustomRandomPlaylist());
        mMainSlides.setTransitionFactory(new CustomTransitionFactory(2000));
        mMainSlides.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBeatPlayer != null) {
            mBeatPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBeatPlayer != null) {
            mBeatPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBeatPlayer != null && mBeatPlayer.isPlaying()) {
            mBeatPlayer.release();
            mBeatPlayer.stop();
        }
    }
}
