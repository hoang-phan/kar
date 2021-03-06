package vn.hoangphan.karaokearena.activities;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.hoangphan.karaokearena.R;
import vn.hoangphan.karaokearena.analysis.ComplexDoubleFFT;
import vn.hoangphan.karaokearena.models.Line;
import vn.hoangphan.karaokearena.models.Word;
import vn.hoangphan.karaokearena.utils.Constants;
import vn.hoangphan.karaokearena.views.ProcessableTextView;

public class KaraokeActivity extends Activity implements SurfaceHolder.Callback {
    public static final String URL = "https://www.dropbox.com/s/dnrop9jldn61zem/%5BKaraoke%20beat%5D%20Xe%20%C4%91%E1%BA%A1p%20-%20Th%C3%B9y%20Chi%20ft.%20M4U%20%28h%E1%BA%A1%201%20note%29.mp4?dl=1";
    public static final int UNPROCESSED_COLOR = 0xffff0000;
    public static final int PROCESSED_COLOR = 0xff0000ff;
    public static final String FILE_NAME = "sample.mp4";
    public static final String FILE_PATH = "/sdcard/" + FILE_NAME;
    private static final int SAMPLE_RATE_HZ = 8000;
    private static final int CHANNEL_IN_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    public static final double[] NOTE_FREQUENCIES = { 16.35, 17.32, 18.35, 19.45, 20.60, 21.83, 23.12, 24.50, 25.96, 27.50, 29.14, 30.87, 32.70, 34.65, 36.71, 38.89, 41.20, 43.65, 46.25, 49.00, 51.91, 55.00, 58.27, 61.74, 65.41, 69.30, 73.42, 77.78, 82.41, 87.31, 92.50, 98.00, 103.8, 110.0, 116.5, 123.5, 130.8, 138.6, 146.8, 155.6, 164.8, 174.6, 185.0, 196.0, 207.7, 220.0, 233.1, 246.9, 261.6, 277.2, 293.7, 311.1, 329.6, 349.2, 370.0, 392.0, 415.3, 440.0, 466.2, 493.9, 523.3, 554.4, 587.3, 622.3, 659.3, 698.5, 740.0, 784.0, 830.6, 880.0, 932.3, 987.8, 1047, 1109, 1175, 1245, 1319, 1397, 1480, 1568, 1661, 1760, 1865, 1976, 2093, 2217, 2349, 2489, 2637, 2794, 2960, 3136, 3322, 3520, 3729, 3951, 4186, 4435, 4699, 4978, 5274, 5588, 5920, 6272, 6645, 7040, 7459, 7902 };
    public static final String[] NOTE_NAMES = {"C0", "C#0", "D0", "Eb0", "E0", "F0", "F#0", "G0", "G#0", "A0", "Bb0", "B0", "C1", "C#1", "D1", "Eb1", "E1", "F1", "F#1", "G1", "G#1", "A1", "Bb1", "B1", "C2", "C#2", "D2", "Eb2", "E2", "F2", "F#2", "G2", "G#2", "A2", "Bb2", "B2", "C3", "C#3", "D3", "Eb3", "E3", "F3", "F#3", "G3", "G#3", "A3", "Bb3", "B3", "C4", "C#4", "D4", "Eb4", "E4", "F4", "F#4", "G4", "G#4", "A4", "Bb4", "B4", "C5", "C#5", "D5", "Eb5", "E5", "F5", "F#5", "G5", "G#5", "A5", "Bb5", "B5", "C6", "C#6", "D6", "Eb6", "E6", "F6", "F#6", "G6", "G#6", "A6", "Bb6", "B6", "C7", "C#7", "D7", "Eb7", "E7", "F7", "F#7", "G7", "G#7", "A7", "Bb7", "B7", "C8", "C#8", "D8", "Eb8", "E8", "F8", "F#8", "G8", "G#8", "A8", "Bb8", "B8"};
    public static final int NOTES_COUNT = NOTE_NAMES.length;
    private MediaPlayer mBeatPlayer;
    private List<Line> mLyric = new ArrayList<>();
    private ProcessableTextView mLine1Tv;
    private ProcessableTextView mLine2Tv;
    private LinearLayout mScoreboardLy;
    private ImageView mDrawerIv;
    private ForegroundColorSpan mProcessedSpan;
    private ForegroundColorSpan mUnprocessedSpan;
    private AudioRecord mAudioRecord;
    private ComplexDoubleFFT mTransformer;
    private Canvas mCanvas;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Handler mHandler;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private int mScoreboardHeight;
    private Button mStartStopBtn;
    private boolean mInitSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karaoke);
    }

    private void bindComponents() {
        mPaint.setColor(Color.GREEN);
        mDrawerIv.setImageBitmap(mBitmap);
        ViewTreeObserver observer = mScoreboardLy.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                initScoreboardHeight();
                mScoreboardLy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mStartStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInitSuccessful) {
                    playFile();
                }
            }
        });
    }

    private void initScoreboardHeight() {
        mScoreboardHeight = mScoreboardLy.getHeight() - 40;
    }

    private void initComponents() {
        mBitmap = Bitmap.createBitmap(1000, 200, Bitmap.Config.ARGB_8888);
        mProcessedSpan = new ForegroundColorSpan(PROCESSED_COLOR);
        mUnprocessedSpan = new ForegroundColorSpan(UNPROCESSED_COLOR);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mBeatPlayer = new MediaPlayer();
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, SAMPLE_RATE_HZ, CHANNEL_IN_CONFIG, ENCODING, getMinBufferSize());
        mTransformer = new ComplexDoubleFFT(Constants.RECORD_BUFFERED_SIZE);
        mHandler = new Handler();
        mMediaRecorder = new MediaRecorder();

        mLine1Tv = (ProcessableTextView) findViewById(R.id.tv_line_1);
        mLine2Tv = (ProcessableTextView) findViewById(R.id.tv_line_2);
        mScoreboardLy = (LinearLayout) findViewById(R.id.ly_scoreboard);
        mDrawerIv = (ImageView) findViewById(R.id.iv_drawer);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mStartStopBtn = (Button) findViewById(R.id.btn_start_stop);

        mSurfaceHolder = mSurfaceView.getHolder();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void playFile() {
        startVideoCapture();
        startBeat();
        startLyricDisplayer();
    }

    private void startVideoCapture() {
        if (mInitSuccessful) {
            mMediaRecorder.start();
        } else {
            Log.e("error", "cannot start media recorder");
        }
    }

    private void startLyricDisplayer() {
        mLine1Tv.setText(mLyric.get(0).getContent());
        final int size = mLyric.size();

        for (int i = 0; i < size; i++) {
            Line currentLine = mLyric.get(i);
            int lastPosition = 0;
            final int iClone = i;
            List<Word> words = new ArrayList<>();

            for (int j = 0; j < words.size(); j++) {
                final Word word = words.get(j);
                final int position = word.getPosition();
                final int lastPositionClone = lastPosition;
                final int jClone = j;
                lastPosition = position;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TextView processing, next;
                        if (iClone % 2 == 0) {
                            processing = mLine1Tv;
                            next = mLine2Tv;
                        } else {
                            processing = mLine2Tv;
                            next = mLine1Tv;
                        }
                        if (jClone == 0 && iClone < size - 1) {
                            next.setText(mLyric.get(iClone + 1).getContent());
                        }
                        ObjectAnimator animator = ObjectAnimator.ofInt(processing, "processedPosition", lastPositionClone, position);
                        animator.setDuration(word.getDuration());
                        animator.start();
                    }
                }, word.getProcessedAt());
            }
        }
    }

    private void startBeat() {
        try {
            AssetFileDescriptor descriptor = getAssets().openFd("Xedap-beat.mp3");
            mBeatPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            mBeatPlayer.prepare();
            mBeatPlayer.setVolume(1f, 1f);
            mBeatPlayer.setLooping(false);
            mBeatPlayer.start();

            mAudioRecord.startRecording();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double getFrequency() {
        short[] array = new short[Constants.RECORD_BUFFERED_SIZE];
        double[] toTransform = new double[Constants.RECORD_BUFFERED_SIZE * 2];

        int length = mAudioRecord.read(array, 0, Constants.RECORD_BUFFERED_SIZE);
        for (int i = 0; i < length; i++) {
            toTransform[2 * i] = array[i] / 32768.0; // signed 16 bit
            toTransform[2 * i + 1] = 0;
        }
        mTransformer.ft(toTransform);
        double magnitude[] = new double[Constants.RECORD_BUFFERED_SIZE / 2];

        for (int i = 0; i < magnitude.length; i++) {
            double R = toTransform[2 * i] * toTransform[2 * i];
            double I = toTransform[2 * i + 1] * toTransform[2 * i + 1];

            magnitude[i] = Math.sqrt(I + R);
        }
        int maxIndex = 0;
        double max = magnitude[0];
        for(int i = 1; i < magnitude.length; i++) {
            if (magnitude[i] > max) {
                max = magnitude[i];
                maxIndex = i;
            }
        }

        return (double)maxIndex * SAMPLE_RATE_HZ / Constants.RECORD_BUFFERED_SIZE;
    }

    private int lookUpNotePos(double freq) {
        return lookUpNotePos(freq, 0, NOTES_COUNT);
    }

    private int lookUpNotePos(double freq, int minIndex, int maxIndex) {
        if (minIndex >= maxIndex) {
            return maxIndex;
        }
        int mid = (minIndex + maxIndex) / 2;
        if (freq < NOTE_FREQUENCIES[mid]) {
            return lookUpNotePos(freq, minIndex, mid);
        } else {
            return lookUpNotePos(freq, mid + 1, maxIndex);
        }
    }

    private int getMinBufferSize() {
        return AudioRecord.getMinBufferSize(SAMPLE_RATE_HZ, CHANNEL_IN_CONFIG, ENCODING);
    }

    private void initRecorder(Surface surface) throws IOException {
        if(mCamera == null) {
            mCamera = Camera.open();
            mCamera.unlock();
        }

        if(mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();

        mMediaRecorder.setPreviewDisplay(surface);
        mMediaRecorder.setCamera(mCamera);

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        File file = new File(Environment.getExternalStorageDirectory(),"/x.3gp");
        if(!file.exists()) {
            File parent = file.getParentFile();
            if(parent != null)
                if(!parent.exists())
                    if(!parent.mkdirs())
                        throw new IOException("6 " +
                                "parent directories for file: " + file);

            file.createNewFile();
        }

        mMediaRecorder.setOutputFile(file.getAbsolutePath());

        mMediaRecorder.setMaxDuration(-1);
        mMediaRecorder.setVideoFrameRate(15);

        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mMediaRecorder.prepare();

        mInitSuccessful = true;
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
        if (mAudioRecord != null) {
            mAudioRecord.release();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (!mInitSuccessful) {
                initRecorder(mSurfaceHolder.getSurface());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        shutdown();
    }

    private void shutdown() {
        mMediaRecorder.reset();
        mMediaRecorder.release();
        if (mCamera != null) {
            mCamera.release();
        }
        mMediaRecorder = null;
        mCamera = null;
    }
}
