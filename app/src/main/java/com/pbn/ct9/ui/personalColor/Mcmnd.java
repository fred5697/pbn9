package com.pbn.ct9.ui.personalColor;

import static java.lang.Double.valueOf;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pbn.ct9.AppStart;
import com.pbn.ct9.R;

public class Mcmnd extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcmnd);

        String MTL = ((AppStart) this.getApplication()).getMTL();
        System.out.println(":MTLmmd!"+ MTL );
        final TextView viewMTL = (TextView) findViewById(R.id.text_view_MTL);
        viewMTL.setText(MTL);

        String MTa = ((AppStart) this.getApplication()).getMTa();
        final TextView viewMTa = (TextView) findViewById(R.id.text_view_MTa);
        viewMTa.setText(MTa);

        String MTb = ((AppStart) this.getApplication()).getMTb();
        final TextView viewMTb = (TextView) findViewById(R.id.text_view_MTb);
        viewMTb.setText(MTb);

        String MmL = ((AppStart) this.getApplication()).getMmL();
        System.out.println(":MmLcmmd!"+ MmL );
        final TextView viewMmL = (TextView) findViewById(R.id.text_view_mmL);
        viewMmL.setText(MmL);

        String Mma = ((AppStart) this.getApplication()).getMma();
        final TextView viewMma = (TextView) findViewById(R.id.text_view_mma);
        viewMma.setText(Mma);

        String Mmb = ((AppStart) this.getApplication()).getMmb();
        final TextView viewMmb = (TextView) findViewById(R.id.text_view_mmb);
        viewMmb.setText(Mmb);

        String Mmd = ((AppStart) this.getApplication()).getMmd();
        final TextView viewMmd = (TextView) findViewById(R.id.text_view_mmd);
        viewMmd.setText(Mmd);

        String MpL = ((AppStart) this.getApplication()).getMpL();
        System.out.println(":MmLmmmd!"+ MpL );
        final TextView viewMpL = (TextView) findViewById(R.id.text_view_mpL);
        viewMpL.setText(MpL);

        String Mpa = ((AppStart) this.getApplication()).getMpa();
        final TextView viewMpa = (TextView) findViewById(R.id.text_view_mpa);
        viewMpa.setText(Mpa);
        String Mpb = ((AppStart) this.getApplication()).getMpb();
        final TextView viewMpb = (TextView) findViewById(R.id.text_view_mpb);
        viewMpb.setText(Mpb);
        String Mpd = ((AppStart) this.getApplication()).getMpd();
        final TextView viewMpd = (TextView) findViewById(R.id.text_view_mpd);
        viewMpd.setText(Mpd);



        String Mmde = ((AppStart) this.getApplication()).getMmde();
        if (valueOf(Mmde)>3.5) {
            final TextView viewMmde = (TextView) findViewById(R.id.text_view_mmdeR);
            viewMmde.setText(Mmde);
        }
        else {
            final TextView viewMmde = (TextView) findViewById(R.id.text_view_mmde);
            viewMmde.setText(Mmde);
        }



        String Mpde = ((AppStart) this.getApplication()).getMpde();
        if (valueOf(Mpde)>3.5) {
            final TextView viewMpde = (TextView) findViewById(R.id.text_view_mpdeR);
            viewMpde.setText(Mpde);
        }
        else {
            final TextView viewMpde = (TextView) findViewById(R.id.text_view_mpde);
            viewMpde.setText(Mpde);
        }



        System.out.println(":Mpdmmd!"+ Mpd + ",Mmd:" + Mmd);

        String Mop = ((AppStart) this.getApplication()).getMop();
        final TextView viewMop = (TextView) findViewById(R.id.text_view_mop);
        viewMop.setText(Mop);

        String Msn = ((AppStart) this.getApplication()).getMsn();
        final TextView viewMsn = (TextView) findViewById(R.id.text_view_msn);
        viewMsn.setText(Msn);


        //Bundle extras = getIntent().getExtras();

    }


}