package lamarque.tsp.lol_app;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

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

public class AsyncJSONDataForListHistorique extends AsyncTask<String, Void, JSONObject> {

    public Activity activity;
    public AdapterHistList adapter;

    public AsyncJSONDataForListHistorique(Activity activity, AdapterHistList adapter) {
        this.activity = activity;
        this.adapter = adapter;

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        JSONObject res = null;
        try {
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
            cancel(true);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            cancel(true);
        }
        return res;
    }

    protected void onPostExecute(JSONObject res) {
        try {

            String accountID = res.getString("accountId");
            System.out.println(accountID);

            AsyncJSONDataForListHistorique2 a = new AsyncJSONDataForListHistorique2(activity, adapter, accountID);
            a.execute("https://euw1.api.riotgames.com/lol/match/v4/matchlists/by-account/" + accountID + "?api_key=" + Api.API_KEY);




        } catch (JSONException e) {
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
