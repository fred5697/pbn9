package com.pbn.ct9.ui.personalColor;

import static java.lang.Double.valueOf;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pbn.ct9.AppStart;
import com.pbn.ct9.R;

public class Ycmnd extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ycmnd);

        String YTL = ((AppStart) this.getApplication()).getYTL();
        System.out.println(":YTLmmd!"+ YTL );
        final TextView viewYTL = (TextView) findViewById(R.id.text_view_YTL);
        viewYTL.setText(YTL);

        String YTa = ((AppStart) this.getApplication()).getYTa();
        final TextView viewYTa = (TextView) findViewById(R.id.text_view_YTa);
        viewYTa.setText(YTa);

        String YTb = ((AppStart) this.getApplication()).getYTb();
        final TextView viewYTb = (TextView) findViewById(R.id.text_view_YTb);
        viewYTb.setText(YTb);

        String YmL = ((AppStart) this.getApplication()).getYmL();
        System.out.println(":YmLcmmd!"+ YmL );
        final TextView viewYmL = (TextView) findViewById(R.id.text_view_ymL);
        viewYmL.setText(YmL);

        String Yma = ((AppStart) this.getApplication()).getYma();
        final TextView viewYma = (TextView) findViewById(R.id.text_view_yma);
        viewYma.setText(Yma);

        String Ymb = ((AppStart) this.getApplication()).getYmb();
        final TextView viewYmb = (TextView) findViewById(R.id.text_view_ymb);
        viewYmb.setText(Ymb);

        String Ymd = ((AppStart) this.getApplication()).getYmd();
        final TextView viewYmd = (TextView) findViewById(R.id.text_view_ymd);
        viewYmd.setText(Ymd);

        String YpL = ((AppStart) this.getApplication()).getYpL();
        System.out.println(":YmLmmmd!"+ YpL );
        final TextView viewYpL = (TextView) findViewById(R.id.text_view_ypL);
        viewYpL.setText(YpL);

        String Ypa = ((AppStart) this.getApplication()).getYpa();
        final TextView viewYpa = (TextView) findViewById(R.id.text_view_ypa);
        viewYpa.setText(Ypa);
        String Ypb = ((AppStart) this.getApplication()).getYpb();
        final TextView viewYpb = (TextView) findViewById(R.id.text_view_ypb);
        viewYpb.setText(Ypb);
        String Ypd = ((AppStart) this.getApplication()).getYpd();
        final TextView viewYpd = (TextView) findViewById(R.id.text_view_ypd);
        viewYpd.setText(Ypd);

        String Ypde = ((AppStart) this.getApplication()).getYpde();
        if (valueOf(Ypde)>3.5) {
            final TextView viewYpde = (TextView) findViewById(R.id.text_view_ypdeR);
            viewYpde.setText(Ypde);
        }
        else {
            final TextView viewYpde = (TextView) findViewById(R.id.text_view_ypde);
            viewYpde.setText(Ypde);
        }

        String Ymde = ((AppStart) this.getApplication()).getYmde();
        if (valueOf(Ymde)>3.5) {
            final TextView viewYmde = (TextView) findViewById(R.id.text_view_ymdeR);
            viewYmde.setText(Ymde);
        }
        else {
            final TextView viewYmde = (TextView) findViewById(R.id.text_view_ymde);
            viewYmde.setText(Ymde);
        }



        System.out.println(":Ypdmmd!"+ Ypd + ",Ymd:" + Ymd);

        String Yop = ((AppStart) this.getApplication()).getYop();
        final TextView viewYop = (TextView) findViewById(R.id.text_view_yop);
        viewYop.setText(Yop);

        String Ysn = ((AppStart) this.getApplication()).getYsn();
        final TextView viewYsn = (TextView) findViewById(R.id.text_view_ysn);
        viewYsn.setText(Ysn);


        //Bundle extras = getIntent().getExtras();

    }


}