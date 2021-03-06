package xyz.dushyant31.laundroapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.ImageView;

import java.util.List;

public class ArrayAdapterQRcodes extends ArrayAdapter < MyDataModel > {

    List < MyDataModel > modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public ArrayAdapterQRcodes(Context context, List < MyDataModel > objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.qrcodes, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);

        vh.Qrcodes.setImageBitmap(item.getQrcode());

        return vh.rootView;
    }



    private static class ViewHolder {
        public final RelativeLayout rootView;

        public final ImageView Qrcodes;


        private ViewHolder(RelativeLayout rootView, ImageView Qrcodes) {
            this.rootView = rootView;
            this.Qrcodes = Qrcodes;

        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView Qrcode = rootView.findViewById(R.id.qrlist);

            return new ViewHolder(rootView, Qrcode);
        }
    }


}