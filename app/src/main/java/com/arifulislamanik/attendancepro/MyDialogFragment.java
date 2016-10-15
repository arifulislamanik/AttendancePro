package com.arifulislamanik.attendancepro;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample_dialog, container, false);
        getDialog().setTitle("Dates");
        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        String attendance = "";
        TextView tv = (TextView) rootView.findViewById(R.id.title);
        att_form at = new att_form();
//
        int i=0;
        System.out.println(at.q);
        //System.out.println(at.disp.length);
        for(i=0;i<at.q;i++)
        {

            System.out.println(at.q);
            attendance+=at.disp[i];
            at.disp[i]="";
            attendance=attendance+"\n";

        }
        tv.setText(attendance);
        at.q=0;

        return rootView;

    }
}