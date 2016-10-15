package com.arifulislamanik.attendancepro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by ariful islam anik on 6/27/2015.
 */
public class StudentListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    int as=R.drawable.absent;
    int ans = R.drawable.absent_ns;
    int ls= R.drawable.late;
    int lns = R.drawable.late_ns;
    int ps=R.drawable.present;
    int pns= R.drawable.present_ns;
    static int[] pres=new int[100];
    static int[] abs=new int[100];
    static int[] lat=new int[100];
    String att;
    int len;
    Students_attendance s = new Students_attendance();
    private static LayoutInflater inflater=null;
    public StudentListAdapter(Students_attendance context, String[] name,String a,int l) {
        super(context, R.layout.student_list_item, name);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.name=name;
        len=l;
        s.att = "P";
        for(int i = 1; i<len;i++)
        {
            s.att=s.att+"P";
        }
        att = a;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
        for(int i=0;i<l;i++)
        {
            pres[i]=1;
            abs[i]=0;
            lat[i]=0;
        }
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return len;
    }

    public View getView(final int position,View view,ViewGroup parent) {
      //  LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.student_list_item, null, true);

        final TextView txtTitle = (TextView) rowView.findViewById(R.id.textView12);
        final ImageView absent = (ImageView) rowView.findViewById(R.id.imageButton2);
        final ImageView late = (ImageView) rowView.findViewById(R.id.imageButton);
        final ImageView present = (ImageView) rowView.findViewById(R.id.imageButton3);
        if(abs[position]==0)
        {
            absent.setImageResource(ans);
        }
        else absent.setImageResource(as);

        if(lat[position]==0)
        {
            late.setImageResource(lns);
        }
        else late.setImageResource(ls);
        if(pres[position]==0)
        {
            present.setImageResource(pns);
        }
        else present.setImageResource(ps);
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(abs[position]==0 && lat[position]==0)
                {
                    absent.setImageResource(as);
                    present.setImageResource(pns);
                    late.setImageResource(lns);
                    abs[position]=1;
                    pres[position]=0;
                    lat[position]=0;
                    StringBuilder sb = new StringBuilder();
                    s.att = s.att.substring(0,position)+'A'+s.att.substring(position+1);
                }
                else if(pres[position]==0 && lat[position]==0)
                {
                    absent.setImageResource(ans);
                    present.setImageResource(pns);
                    late.setImageResource(ls);
                    lat[position]=1;
                    abs[position]=0;
                    pres[position]=0;
                    s.att = s.att.substring(0,position)+'L'+s.att.substring(position+1);
                }
                else if(pres[position]==0 && abs[position]==0)
                {
                    absent.setImageResource(ans);
                    present.setImageResource(ps);
                    late.setImageResource(lns);
                    abs[position]=0;
                    pres[position]=1;
                    lat[position]=0;
                    s.att = s.att.substring(0,position)+'P'+s.att.substring(position+1);
                }
            }
        });
        txtTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context,att_form1.class);
                intent.putExtra("id",txtTitle.getText().toString());
                context.startActivity(intent);
                return false;
            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(abs[position]==0)
                {
                    absent.setImageResource(as);
                    present.setImageResource(pns);
                    late.setImageResource(lns);
                    abs[position]=1;
                    pres[position]=0;
                    lat[position]=0;
                    StringBuilder sb = new StringBuilder();
                    s.att = s.att.substring(0,position)+'A'+s.att.substring(position+1);

                }
                else
                {
                    absent.setImageResource(ans);
                    present.setImageResource(ps);
                    late.setImageResource(lns);
                    abs[position]=0;
                    pres[position]=1;
                    lat[position]=0;
                    s.att = s.att.substring(0,position)+'P'+s.att.substring(position+1);

                }
            }
        });

        late.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(lat[position]==0)
                {
                    absent.setImageResource(ans);
                    present.setImageResource(pns);
                    late.setImageResource(ls);
                    lat[position]=1;
                    abs[position]=0;
                    pres[position]=0;
                    s.att = s.att.substring(0,position)+'L'+s.att.substring(position+1);

                }
                else
                {
                    absent.setImageResource(ans);
                    present.setImageResource(ps);
                    late.setImageResource(lns);
                    lat[position]=0;
                    abs[position]=0;
                    pres[position]=1;
                    s.att = s.att.substring(0,position)+'P'+s.att.substring(position+1);

                }
            }
        });

        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                absent.setImageResource(ans);
                present.setImageResource(ps);
                late.setImageResource(lns);
                pres[position]=1;
                abs[position]=0;
                lat[position]=0;
                s.att = s.att.substring(0,position)+'P'+s.att.substring(position+1);

            }
        });

        txtTitle.setText(name[position]);
//        imageView.setImageResource(m1);
//        imageView2.setImageResource(m2);
//        imageView3.setImageResource(m3);
        return rowView;

    };

}