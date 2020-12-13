package lamarque.tsp.lol_app;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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
import java.util.Date;

public class AsyncJSONDataForListHistorique3 extends AsyncTask<String, Void, JSONObject> {

    public Activity activity;
    public AdapterHistList adapter;
    public String accountId;

    public AsyncJSONDataForListHistorique3(Activity activity, AdapterHistList adapter, String accountId) {
        this.activity = activity;
        this.adapter = adapter;
        this.accountId = accountId;

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

            String gameID = res.getString("gameId");
            adapter.vectorgame.add(gameID);

            Long dateCreation = res.getLong("gameCreation");
            Date vraidate = new Date(dateCreation);
            String datestr = vraidate.toString().substring(4,13) + "h" + vraidate.toString().substring(14,16);

            Long dateDuration = res.getLong("gameDuration");
            Double duree = dateDuration.doubleValue() / 60;
            int min = duree.intValue();
            int seconds = (int) ((duree - min) * 60);

            JSONArray participantIdentities = res.getJSONArray("participantIdentities");
            int participantId = 0;
            for (int i=0; i<participantIdentities.length(); i++) {
                JSONObject participant = participantIdentities.getJSONObject(i);
                JSONObject player = participant.getJSONObject("player");
                if (accountId.equals(player.getString("accountId"))) {
                    System.out.println("coucou");
                    participantId = participant.getInt("participantId");
                }
            }
            JSONArray participants = res.getJSONArray("participants");
            int kills = 0, deaths = 0, assists = 0, goldEarned = 0, cs = 0;
            boolean win = false;
            for (int i=0; i<participants.length(); i++) {
                JSONObject participant = participants.getJSONObject(i);
                if (participantId == participant.getInt("participantId")) {
                    JSONObject stats = participant.getJSONObject("stats");
                    win = stats.getBoolean("win");
                    kills = stats.getInt("kills");
                    deaths = stats.getInt("deaths");
                    assists = stats.getInt("assists");
                    cs = stats.getInt("totalMinionsKilled");
                    goldEarned = stats.getInt("goldEarned");
                }
            }

            String resultat = null;
            if (win == true) {
                resultat = "Victoire";
            } else {
                resultat = "Défaite";
            }


            adapter.vectordate.add(datestr + "\nDurée: " + String.format("%02d", min) + ":" + String.format("%02d", seconds) + "\nScore: " + kills + "/" + deaths + "/" + assists + "\nCS: " + cs + "\nGolds: " + goldEarned);
            adapter.vectortext.add(resultat);

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
