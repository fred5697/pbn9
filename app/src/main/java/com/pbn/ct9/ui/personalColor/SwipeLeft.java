package com.pbn.ct9.ui.personalColor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pbn.ct9.AppStart;
import com.pbn.ct9.R;

public class SwipeLeft extends AppCompatActivity {
    public final ObservableField<String> m_bt3_Value = new ObservableField<>("-");
 /*   public final String firstName;


    public SwipeLeft(String firstName) {
            this.firstName = firstName;
        }
    public String getFirstName() {
        return this.firstName;
    }
*/
 private RadioGroup mRadioGroup;
    private RadioButton mRadioButton0,
            mRadioButton1,
            mRadioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String some = ((AppStart) this.getApplication()).getBTVariable();
        System.out.println(":SwipeVariable!"+ some );
        String sessionId = getIntent().getStringExtra("my_variable");
        System.out.println("my_variable:" + some);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_left);

        mRadioButton0 = (RadioButton) findViewById(R.id.mRadioButton0);
        mRadioButton1 = (RadioButton) findViewById(R.id.mRadioButton1);
        mRadioButton2 = (RadioButton) findViewById(R.id.mRadioButton2);


        RadioGroup mRadGrpRegion = (RadioGroup) findViewById(R.id.mRadioGroup);
        mRadGrpRegion.check(R.id.mRadioButton0); //設定 mRadioButton0 選項為選取狀態
        mRadGrpRegion.setOnCheckedChangeListener(radGrpRegionOnCheckedChange); //設定單選選項監聽器


        final TextView helloTextView = (TextView) findViewById(R.id.text_view_id);
        helloTextView.setText(some);

        Bundle extras = getIntent().getExtras();
        m_bt3_Value.set("google");

        //String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");

        //String valueZ = extras.getString("my_variable");
        System.out.println("m_bt3_Valuex:" + helloTextView);


        m_bt3_Value.set(some);
        System.out.println("m_bt3_Valuex2:" + m_bt3_Value.get());
        //System.out.println("my_variable2:" + valueZ);
        //setContentView(R.layout.activity_swipe_left);
    }

    private RadioGroup.OnCheckedChangeListener radGrpRegionOnCheckedChange =
            new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    // TODO Auto-generated method stub
                    String str = getString(R.string.select_region);

                    switch (checkedId)
                    {
                        case R.id.mRadioButton0: //case mRadioButton0.getId():
                            System.out.println("case button 1!" );
                            break;

                        case R.id.mRadioButton1: //case mRadioButton1.getId():
                            System.out.println("case button 2!" );
                            break;

                        case R.id.mRadioButton2: //case mRadioButton2.getId():
                            System.out.println("case button 3!" );
                            break;


                    }
                }
            };
}