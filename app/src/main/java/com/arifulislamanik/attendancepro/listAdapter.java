package com.arifulislamanik.attendancepro;

/**
 * Created by Yeakub Hassan Rafi on 26-Apr-15.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class listAdapter extends BaseAdapter{
    String [] result;
    String [] name;
    Context context;
    int [] imageId;
    int len;
    private static LayoutInflater inflater=null;
    Typeface F ;
    public listAdapter(CourseReport mainActivity, String[] prgmNameList,String[] sub, int l) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        len = l;
        name = sub;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return len;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

    public class Holder
    {
        TextView b,c;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item_c, null);
        holder.b=(TextView) rowView.findViewById(R.id.listText);
        holder.c=(TextView) rowView.findViewById(R.id.listsubText);
        holder.b.setText(result[position]);
        holder.c.setText(name[position]);
        return rowView;
    }

}
