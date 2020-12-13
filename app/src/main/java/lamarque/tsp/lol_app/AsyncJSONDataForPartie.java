package lamarque.tsp.lol_app;

import android.app.Activity;
import android.os.AsyncTask;
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
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncJSONDataForPartie extends AsyncTask<String, Void, JSONObject> {

    public Activity activity;
    public AdapterPartieList adapter;

    public AsyncJSONDataForPartie(Activity activity, AdapterPartieList adapter) {
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

            Long dateCreation = res.getLong("gameCreation");
            Date vraidate = new Date(dateCreation);
            String datestr = vraidate.toString().substring(4,13) + "h" + vraidate.toString().substring(14,16);

            Long dateDuration = res.getLong("gameDuration");
            Double duree = dateDuration.doubleValue() / 60;
            int min = duree.intValue();
            int seconds = (int) ((duree - min) * 60);

            String mode = res.getString("gameMode");

            JSONArray team = res.getJSONArray("teams");
            JSONObject team1 = team.getJSONObject(0);
            String win = team1.getString("win");
            String textwin;
            if (win.equals("Win")) {
                textwin = "Victoire de l'équipe 1";
            } else {
                textwin = "Victoire de l'équipe 2";
            }

            TextView textpartie = (TextView) activity.findViewById(R.id.textpartie);
            textpartie.setText("GameID: " + gameID + "\nDate: " + datestr + "\nDurée: " + String.format("%02d", min) + ":" + String.format("%02d", seconds) + "\nMode: " + mode + "\n" + "\n" + textwin);

            JSONArray participantIdentities = res.getJSONArray("participantIdentities");
            int participantId = 0;
            for (int i=0; i<participantIdentities.length(); i++) {
                JSONObject participant = participantIdentities.getJSONObject(i);
                JSONObject player = participant.getJSONObject("player");
                String name = player.getString("summonerName");
                adapter.vectortext1.add(name);
            }


            JSONArray participants = res.getJSONArray("participants");
            int kills = 0, deaths = 0, assists = 0, goldEarned = 0, cs = 0;
            boolean winbool = false;
            for (int i=0; i<participants.length(); i++) {
                JSONObject participant = participants.getJSONObject(i);
                int championID = participant.getInt("championId");
                String championName = getIDToName(championID);

                AsyncBitmapDownloaderForPartie b = new AsyncBitmapDownloaderForPartie(activity, adapter);
                b.execute("http://ddragon.leagueoflegends.com/cdn/10.25.1/img/champion/" + championName + ".png");
                b.get(1000, TimeUnit.MILLISECONDS);

                String textw;
                int teamID = participant.getInt("teamId");
                if (teamID == 100) {
                    textw = "Equipe 1";
                } else {
                    textw = "Equipe 2";
                }


                JSONObject stats = participant.getJSONObject("stats");
                winbool = stats.getBoolean("win");
                kills = stats.getInt("kills");
                deaths = stats.getInt("deaths");
                assists = stats.getInt("assists");
                cs = stats.getInt("totalMinionsKilled");
                goldEarned = stats.getInt("goldEarned");

                String resultat = null;
                if (winbool == true) {
                    resultat = "Victoire";
                } else {
                    resultat = "Défaite";
                }

                JSONObject timeline = participant.getJSONObject("timeline");
                String role = timeline.getString("lane");

                adapter.vectortext2.add(textw + ": " + resultat + "\nRôle: " + role + "\n" + "\nScore: " + kills + "/" + deaths + "/" + assists + "\nCS: " + cs + "\nGolds: " + goldEarned + "\n" );


            }

            ListView listpartie = (ListView) activity.findViewById(R.id.listpartie);
            listpartie.setAdapter(this.adapter);

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

    private String getIDToName(int id) {
        switch (id) {
            case 266:
                return "Aatrox";
            case 412:
                return "Thresh";
            case 23:
                return "Tryndamere";
            case 79:
                return "Gragas";
            case 69:
                return "Cassiopeia";
            case 136:
                return "AurelionSol";
            case 13:
                return "Ryze";
            case 78:
                return "Poppy";
            case 14:
                return "Sion";
            case 1:
                return "Annie";
            case 202:
                return "Jhin";
            case 43:
                return "Karma";
            case 111:
                return "Nautilus";
            case 240:
                return "Kled";
            case 99:
                return "Lux";
            case 103:
                return "Ahri";
            case 2:
                return "Olaf";
            case 112:
                return "Viktor";
            case 34:
                return "Anivia";
            case 27:
                return "Singed";
            case 86:
                return "Garen";
            case 127:
                return "Lissandra";
            case 57:
                return "Maokai";
            case 25:
                return "Morgana";
            case 28:
                return "Evelynn";
            case 105:
                return "Fizz";
            case 74:
                return "Heimerdinger";
            case 238:
                return "Zed";
            case 68:
                return "Rumble";
            case 82:
                return "Mordekaiser";
            case 37:
                return "Sona";
            case 96:
                return "KogMaw";
            case 55:
                return "Katarina";
            case 117:
                return "Lulu";
            case 22:
                return "Ashe";
            case 30:
                return "Karthus";
            case 12:
                return "Alistar";
            case 122:
                return "Darius";
            case 67:
                return "Vayne";
            case 110:
                return "Varus";
            case 77:
                return "Udyr";
            case 89:
                return "Leona";
            case 126:
                return "Jayce";
            case 134:
                return "Syndra";
            case 80:
                return "Pantheon";
            case 92:
                return "Riven";
            case 121:
                return "Khazix";
            case 42:
                return "Corki";
            case 268:
                return "Azir";
            case 51:
                return "Caitlyn";
            case 76:
                return "Nidalee";
            case 85:
                return "Kennen";
            case 3:
                return "Galio";
            case 45:
                return "Veigar";
            case 432:
                return "Bard";
            case 150:
                return "Gnar";
            case 90:
                return "Malzahar";
            case 104:
                return "Graves";
            case 254:
                return "Vi";
            case 10:
                return "Kayle";
            case 39:
                return "Irelia";
            case 64:
                return "LeeSin";
            case 420:
                return "Illaoi";
            case 60:
                return "Elise";
            case 106:
                return "Volibear";
            case 20:
                return "Nunu";
            case 4:
                return "TwistedFate";
            case 24:
                return "Jax";
            case 102:
                return "Shyvana";
            case 429:
                return "Kalista";
            case 36:
                return "DrMundo";
            case 427:
                return "Ivern";
            case 131:
                return "Diana";
            case 223:
                return "TahmKench";
            case 63:
                return "Brand";
            case 113:
                return "Sejuani";
            case 8:
                return "Vladimir";
            case 154:
                return "Zac";
            case 421:
                return "RekSai";
            case 133:
                return "Quinn";
            case 84:
                return "Akali";
            case 163:
                return "Taliyah";
            case 18:
                return "Tristana";
            case 120:
                return "Hecarim";
            case 15:
                return "Sivir";
            case 236:
                return "Lucian";
            case 107:
                return "Rengar";
            case 19:
                return "Warwick";
            case 72:
                return "Skarner";
            case 54:
                return "Malphite";
            case 157:
                return "Yasuo";
            case 101:
                return "Xerath";
            case 17:
                return "Teemo";
            case 75:
                return "Nasus";
            case 58:
                return "Renekton";
            case 119:
                return "Draven";
            case 35:
                return "Shaco";
            case 50:
                return "Swain";
            case 91:
                return "Talon";
            case 40:
                return "Janna";
            case 115:
                return "Ziggs";
            case 245:
                return "Ekko";
            case 61:
                return "Orianna";
            case 114:
                return "Fiora";
            case 9:
                return "Fiddlesticks";
            case 31:
                return "Chogath";
            case 33:
                return "Rammus";
            case 7:
                return "LeBlanc";
            case 16:
                return "Soraka";
            case 26:
                return "Zilean";
            case 56:
                return "Nocturne";
            case 222:
                return "Jinx";
            case 83:
                return "Yorick";
            case 6:
                return "Urgot";
            case 203:
                return "Kindred";
            case 21:
                return "MissFortune";
            case 62:
                return "Wukong";
            case 53:
                return "Blitzcrank";
            case 98:
                return "Shen";
            case 201:
                return "Braum";
            case 5:
                return "XinZhao";
            case 29:
                return "Twitch";
            case 11:
                return "MasterYi";
            case 44:
                return "Taric";
            case 32:
                return "Amumu";
            case 41:
                return "Gangplank";
            case 48:
                return "Trundle";
            case 38:
                return "Kassadin";
            case 161:
                return "Velkoz";
            case 143:
                return "Zyra";
            case 267:
                return "Nami";
            case 59:
                return "JarvanIV";
            case 81:
                return "Ezreal";
            case 164:
                return "Camille";
            case 498:
                return "Xayah";
            case 497:
                return "Rakan";
            case 141:
                return "Kayn";
            case 516:
                return "Ornn";
            case 142:
                return "Zoe";
            case 145:
                return "Kaisa";
            case 555:
                return "Pyke";
            case 518:
                return "Neeko";
            case 517:
                return "Sylas";
            case 350:
                return "Yuumi";
            case 246:
                return "Qiyana";
            case 235:
                return "Senna";
            case 523:
                return "Aphelios";
            case 875:
                return "Sett";
            case 876:
                return "Lillia";
            case 777:
                return "Yone";
            case 147:
                return "Seraphine";
            case 360:
                return "Samira";
        }
        return "Rell";
    }

}
