package vn.hoangphan.karaokearena.async;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Hoang Phan on 2/10/2016.
 */
public class DownloadFileFromURL extends AsyncTask<String, Integer, String> {
    private String mFilePath;
    private OnProgressUpdate mOnProgressUpdate;
    private OnPostExecute mOnPostExecute;

    public DownloadFileFromURL(String filePath) {
        mFilePath = filePath;
    }

    @Override
    protected String doInBackground(String... f_url) {
        try {
            File file = new File(mFilePath);
            if (file.exists()) {
                Log.d("Download", "Downloaded");
            } else {
                int count;

                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int contentLength = connection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / contentLength));

                    output.write(data, 0, count);
                }

                output.flush();

                output.close();
                input.close();
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    public void setOnProgressUpdate(OnProgressUpdate onProgressUpdate) {
        mOnProgressUpdate = onProgressUpdate;
    }

    public void setOnPostExecute(OnPostExecute onPostExecute) {
        mOnPostExecute = onPostExecute;
    }

    protected void onProgressUpdate(Integer... progress) {
        if (mOnProgressUpdate != null) {
            mOnProgressUpdate.update(progress[0]);
        }
    }

    @Override
    protected void onPostExecute(String file_url) {
        if (mOnPostExecute != null) {
            mOnPostExecute.execute();
        }
    }
}