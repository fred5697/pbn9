package com.pbn.ct9.ui.personalColor;

import static java.lang.Double.valueOf;
import static java.lang.Math.abs;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;

import com.pbn.ct9.AppStart;
import com.pbn.ct9.R;

import java.text.NumberFormat;
import java.text.ParseException;

public class Ccmnd extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccmnd);

        String CTL = ((AppStart) this.getApplication()).getCTL();
        System.out.println(":CTLmmd!"+ CTL );
        final TextView viewCTL = (TextView) findViewById(R.id.text_view_CTL);
        viewCTL.setText(CTL);

        String CTa = ((AppStart) this.getApplication()).getCTa();
        final TextView viewCTa = (TextView) findViewById(R.id.text_view_CTa);
        viewCTa.setText(CTa);

        String CTb = ((AppStart) this.getApplication()).getCTb();
        final TextView viewCTb = (TextView) findViewById(R.id.text_view_CTb);
        viewCTb.setText(CTb);

        String CmL = ((AppStart) this.getApplication()).getCmL();
        System.out.println(":CmLcmmd!"+ CmL );
        final TextView viewCmL = (TextView) findViewById(R.id.text_view_cmL);
        viewCmL.setText(CmL);

        String Cma = ((AppStart) this.getApplication()).getCma();
        final TextView viewCma = (TextView) findViewById(R.id.text_view_cma);
        viewCma.setText(Cma);

        String Cmb = ((AppStart) this.getApplication()).getCmb();
        final TextView viewCmb = (TextView) findViewById(R.id.text_view_cmb);
        viewCmb.setText(Cmb);

        String Cmd = ((AppStart) this.getApplication()).getCmd();
        final TextView viewCmd = (TextView) findViewById(R.id.text_view_cmd);
        viewCmd.setText(Cmd);

        String CpL = ((AppStart) this.getApplication()).getCpL();
        System.out.println(":CmLcmmd!"+ CpL );
        final TextView viewCpL = (TextView) findViewById(R.id.text_view_cpL);
        viewCpL.setText(CpL);

        String Cpa = ((AppStart) this.getApplication()).getCpa();
        final TextView viewCpa = (TextView) findViewById(R.id.text_view_cpa);
        viewCpa.setText(Cpa);
        String Cpb = ((AppStart) this.getApplication()).getCpb();
        final TextView viewCpb = (TextView) findViewById(R.id.text_view_cpb);
        viewCpb.setText(Cpb);
        String Cpd = ((AppStart) this.getApplication()).getCpd();
        final TextView viewCpd = (TextView) findViewById(R.id.text_view_cpd);
        viewCpd.setText(Cpd);

        String Cpde = ((AppStart) this.getApplication()).getCpde();
        if (valueOf(Cpde)>3.5) {
            final TextView viewCpde = (TextView) findViewById(R.id.text_view_cpdeR);
            viewCpde.setText(Cpde);
        }
        else {
            final TextView viewCpde = (TextView) findViewById(R.id.text_view_cpde);
            viewCpde.setText(Cpde);
        }

        String Cmde = ((AppStart) this.getApplication()).getCmde();
        if (valueOf(Cmde)>3.5) {
        final TextView viewCmde = (TextView) findViewById(R.id.text_view_cmdeR);
            viewCmde.setText(Cmde);
        }
        else {
            final TextView viewCmde = (TextView) findViewById(R.id.text_view_cmde);
            viewCmde.setText(Cmde);
        }


        System.out.println(":Cpdmmd!"+ Cpd + ",Cmd:" + Cmd);

        String Cop = ((AppStart) this.getApplication()).getCop();
        final TextView viewCop = (TextView) findViewById(R.id.text_view_cop);
        viewCop.setText(Cop);

        String Csn = ((AppStart) this.getApplication()).getCsn();
        final TextView viewCsn = (TextView) findViewById(R.id.text_view_csn);
        viewCsn.setText(Csn);


        //Bundle extras = getIntent().getExtras();

    }


}