package com.pbn.ct9.ui.personalColor;

import static java.lang.Double.valueOf;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pbn.ct9.AppStart;
import com.pbn.ct9.R;

public class Kcmnd extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kcmnd);

        String KTL = ((AppStart) this.getApplication()).getKTL();
        System.out.println(":KTLmmd!"+ KTL );
        final TextView viewKTL = (TextView) findViewById(R.id.text_view_KTL);
        viewKTL.setText(KTL);

        String KTa = ((AppStart) this.getApplication()).getKTa();
        final TextView viewKTa = (TextView) findViewById(R.id.text_view_KTa);
        viewKTa.setText(KTa);

        String KTb = ((AppStart) this.getApplication()).getKTb();
        final TextView viewKTb = (TextView) findViewById(R.id.text_view_KTb);
        viewKTb.setText(KTb);

        String KmL = ((AppStart) this.getApplication()).getKmL();
        System.out.println(":KmLcmmd!"+ KmL );
        final TextView viewKmL = (TextView) findViewById(R.id.text_view_kmL);
        viewKmL.setText(KmL);

        String Kma = ((AppStart) this.getApplication()).getKma();
        final TextView viewKma = (TextView) findViewById(R.id.text_view_kma);
        viewKma.setText(Kma);

        String Kmb = ((AppStart) this.getApplication()).getKmb();
        final TextView viewKmb = (TextView) findViewById(R.id.text_view_kmb);
        viewKmb.setText(Kmb);

        String Kmd = ((AppStart) this.getApplication()).getKmd();
        final TextView viewKmd = (TextView) findViewById(R.id.text_view_kmd);
        viewKmd.setText(Kmd);

        String KpL = ((AppStart) this.getApplication()).getKpL();
        System.out.println(":KmLmmmd!"+ KpL );
        final TextView viewKpL = (TextView) findViewById(R.id.text_view_kpL);
        viewKpL.setText(KpL);

        String Kpa = ((AppStart) this.getApplication()).getKpa();
        final TextView viewKpa = (TextView) findViewById(R.id.text_view_kpa);
        viewKpa.setText(Kpa);
        String Kpb = ((AppStart) this.getApplication()).getKpb();
        final TextView viewKpb = (TextView) findViewById(R.id.text_view_kpb);
        viewKpb.setText(Kpb);
        String Kpd = ((AppStart) this.getApplication()).getKpd();
        final TextView viewKpd = (TextView) findViewById(R.id.text_view_kpd);
        viewKpd.setText(Kpd);

        String Kpde = ((AppStart) this.getApplication()).getKpde();
        if (valueOf(Kpde)>3.5) {
            final TextView viewKpde = (TextView) findViewById(R.id.text_view_kpdeR);
            viewKpde.setText(Kpde);
        }
        else {
            final TextView viewKpde = (TextView) findViewById(R.id.text_view_kpde);
            viewKpde.setText(Kpde);
        }

        String Kmde = ((AppStart) this.getApplication()).getKmde();
        if (valueOf(Kmde)>3.5) {
            final TextView viewKmde = (TextView) findViewById(R.id.text_view_kmdeR);
            viewKmde.setText(Kmde);
        }
        else {
            final TextView viewKmde = (TextView) findViewById(R.id.text_view_kmde);
            viewKmde.setText(Kmde);
        }

        System.out.println(":Kpdmmd!"+ Kpd + ",Kmd:" + Kmd);

        String Kop = ((AppStart) this.getApplication()).getKop();
        final TextView viewKop = (TextView) findViewById(R.id.text_view_kop);
        viewKop.setText(Kop);

        String Ksn = ((AppStart) this.getApplication()).getKsn();
        final TextView viewKsn = (TextView) findViewById(R.id.text_view_ksn);
        viewKsn.setText(Ksn);


        //Bundle extras = getIntent().getExtras();

    }


}