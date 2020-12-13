package lamarque.tsp.lol_app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloaderForPartie extends AsyncTask<String, Void, Bitmap>{

    public Activity activity;
    public AdapterPartieList adapter;

    public AsyncBitmapDownloaderForPartie(Activity activity, AdapterPartieList adapter) {
        this.activity = activity;
        this.adapter = adapter;

    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        Bitmap bm = null;
        System.out.println(strings[0]);

        try {
            System.out.println(strings[0] + "coucou");
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                bm = BitmapFactory.decodeStream(in);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;
    }

    protected void onPostExecute(Bitmap bitmap) {
        this.adapter.vectorimage.add(bitmap);
        adapter.notifyDataSetChanged();

    }

}
