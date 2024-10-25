package com.pbn.ct9.ui.personalColor;

import static java.lang.Double.valueOf;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pbn.ct9.AppStart;
import com.pbn.ct9.R;

public class Graymnd extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graymnd);

        String GTL = ((AppStart) this.getApplication()).getGTL();
        System.out.println(":GTLmmd!"+ GTL );
        final TextView viewGTL = (TextView) findViewById(R.id.text_view_gTL);
        viewGTL.setText(GTL);

        String GTa = ((AppStart) this.getApplication()).getGTa();
        final TextView viewGTa = (TextView) findViewById(R.id.text_view_gTa);
        viewGTa.setText(GTa);

        String GTb = ((AppStart) this.getApplication()).getGTb();
        final TextView viewGTb = (TextView) findViewById(R.id.text_view_gTb);
        viewGTb.setText(GTb);

        String GmL = ((AppStart) this.getApplication()).getGmL();
        System.out.println(":GmLcmmd!"+ GmL );
        final TextView viewGmL = (TextView) findViewById(R.id.text_view_gmL);
        viewGmL.setText(GmL);

        String Gma = ((AppStart) this.getApplication()).getGma();
        final TextView viewGma = (TextView) findViewById(R.id.text_view_gma);
        viewGma.setText(Gma);

        String Gmb = ((AppStart) this.getApplication()).getGmb();
        final TextView viewGmb = (TextView) findViewById(R.id.text_view_gmb);
        viewGmb.setText(Gmb);

        String Gmde = ((AppStart) this.getApplication()).getGmde();
        final TextView viewGmde = (TextView) findViewById(R.id.text_view_gmde);
        viewGmde.setText(Gmde);


        String Dea = ((AppStart) this.getApplication()).getDea();
        final TextView viewDea = (TextView) findViewById(R.id.text_view_dea);
        viewDea.setText(Dea);
        String Deb = ((AppStart) this.getApplication()).getDeb();
        final TextView viewDeb = (TextView) findViewById(R.id.text_view_deb);
        viewDeb.setText(Deb);

        String DeL = ((AppStart) this.getApplication()).getDeL();
        if (valueOf(DeL)>3.0 || valueOf(DeL)<-3) {
            final TextView viewDeL = (TextView) findViewById(R.id.text_view_deLR);
            viewDeL.setText(DeL);
        }
        else {
            final TextView viewDeL = (TextView) findViewById(R.id.text_view_deL);
            viewDeL.setText(DeL);
        }

        String Def = ((AppStart) this.getApplication()).getDef();
        if (valueOf(Def)>3.0) {
            final TextView viewDef = (TextView) findViewById(R.id.text_view_defR);
            viewDef.setText(Def);
        }
        else {
            final TextView viewDef = (TextView) findViewById(R.id.text_view_def);
            viewDef.setText(Def);
        }


        String Gcsn = ((AppStart) this.getApplication()).getGcsn();
        final TextView viewGcsn = (TextView) findViewById(R.id.text_view_gcsn);
        viewGcsn.setText(Gcsn);

        String Gcop = ((AppStart) this.getApplication()).getGcop();
        final TextView viewGcop = (TextView) findViewById(R.id.text_view_gcop);
        viewGcop.setText(Gcop);

        String Gmsn = ((AppStart) this.getApplication()).getGmsn();
        final TextView viewGmsn = (TextView) findViewById(R.id.text_view_gmsn);
        viewGmsn.setText(Gmsn);

        System.out.println(":Gmsn!"+ Gcop + ",Dea:" + Dea);

        String Gmop = ((AppStart) this.getApplication()).getGmop();
        final TextView viewGmop = (TextView) findViewById(R.id.text_view_gmop);
        viewGmop.setText(Gmop);

        String Gysn = ((AppStart) this.getApplication()).getGysn();
        final TextView viewGysn = (TextView) findViewById(R.id.text_view_gysn);
        viewGysn.setText(Gysn);
        String Gyop = ((AppStart) this.getApplication()).getGyop();
        final TextView viewGyop = (TextView) findViewById(R.id.text_view_gyop);
        viewGyop.setText(Gyop);


        //Bundle extras = getIntent().getExtras();

    }


}