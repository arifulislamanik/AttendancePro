package com.arifulislamanik.attendancepro;

/**
 * Created by ariful islam anik on 9/3/2015.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by ariful islam anik on 8/27/2015.
 */
public class ListAdapterP extends BaseAdapter {
    String [] result;
    double [] per;
    Context context;
    int [] imageId;
    int len;
    private static LayoutInflater inflater=null;
    Typeface F ;
    public ListAdapterP(StudentId mainActivity,String[] prgmNameList,double[] p,int l) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        per = p;
        context=mainActivity;
        len = l;
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
        TextView b;
        TextView c;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_item2, null);
        holder.b=(TextView) rowView.findViewById(R.id.s_id);
        holder.c= (TextView) rowView.findViewById(R.id.parcentage);
        holder.b.setText(result[position]);
        holder.c.setText(String.format("%.2f",per[position])+"%");

        return rowView;
    }

}


