package lamarque.tsp.lol_app;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public class AdapterPartieList extends BaseAdapter {


    public Vector<Bitmap> vectorimage;
    public Vector<String> vectortext1;
    public Vector<String> vectortext2;

    public AdapterPartieList() {

        this.vectorimage = new Vector<Bitmap>();
        this.vectortext1 = new Vector<String>();
        this.vectortext2 = new Vector<String>();
    }

    public void add(String url) {
        this.vectortext1.add(url);
    }

    @Override
    public int getCount() {
        return this.vectortext1.size();
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
                    .inflate(R.layout.listpartie, parent, false);
        }

        TextView text1 = (TextView) convertView.findViewById(R.id.textlistpartie1);
        text1.setText(this.vectortext1.get(position));

        TextView text2 = (TextView) convertView.findViewById(R.id.textlistpartie2);
        text2.setText(this.vectortext2.get(position));


        ImageView image = (ImageView) convertView.findViewById(R.id.imagelistpartie);
        if (this.vectorimage.size() != 0) {
            image.setImageBitmap(this.vectorimage.get(position));
        }



        return convertView;

    }


}
