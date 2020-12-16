package lamarque.tsp.lol_app;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncJSONDataForChamp extends AsyncTask<String, Void, JSONObject> {

    public Activity activity;
    public String name;

    public AsyncJSONDataForChamp(Activity activity, String name) {
        this.activity = activity;
        this.name = name;

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
            JSONObject champion = res2.getJSONObject(name);
            String title = champion.getString("title");

            TextView textchamp = (TextView) activity.findViewById(R.id.champName);
            textchamp.setText(name + "\nTitre: " + title);

            String lore = champion.getString("lore");

            TextView textchamp2 = (TextView) activity.findViewById(R.id.textchamp2);
            textchamp2.setText("Histoire:\n" + lore + "\n");

            JSONArray allytips = champion.getJSONArray("allytips");
            String atips = "Astuces (alliés) : \n";
            for (int i=0; i< allytips.length(); i++) {
                atips = atips + "   - " + allytips.getString(i) + "\n";
            }

            JSONArray enemytips = champion.getJSONArray("enemytips");
            String etips = "Astuces (ennemies) : \n";
            for (int i=0; i< enemytips.length(); i++) {
                etips = etips + "   - " + enemytips.getString(i) + "\n";
            }

            TextView textchamp3 = (TextView) activity.findViewById(R.id.textchamp3);
            textchamp3.setText(atips + "\n" + etips + "\n");

            JSONObject info = champion.getJSONObject("info");
            String attack = info.getString("attack");
            String defense = info.getString("defense");
            String magic = info.getString("magic");
            String difficulty = info.getString("difficulty");

            TextView textchamp4 = (TextView) activity.findViewById(R.id.textchamp4);
            textchamp4.setText(" Informations générales\n" + "\n     Attaque: " + attack + "\n     Défense: " + defense + "\n     Magie: " + magic + "\n     Difficulté: " + difficulty + "\n");

            JSONObject stats = champion.getJSONObject("stats");
            String hp = stats.getString("hp");
            String mp = stats.getString("mp");
            String movespeed = stats.getString("movespeed");
            String armor = stats.getString("armor");
            String spellblock = stats.getString("spellblock");
            String attackspeed = stats.getString("attackspeed");

            TextView textchamp5 = (TextView) activity.findViewById(R.id.textchamp5);
            textchamp5.setText(" Statistiques (niveau 1)\n" + "\n     PV: " + hp + "\n     PM: " + mp + "\n     Vitesse(dépl.): " + movespeed + "\n     Vitesse d'attaque: " + attackspeed + "\n     Armure: " + armor + "\n     Résistance mag.: " + spellblock + "\n");

            JSONArray spells = champion.getJSONArray("spells");
            String spell = "Sorts:\n";
            for (int i=0; i< spells.length(); i++) {
                JSONObject sort = spells.getJSONObject(i);
                String namespell = sort.getString("name");
                String description = sort.getString("description");
                spell = spell + "\n   - " + namespell + ":\n       " + description + "\n";

            }


            TextView textchamp6 = (TextView) activity.findViewById(R.id.textchamp6);
            textchamp6.setText(spell);



            TextView textchamp7 = (TextView) activity.findViewById(R.id.textchamp7);
            textchamp7.setText(" ");



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
