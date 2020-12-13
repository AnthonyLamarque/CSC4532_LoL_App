package lamarque.tsp.lol_app;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncJSONDataForListChamp extends AsyncTask<String, Void, JSONObject> {

    public Activity activity;
    public AdapterChampList adapter;

    public AsyncJSONDataForListChamp(Activity activity, AdapterChampList adapter) {
        this.activity = activity;
        this.adapter = adapter;

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        JSONObject res = null;
        try {
            System.out.println(strings[0]);
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                Log.i("JFL", s);
                res = new JSONObject(s);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    protected void onPostExecute(JSONObject res) {
        try {

            JSONObject res2 = res.getJSONObject("data");
            JSONArray keys = res2.names ();

            for (int i = 0; i< res2.length(); i++){
                String key = keys.getString(i);
                JSONObject res3 = res2.getJSONObject(key);
                String res4 = res3.getString("id");
                this.adapter.vector.add(res4);

                AsyncBitmapDownloader b = new AsyncBitmapDownloader(activity, adapter);
                b.execute("http://ddragon.leagueoflegends.com/cdn/10.24.1/img/champion/" + res4 + ".png");
                b.get(1000, TimeUnit.MILLISECONDS);


                Log.i("JFL", "Adding to adapter name : " + res4);
            }
            ListView list = (ListView) activity.findViewById(R.id.listChampions);
            list.setAdapter(this.adapter);



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

}
