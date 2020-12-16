package lamarque.tsp.lol_app;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public class AdapterFavList extends BaseAdapter {

        public Vector<String> vectortext;

        public AdapterFavList() {
            this.vectortext = new Vector<String>();
        }

        public void add(String url) {
            this.vectortext.add(url);
        }

        @Override
        public int getCount() {
            return this.vectortext.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listfav, parent, false);
            }

            TextView textlistfav = (TextView) convertView.findViewById(R.id.textlistfav);
            textlistfav.setText(this.vectortext.get(position));

            return convertView;

        }



}
