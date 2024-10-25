package com.pbn.ct9.ui.personalColor;

import static java.lang.Math.abs;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.pbn.ct9.AppStart;
import com.pbn.ct9.MainActivity;
import com.pbn.ct9.R;
import com.pbn.ct9.bean.MeasureBean;
import com.pbn.ct9.bean.ReadMeasureDataBean;
import com.pbn.ct9.bean.ReadRgbMeasureDataBean;
import com.pbn.ct9.ble.IBluetoothManager;
import com.pbn.ct9.broadcast.SpectrometerBroadcast;
import com.pbn.ct9.databinding.FragmentPersonalColorBinding;
import com.pbn.ct9.ui.memberCenter.BleDeviceSearch;
import com.pbn.ct9.utils.Constant;
import com.pbn.ct9.utils.MessageDialogUtil;
import com.pbn.ct9.utils.WaitDialogUtil;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



public class PersonalColorFragment extends Fragment implements View.OnClickListener
{
	public PersonalColorFragment() {
		// Required empty public constructor
	}

	private FragmentPersonalColorBinding binding;
	private SpectrometerBroadcast mSpectrometerBroadcast = null;

	public final ObservableField<String> m_bt3_Value = new ObservableField<>("-");
	public final ObservableField<String> m_Lp_Value = new ObservableField<>("-");
	public final ObservableField<String> m_ap_Value = new ObservableField<>("-");
	public final ObservableField<String> m_bp_Value = new ObservableField<>("-");
	public final ObservableField<String> m_score_Value = new ObservableField<>("-");


	public double mpL = 0;
	public double mpa = 0;
	public double mpb = 0;

	public double SCTL = 55;
	public double SCTa = -37;
	public double SCTb = -50;

	public double SMTL = 48;
	public double SMTa = 74;
	public double SMTb = -3;

	public double SYTL = 89;
	public double SYTa = -5;
	public double SYTb = 93;

	public double SKTL = 16;
	public double SKTa = 0;
	public double SKTb = 0;

	public double TC50 = 64;
	public double TM50 = 64;
	public double TY50 = 64;
	public double TK50 = 67;
	public double dl;
	public double df;
	public double dlT;
	public double dfT;
	public double cLm;
	public double cam;
	public double cbm;
	public double mLm;
	public double mam;
	public double mbm;
	public double yLm;
	public double yam;
	public double ybm;
	public double kLm;
	public double kam;
	public double kbm;

	public double cTm;
	public double yTm;
	public double mTm;
	public double kTm;

	public final ObservableField<String> m_DeC_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DeM_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DeY_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DeK_Value = new ObservableField<>("-");


	public final ObservableField<String> m_DeCbg_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DeMbg_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DeYbg_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DeKbg_Value = new ObservableField<>("-");

	public final ObservableField<String> m_Denp_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenC_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenM_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenY_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenK_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenC50_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenM50_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenY50_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenK50_Value = new ObservableField<>("-");
	public final ObservableField<String> m_Dengray_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenA_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenS_Value = new ObservableField<>("-");
	public final ObservableField<String> m_BestDen_Value = new ObservableField<>("-");
	public final ObservableField<String> m_BestDe00_Value = new ObservableField<>("-");

	public final ObservableField<String> m_L1_Value = new ObservableField<>("-");
	public final ObservableField<String> m_a1_Value = new ObservableField<>("-");
	public final ObservableField<String> m_b1_Value = new ObservableField<>("-");
	public final ObservableField<String> m_R_Value = new ObservableField<>("-");
	public final ObservableField<String> m_G_Value = new ObservableField<>("-");
	public final ObservableField<String> m_B_Value = new ObservableField<>("-");

	public final ObservableField<String> m_L2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_a2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_b2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_R2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_G2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_B2_Value = new ObservableField<>("-");

	public final ObservableField<String> m_c100L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_c100a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_c100b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_m100L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_m100a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_m100b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_y100L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_y100a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_y100b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_k100L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_k100a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_k100b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_c50L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_c50a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_c50b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_m50L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_m50a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_m50b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_y50L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_y50a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_y50b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_k50L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_k50a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_k50b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_grayL_Value = new ObservableField<>("-");
	public final ObservableField<String> m_graya_Value = new ObservableField<>("-");
	public final ObservableField<String> m_grayb_Value = new ObservableField<>("-");


	public final ObservableField<String> m_hidex_Value = new ObservableField<>("-");

	public double m2L = 0;
	public double m2a = 0;
	public double m2b = 0;

	public double m1L = 0;
	public double m1a = 0;
	public double m1b = 0;



	public double mp0 = 0;public double mp1 = 0;public double mp2 = 0;public double mp3 = 0;public double mp4 = 0;public double mp5 = 0;public double mp6 = 0;public double mp7 = 0;public double mp8 = 0;public double mp9 = 0;
	public double mp10 = 0;public double mp11 = 0;public double mp12 = 0;public double mp13 = 0;public double mp14 = 0;public double mp15 = 0;public double mp16 = 0;public double mp17 = 0;public double mp18 = 0;public double mp19 = 0;
	public double mp20 = 0;public double mp21 = 0;public double mp22 = 0;public double mp23 = 0;public double mp24 = 0;public double mp25 = 0;public double mp26 = 0;public double mp27 = 0;public double mp28 = 0;public double mp29 = 0;
	public double mp30 = 0;

	public final ObservableField<String> m_dE00_Value = new ObservableField<>("-");

	public ObservableInt m_rgbColorInt = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntP = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntC = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntM = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntY = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntK = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntC50 = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntM50 = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntY50 = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntK50 = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorIntgray = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorInt2 = new ObservableInt(Color.rgb(255, 255, 255));
	public int fixFlag;
	public final ObservableField<String> m_fix_Value = new ObservableField<>("-");
	public final ObservableField<String> m_TV_Value = new ObservableField<>("-");
	public final ObservableField<String> m_TVC_Value = new ObservableField<>("-");
	public final ObservableField<String> m_TVM_Value = new ObservableField<>("-");
	public final ObservableField<String> m_TVY_Value = new ObservableField<>("-");
	public final ObservableField<String> m_TVK_Value = new ObservableField<>("-");
	public double SolidC;
	public double SolidM;
	public double SolidY;
	public double SolidK;
	public double SolidS;
	public double pdm;
	public double DenC50;
	public double DenM50;
	public double DenY50;
	public double DenK50;
	public int flagC;
	public int flagM;
	public int flagY;
	public int flagK;
	public int flagS;
	public double c50TV;
	public double m50TV;
	public double y50TV;
	public double k50TV;
	public double s50TV;
	public double tgl;
	public double tga;
	public double tgb;
	public double DenxC;
	public double DenxM;
	public double DenxY;
	//public double DenxS;
	public double Denx;
	public double Denxg;
	public double PXm;
	public double PYm;
	public double PZm;
	public double cX;
	public double cY;
	public double cZ;
	public double SXm;
	public double SYm;
	public double SZm;
	public double dE00;
	public double spoth1;
	public double spoth2;
	double chrome2 ;
	double hue;
	double deg ;
	public int flagSTV=0;

	public final ObservableField<String> m_dl_Value = new ObservableField<>("-");
	public final ObservableField<String> m_da_Value = new ObservableField<>("-");
	public final ObservableField<String> m_db_Value = new ObservableField<>("-");
	public final ObservableField<String> m_df_Value = new ObservableField<>("-");
	public final ObservableField<String> m_cf_Value = new ObservableField<>("-");
	public final ObservableField<String> m_mf_Value = new ObservableField<>("-");
	public final ObservableField<String> m_yf_Value = new ObservableField<>("-");

	double score = 120;
	double dec;
	double dem;
	double dey;
	double dek;
	double del;
	double def;

	double deTVc;
	double deTVm;
	double deTVy;
	double deTVk;
	String target="Fogra39";
	String cmyFilter;
	double cop;
	double mop;
	double yop;
	double kop;

	double gmL;
	double gma;
	double gmb;



	public String mp="";
	public String cmp="";
	public String mmp="";
	public String ymp="";

	private RadioGroup mRadioGroup;
	private RadioButton mRadioButton0,
			mRadioButton1,
			mRadioButton2;




	private ReadMeasureDataBean mReadMeasureDataBean;
/*
	private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
				case R.id.mRadioButton0:
					// Write your code here
					System.out.println("case button 1z!");
					break;
				case R.id.mRadioButton1:
					System.out.println("case button 2z1!");
					// Write your code here
					break;
				case R.id.mRadioButton2:
					System.out.println("case button 3");
					// Write your code here
					break;


			}
		}

	};

*/
	public View onCreateView(@NonNull LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {




		binding = FragmentPersonalColorBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		root.findViewById(R.id.mRadioButton0).setOnClickListener(this);
		root.findViewById(R.id.mRadioButton1).setOnClickListener(this);
		root.findViewById(R.id.mRadioButton2).setOnClickListener(this);



		binding.setFragment(this);

		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			//MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), "先測量紙張！");
			final DecimalFormat format = new DecimalFormat();
			format.setMaximumFractionDigits(2);
			mSpectrometerBroadcast = new SpectrometerBroadcast(requireActivity(),
					new SpectrometerBroadcast.onBroadcastReceived() {
						@Override
						public void onMeasure(MeasureBean data) {
							//Log.d("PersonalColorFragment", "onReadMeasureData: " + data.toString());

							if (data.getMeasureSuccesses())
								IBluetoothManager.getInstance().setOrder(Constant.READ_MEASURE_DATA);
							else {
								mReadMeasureDataBean = null;
								MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), getString(R.string.measure_fail));
							}
						}

						@RequiresApi(api = Build.VERSION_CODES.N)
						@Override public void onReadMeasureData(ReadMeasureDataBean data) {
							//Log.d("HomeFragment", "onReadMeasureData: " + data.toString());

							if (data.getLabData() != null) {
								mReadMeasureDataBean = data;

								double DenxS = densS(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
								double DenxHi = densHi(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);


								double D50X = spXYZ("X", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
								double D50Y = spXYZ("Y", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
								double D50Z = spXYZ("Z", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
								double D50L = xyz2lab01("l", D50X, D50Y, D50Z);
								double D50a = xyz2lab01("a", D50X, D50Y, D50Z);
								double D50b = xyz2lab01("b", D50X, D50Y, D50Z);

								System.out.println("xyz-1:"+ D50X + ","+ D50Y + ","+ D50Z );
								System.out.println("lab-1:"+ D50L + ","+ D50a + ","+ D50b );



								double smartK = data.getData()[27]/data.getData()[17];


								if (mBtn == "measureTargetColorp") {
									m_Lp_Value.set(String.valueOf(D50L));
									m_ap_Value.set(String.valueOf(D50a));
									m_bp_Value.set(String.valueOf(D50b));
									fixFlag = 0;
									m_fix_Value.set(String.valueOf(""));
									mpL = D50L;
									mpa = D50a;
									mpb = D50b;
									m1L = 0;
									m1a = 0;
									m1b = 0;
									m2L = 0;
									m2a = 0;
									m2b = 0;
									Denx = dens(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);

									m_Denp_Value.set(String.valueOf(Denx));
									double[] spPaperArray = {data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30]};
									mp0 = data.getData()[0];
									mp1 = data.getData()[1];
									mp2 = data.getData()[2];
									mp3 = data.getData()[3];
									mp4 = data.getData()[4];
									mp5 = data.getData()[5];
									mp6 = data.getData()[6];
									mp7 = data.getData()[7];
									mp8 = data.getData()[8];
									mp9 = data.getData()[9];
									mp10 = data.getData()[10];
									mp11 = data.getData()[11];
									mp12 = data.getData()[12];
									mp13 = data.getData()[13];
									mp14 = data.getData()[14];
									mp15 = data.getData()[15];
									mp16 = data.getData()[16];
									mp17 = data.getData()[17];
									mp18 = data.getData()[18];
									mp19 = data.getData()[19];
									mp20 = data.getData()[20];
									mp21 = data.getData()[21];
									mp22 = data.getData()[22];
									mp23 = data.getData()[23];
									mp24 = data.getData()[24];
									mp25 = data.getData()[25];
									mp26 = data.getData()[26];
									mp27 = data.getData()[27];
									mp28 = data.getData()[28];
									mp29 = data.getData()[29];
									mp30 = data.getData()[30];
									pdm=Denx;
									SolidC=0;
									SolidM=0;
									SolidY=0;
									SolidK=0;
									SolidS=0;
									m_TV_Value.set(String.valueOf(0));
									c50TV=0;
									m50TV=0;
									y50TV=0;
									k50TV=0;
									s50TV=0;
									tgl=58;
									tga=mpa/2;
									tgb=mpb/2;
									flagC = 0;
									flagM = 0;
									flagY = 0;
									flagK= 0;
									flagS= 0;
									PXm = D50X;
									PYm = D50Y;
									PZm = D50Z;
									spoth1=0;
									spoth2=0;
									flagSTV = 0;
									DenxHi = 0;

									//m_Denp_Value.set(String.valueOf(0));
									m_DenC_Value.set(String.valueOf(0));
									m_DenM_Value.set(String.valueOf(0));
									m_DenY_Value.set(String.valueOf(0));
									m_DenK_Value.set(String.valueOf(0));
									m_DenA_Value.set(String.valueOf(0));
									m_DenS_Value.set(String.valueOf(0));
									m_BestDen_Value.set(String.valueOf(0));
									m_BestDe00_Value.set(String.valueOf(0));
									m_hidex_Value.set(String.valueOf(0));

									m_c100L_Value.set(String.valueOf(0));
									m_c100a_Value.set(String.valueOf(0));
									m_c100b_Value.set(String.valueOf(0));
									m_m100L_Value.set(String.valueOf(0));
									m_m100a_Value.set(String.valueOf(0));
									m_m100b_Value.set(String.valueOf(0));
									m_y100L_Value.set(String.valueOf(0));
									m_y100a_Value.set(String.valueOf(0));
									m_y100b_Value.set(String.valueOf(0));
									m_k100L_Value.set(String.valueOf(0));
									m_k100a_Value.set(String.valueOf(0));
									m_k100b_Value.set(String.valueOf(0));

									m_c50L_Value.set(String.valueOf(0));
									m_c50a_Value.set(String.valueOf(0));
									m_c50b_Value.set(String.valueOf(0));
									m_m50L_Value.set(String.valueOf(0));
									m_m50a_Value.set(String.valueOf(0));
									m_m50b_Value.set(String.valueOf(0));
									m_y50L_Value.set(String.valueOf(0));
									m_y50a_Value.set(String.valueOf(0));
									m_y50b_Value.set(String.valueOf(0));
									m_k50L_Value.set(String.valueOf(0));
									m_k50a_Value.set(String.valueOf(0));
									m_k50b_Value.set(String.valueOf(0));
									m_grayL_Value.set(String.valueOf(0));
									m_graya_Value.set(String.valueOf(0));
									m_grayb_Value.set(String.valueOf(0));

									m_TVC_Value.set(String.valueOf(0));
									m_TVM_Value.set(String.valueOf(0));
									m_TVY_Value.set(String.valueOf(0));
									m_TVK_Value.set(String.valueOf(0));

									//m_cop_Value.set(String.valueOf(0));
									m_TVM_Value.set(String.valueOf(0));
									m_TVY_Value.set(String.valueOf(0));
									m_TVK_Value.set(String.valueOf(0));
									m_score_Value.set(String.valueOf(0));

									m_DenC50_Value.set(String.valueOf(0));
									m_DenM50_Value.set(String.valueOf(0));
									m_DenY50_Value.set(String.valueOf(0));
									m_DenK50_Value.set(String.valueOf(0));
									m_Dengray_Value.set(String.valueOf(0));

									m_DenA_Value.set(String.valueOf(0));
									m_dE00_Value.set(String.valueOf(0));
									m_BestDen_Value.set(String.valueOf(0));
									m_BestDe00_Value.set(String.valueOf(0));
									dE00 = 0;

									m_L2_Value.set(String.valueOf(0));
									m_a2_Value.set(String.valueOf(0));
									m_b2_Value.set(String.valueOf(0));
									m_R2_Value.set(String.valueOf(0));
									m_G2_Value.set(String.valueOf(0));
									m_B2_Value.set(String.valueOf(0));

									m_cf_Value.set(String.valueOf(0));
									m_mf_Value.set(String.valueOf(0));
									m_yf_Value.set(String.valueOf(0));
									m_dl_Value.set(String.valueOf(0));
									m_db_Value.set(String.valueOf(0));
									m_da_Value.set(String.valueOf(0));
									m_df_Value.set(String.valueOf(0));


									//m_rgbColorInt.set(Color.rgb(255, 255, 255));
									//m_rgbColorIntP.set(Color.rgb(255, 255, 255));
									m_rgbColorIntC.set(Color.rgb(255, 255, 255));
									m_rgbColorIntM.set(Color.rgb(255, 255, 255));
									m_rgbColorIntY.set(Color.rgb(255, 255, 255));
									m_rgbColorIntK.set(Color.rgb(255, 255, 255));
									m_rgbColorIntC50.set(Color.rgb(255, 255, 255));
									m_rgbColorIntM50.set(Color.rgb(255, 255, 255));
									m_rgbColorIntY50.set(Color.rgb(255, 255, 255));
									m_rgbColorIntK50.set(Color.rgb(255, 255, 255));
									m_rgbColorIntgray.set(Color.rgb(255, 255, 255));
									//m_rgbColorInt2 = new ObservableInt(Color.rgb(255, 255, 255));

									//System.out.println("paper array 6 is : " + spPaperArray[6]);

									int intColor = ColorUtils.LABToColor(data.getLabData()[0], data.getLabData()[1], data.getLabData()[2]);

									int red = Color.red(intColor);
									int green = Color.green(intColor);
									int blue = Color.blue(intColor);
									m_R_Value.set(String.valueOf(red));
									m_G_Value.set(String.valueOf(green));
									m_B_Value.set(String.valueOf(blue));
									m_rgbColorIntP.set(Color.rgb(red, green, blue));
									//m_rgbColorIntP.set(Color.rgb(255, 255, 255));
									//m_rgbColorInt.set(Color.rgb(255, 255, 255));
									//m_rgbColorInt2.  set(Color.rgb(255, 255, 255));

								}
								else if (mBtn == "measureTargetreset") {

									m_k100L_Value.set(String.valueOf(0));
									m_k100a_Value.set(String.valueOf(0));
									m_k100b_Value.set(String.valueOf(0));
									System.out.println("::reset!");
								} //color1
								else if (mBtn == "measureTargetColor3") {
									System.out.println("lab3:line 434" );
									m_bt3_Value.set("button3");
									System.out.println("button 3x :" );
									//Intent intent = new Intent(getActivity(), SwipeLeft.class);
									//intent.putExtra("my_variable",m_bt3_Value.get());
									//startActivity(intent);

								}
								else if (mBtn == "measureTargetColor2") {

									m_L2_Value.set(String.valueOf(D50L));
									m_a2_Value.set(String.valueOf(D50a));
									m_b2_Value.set(String.valueOf(D50b));

									System.out.println("::m_L2_Value on 445!" + m_L2_Value);
									System.out.println("::btn2 on 445!" );



									m2L = D50L;
									m2a = D50a;
									m2b = D50b;

									System.out.println("lab2:"+ m2L + ","+ m2a + ","+ m2b );
									//System.out.println("paper array 6 is : " + mp6);

									double chrome2 = pow((pow(m2a, 2) + pow(m2b, 2)), 0.5);
									double hue = Math.atan2(m2b, m2a);
									double deg = (hue / Math.PI * 180) + (hue > 0 ? 0 : 360);
									System.out.println("deg is : " + deg);
									System.out.println("chrome is : " + chrome2);
									System.out.println("dot 1 is : " + data.getData()[1]);
									System.out.println("dot 15 is : " + data.getData()[15]);
									System.out.println("dot 27 is : " + data.getData()[27]);
									cmyFilter = "";

									if (deg > 218 & deg < 248 & chrome2 > 50 & fixFlag == 0) { //c100
										DenxC = densC(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);

										m_DenC_Value.set(String.valueOf(DenxC));
										m_DenA_Value.set(String.valueOf(DenxC));
										System.out.println("filter is C:"+m_DenC_Value.get());

										m1L = SCTL;
										m1a = SCTa;
										m1b = SCTb;

										cLm = m2L;
										cam = m2a;
										cbm = m2b;

										((AppStart) getActivity().getApplication()).setCTL(Double.toString(SCTL));
										//String CTL = ((AppStart) getActivity().getApplication()).getCTL();
										//System.out.println(":CTL!"+ CTL );
										((AppStart) getActivity().getApplication()).setCTa(Double.toString(SCTa));
										//String CTa = ((AppStart) getActivity().getApplication()).getCTa();
										((AppStart) getActivity().getApplication()).setCTb(Double.toString(SCTb));
										//String CTb = ((AppStart) getActivity().getApplication()).getCTb();

										((AppStart) getActivity().getApplication()).setCmL(Double.toString(cLm));
										String CmL = ((AppStart) getActivity().getApplication()).getCmL();
										//System.out.println(":CmL!"+ CmL );
										((AppStart) getActivity().getApplication()).setCma(Double.toString(cam));
										String Cma = ((AppStart) getActivity().getApplication()).getCma();
										((AppStart) getActivity().getApplication()).setCmb(Double.toString(cbm));
										String Cmb = ((AppStart) getActivity().getApplication()).getCmb();



										m_c100L_Value.set(String.valueOf(m2L));
										m_c100a_Value.set(String.valueOf(m2a));
										m_c100b_Value.set(String.valueOf(m2b));

										dec = de00(m1L, m1a, m1b, m2L, m2a, m2b);
										m_DeC_Value.set(String.valueOf(dec));
										//m_score_Value.set(String.valueOf(score));
										m_rgbColorIntC.set(Color.rgb(0, 153, 218));
										cmyFilter = "C";
										SolidC = DenxC;
										((AppStart) getActivity().getApplication()).setCmd(Double.toString(SolidC));
										String Cmd = ((AppStart) getActivity().getApplication()).getCmd();
										((AppStart) getActivity().getApplication()).setCmde(Double.toString(dec));
										String Cmde = ((AppStart) getActivity().getApplication()).getCmde();
										flagC = 1 ;

									}

									else if (deg > 218 & deg < 248 & chrome2 < 50 & chrome2 > 9 & fixFlag == 0 & flagC == 1) { //c50
										//m_DenM_Value.set(String.valueOf(DenxM));
										DenxC = densC(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										m_DenA_Value.set(String.valueOf(DenxC));
										System.out.println("filter is C50");
										m1L = 76;
										m1a = -16;
										m1b = -27;
										m_c50L_Value.set(String.valueOf(m2L));
										m_c50a_Value.set(String.valueOf(m2a));
										m_c50b_Value.set(String.valueOf(m2b));
										m_DenC50_Value.set(String.valueOf(DenxC));
										m_rgbColorIntC50.set(Color.rgb(147, 195, 233));
										cmyFilter = "C";
										DenC50 = DenxC;
										System.out.println("Solid C is "+ SolidC);
										System.out.println("TV C is "+ DenC50);
										System.out.println("paper C is "+ pdm);
										c50TV=(((1-(pow(10,(-(DenC50-pdm)))))/(1-(pow(10,(-(SolidC-pdm)))))))*100;
										c50TV=Math.round(c50TV * 100d) / 100d;
										m_TVC_Value.set(String.valueOf(c50TV));
										deTVc = abs(TC50-c50TV);
										cTm = c50TV;
										flagC = 0 ;
									}
									else if ((deg > 342 || deg < 12) & chrome2 > 60 & fixFlag == 0) {//M100
										DenxM = densM(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);

										m_DenM_Value.set(String.valueOf(DenxM));
										m_DenA_Value.set(String.valueOf(DenxM));
										System.out.println("filter is M");
										m1L = SMTL;
										m1a = SMTa;
										m1b = SMTb;
										mLm = m2L;
										mam = m2a;
										mbm = m2b;

										((AppStart) getActivity().getApplication()).setMTL(Double.toString(SMTL));
										String MTL = ((AppStart) getActivity().getApplication()).getMTL();
										System.out.println(":MTL!"+ MTL );
										((AppStart) getActivity().getApplication()).setMTa(Double.toString(SMTa));
										//String MTa = ((AppStart) getActivity().getApplication()).getMTa();
										((AppStart) getActivity().getApplication()).setMTb(Double.toString(SMTb));

										((AppStart) getActivity().getApplication()).setMmL(Double.toString(mLm));
										String MmL = ((AppStart) getActivity().getApplication()).getMmL();
										System.out.println(":MmL!"+ MmL );
										((AppStart) getActivity().getApplication()).setMma(Double.toString(mam));
										String Mma = ((AppStart) getActivity().getApplication()).getMma();
										((AppStart) getActivity().getApplication()).setMmb(Double.toString(mbm));
										String Mmb = ((AppStart) getActivity().getApplication()).getMmb();


										m_m100L_Value.set(String.valueOf(m2L));
										m_m100a_Value.set(String.valueOf(m2a));
										m_m100b_Value.set(String.valueOf(m2b));
										dem = de00(m1L, m1a, m1b, m2L, m2a, m2b);
										m_DeM_Value.set(String.valueOf(dem));
										//m_score_Value.set(String.valueOf(score));
										m_rgbColorIntM.set(Color.rgb(186, 11, 121));
										 cmyFilter = "M";
										SolidM = DenxM;
										flagM =1;
										((AppStart) getActivity().getApplication()).setMmd(Double.toString(SolidM));
										String Mmd = ((AppStart) getActivity().getApplication()).getMmd();
										((AppStart) getActivity().getApplication()).setMmde(Double.toString(dem));
										String Mmde = ((AppStart) getActivity().getApplication()).getMmde();
									}
									else if ((deg > 342 || deg < 12) & chrome2 < 60 & chrome2 > 9 & fixFlag == 0 & flagM == 1) { //M50
										DenxM = densM(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);

										m_DenA_Value.set(String.valueOf(DenxM));
										System.out.println("filter is M50");
										m1L = 71;
										m1a = 34;
										m1b = -7;
										m_m50L_Value.set(String.valueOf(m2L));
										m_m50a_Value.set(String.valueOf(m2a));
										m_m50b_Value.set(String.valueOf(m2b));
										m_DenM50_Value.set(String.valueOf(DenxM));
										m_rgbColorIntM50.set(Color.rgb(209, 149, 185));
										cmyFilter = "M";
										DenM50 = DenxM;
										m50TV=(((1-(pow(10,(-(DenM50-pdm)))))/(1-(pow(10,(-(SolidM-pdm)))))))*100;
										m50TV=Math.round(m50TV * 100d) / 100d;
										m_TVM_Value.set(String.valueOf(m50TV));
										deTVm = abs(TM50-m50TV);
										mTm = m50TV;
										flagM = 0 ;
									}
									else if ((deg > 82 & deg < 100) & chrome2 > 80 & fixFlag == 0) {//Y100
										DenxY = densY(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);

										m_DenY_Value.set(String.valueOf(DenxY));
										m_DenA_Value.set(String.valueOf(DenxY));
										System.out.println("filter is Y");
										m1L = SYTL;
										m1a = SYTa;
										m1b = SYTb;
										yLm = m2L;
										yam = m2a;
										ybm = m2b;

										((AppStart) getActivity().getApplication()).setYTL(Double.toString(SYTL));
										String YTL = ((AppStart) getActivity().getApplication()).getYTL();
										System.out.println(":YTL!"+ YTL );
										((AppStart) getActivity().getApplication()).setYTa(Double.toString(SYTa));
										//String YTa = ((AppStart) getActivity().getApplication()).getYTa();
										((AppStart) getActivity().getApplication()).setYTb(Double.toString(SYTb));
										//String YTb = ((AppStart) getActivity().getApplication()).getYTb();

										((AppStart) getActivity().getApplication()).setYmL(Double.toString(yLm));
										String YmL = ((AppStart) getActivity().getApplication()).getYmL();
										System.out.println(":YmL!"+ YmL );
										((AppStart) getActivity().getApplication()).setYma(Double.toString(yam));
										String Yma = ((AppStart) getActivity().getApplication()).getYma();
										((AppStart) getActivity().getApplication()).setYmb(Double.toString(ybm));
										String Ymb = ((AppStart) getActivity().getApplication()).getYmb();


										m_y100L_Value.set(String.valueOf(m2L));
										m_y100a_Value.set(String.valueOf(m2a));
										m_y100b_Value.set(String.valueOf(m2b));
										dey = de00(m1L, m1a, m1b, m2L, m2a, m2b);
										m_DeY_Value.set(String.valueOf(dey));
										m_rgbColorIntY.set(Color.rgb(242, 223, 33));
										 cmyFilter = "Y";
										SolidY = DenxY;
										((AppStart) getActivity().getApplication()).setYmd(Double.toString(SolidY));
										String Ymd = ((AppStart) getActivity().getApplication()).getYmd();
										((AppStart) getActivity().getApplication()).setYmde(Double.toString(dey));
										String Ymde = ((AppStart) getActivity().getApplication()).getYmde();
										flagY = 1;
									}
									else if ((deg > 82 & deg < 107) & chrome2 < 80 & chrome2 > 9 & fixFlag == 0 & flagY == 1) {//Y50
										DenxY = densY(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);

										m_DenA_Value.set(String.valueOf(DenxY));
										System.out.println("filter is Y50");
										m1L = 91;
										m1a = -3;
										m1b = 41;
										m_y50L_Value.set(String.valueOf(m2L));
										m_y50a_Value.set(String.valueOf(m2a));
										m_y50b_Value.set(String.valueOf(m2b));
										m_DenY50_Value.set(String.valueOf(DenxY));
										m_rgbColorIntY50.set(Color.rgb(241, 230, 153));
										cmyFilter = "Y";
										DenY50 = DenxY;
										y50TV=(((1-(pow(10,(-(DenY50-pdm)))))/(1-(pow(10,(-(SolidY-pdm)))))))*100;
										y50TV=Math.round(y50TV * 100d) / 100d;
										m_TVY_Value.set(String.valueOf(y50TV));
										deTVy = abs(TY50-y50TV);
										yTm = y50TV;
										flagY = 0 ;
									}
									else if (data.getData()[1] < 7 & chrome2 < 7 & fixFlag == 0) {  //K100

										Denx = dens(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);

										m_DenK_Value.set(String.valueOf(Denx));
										m_DenA_Value.set(String.valueOf(Denx));
										System.out.println("filter is K");
										m1L = 16;
										m1a = 0.01;
										m1b = 0.01;
										kLm = m2L;
										kam = m2a;
										kbm = m2b;
										((AppStart) getActivity().getApplication()).setKTL(Double.toString(SKTL));
										String KTL = ((AppStart) getActivity().getApplication()).getKTL();
										System.out.println(":KTL!"+ KTL );
										((AppStart) getActivity().getApplication()).setKTa(Double.toString(SKTa));
										//String KTa = ((AppStart) getActivity().getApplication()).getKTa();
										((AppStart) getActivity().getApplication()).setKTb(Double.toString(SKTb));
										//String KTb = ((AppStart) getActivity().getApplication()).getKTb();

										((AppStart) getActivity().getApplication()).setKmL(Double.toString(kLm));
										String KmL = ((AppStart) getActivity().getApplication()).getKmL();
										System.out.println(":KmL!"+ KmL );
										((AppStart) getActivity().getApplication()).setKma(Double.toString(kam));
										String Kma = ((AppStart) getActivity().getApplication()).getKma();
										((AppStart) getActivity().getApplication()).setKmb(Double.toString(kbm));
										String Kmb = ((AppStart) getActivity().getApplication()).getKmb();

										m_k100L_Value.set(String.valueOf(m2L));
										m_k100a_Value.set(String.valueOf(m2a));
										m_k100b_Value.set(String.valueOf(m2b));
										dek = de00(m1L, m1a, m1b, m2L, m2a, m2b);
										m_DeK_Value.set(String.valueOf(dek));
										m_rgbColorIntK.set(Color.rgb(58, 58, 58));
										cmyFilter = "K";
										SolidK = Denx;
										((AppStart) getActivity().getApplication()).setKmd(Double.toString(SolidK));
										String Kmd = ((AppStart) getActivity().getApplication()).getKmd();
										((AppStart) getActivity().getApplication()).setKmde(Double.toString(dek));
										String Kmde = ((AppStart) getActivity().getApplication()).getKmde();
										flagK =1;
									}
									else if (smartK<1.5 & chrome2<6 & fixFlag == 0 & flagK == 1) { //K50
										Denx = dens(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);

										m_DenA_Value.set(String.valueOf(Denx));
										System.out.println("filter is K50");
										m1L = 58;
										m1a = 0.01;
										m1b = -1;
										m_k50L_Value.set(String.valueOf(m2L));
										m_k50a_Value.set(String.valueOf(m2a));
										m_k50b_Value.set(String.valueOf(m2b));
										m_DenK50_Value.set(String.valueOf(Denx));
										m_rgbColorIntK50.set(Color.rgb(147, 148, 149));
										cmyFilter = "K";
										DenK50 = Denx;
										k50TV=(((1-(pow(10,(-(DenK50-pdm)))))/(1-(pow(10,(-(SolidK-pdm)))))))*100;
										k50TV=Math.round(k50TV * 100d) / 100d;
										m_TVK_Value.set(String.valueOf(k50TV));
										deTVk = abs(TK50-k50TV);
										kTm= k50TV;
										flagK = 0 ;
									}

									else if (D50L>25 & D50L<75 & D50a<20 & D50a>-20 & D50b<20 & D50b>-20 & flagS < 1 ) { //gray or K proof K50
										Denxg = dens(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										score = 120;


										m_DenA_Value.set(String.valueOf(Denxg));
										System.out.println("filter is gray");



										m1L = 58;
										m1a = 0.01;
										m1b = -1;

										gmL=D50L;
										gma= D50a;
										gmb= D50b;

										df=sqrt(pow((D50a-tga),2)+pow((D50b-tgb),2));
										df=Math.round(df * 100d) / 100d;
										dl= D50L-58;
										dl=Math.round(dl * 100d) / 100d;
										double da= D50a-tga;
										da=Math.round(da * 100d) / 100d;
										double db= D50b-tgb;
										db=Math.round(db * 100d) / 100d;

										dlT = dl;
										dfT = df;

										////////gray rule set 2
										double TL=58;
										double Ta=tga;
										double Tb=tgb;


										double emin=100;
										String mddexT = null;
										double deT=0;

										double cy=0;
										double cc=0;
										double cm=0;

										double mc=0;
										double mm=0;
										double my=0;

										double yc=0;
										double ym=0;
										double yy=0;


										Map<String, Double> decmy = new HashMap<>();
										for (int i = 45; i <=55; i=i+1) {
											for (int j = 35; j <=45; j=j+1) {
												for (int k = 35; k <=45; k=k+1) {
													double dc=(i-50)/1;
													double dm=(j-40)/1;
													double dy=(k-40)/1;
													cc=dc*0.3; //echo "cc:".$cc;
													cm=dm*0.4;//echo "cm:".$cm;

													if (dy>1 || dy == 0) {
														cy=0;
													}
													else {
														cy=1;
													}

													mc=-dc*0.6;//echo "mc:".$mc;
													mm=dm*0.7;//echo "mm:".$mm;

													if (dy>1) {
														my=-dy*0.1;
													}
													else {
														my=-dy*0.2;
													}

													yc=-dc*0.4;
													ym=-dm*0.2;
													yy=dy*0.6;

													double CL = D50L-(cc+cm-cy);
													double Ca = D50a+(mc+mm+my);
													double Cb = D50b+(yc+ym+yy);

												decmy.put("de" + i + j + k, de00(TL,Ta,Tb,CL,Ca,Cb));
													//System.out.println("emin: " + emin + "-"+de00(TL,Ta,Tb,CL,Ca,Cb));
													emin=Math.min(emin, de00(TL,Ta,Tb,CL,Ca,Cb));
													if (emin==de00(TL,Ta,Tb,CL,Ca,Cb)) {
														mddexT =i+"-"+j+"-"+k;
														//System.out.println("mddexT: " + mddexT );
														System.out.println(" fix emin: " + emin + ":"+mddexT + "-"+de00(TL,Ta,Tb,CL,Ca,Cb));

													}
										}}}
										String pipeDelimited = mddexT;
										String[] cmyFix = pipeDelimited.split("-");
										int FixC = Integer.parseInt(cmyFix[0]);
										int FixM = Integer.parseInt(cmyFix[1]);
										int FixY = Integer.parseInt(cmyFix[2]);
										System.out.println("Fix: " + FixC + ":"+FixM + "-"+FixY);
										m_cf_Value.set(String.valueOf(FixC-50));
										m_mf_Value.set(String.valueOf(FixM-40));
										m_yf_Value.set(String.valueOf(FixY-40));
										///////gray rule set 2
										m_grayL_Value.set(String.valueOf(m2L));
										m_graya_Value.set(String.valueOf(m2a));
										m_grayb_Value.set(String.valueOf(m2b));
										m_Dengray_Value.set(String.valueOf(Denxg));
										m_dl_Value.set(String.valueOf(dl));
										m_da_Value.set(String.valueOf(da));
										m_db_Value.set(String.valueOf(db));
										m_df_Value.set(String.valueOf(df));
										System.out.println("score  is "+ score + ",dl:" + dl + "," + df+ ",dec" + dec+ "," + dem+ "," + dey+ "," + dek+ ",detv" + deTVc+ "," + deTVm+ "," + deTVy+ "," + deTVk);
										score = score - abs(dl*2)- abs(df*6)-dec - dem -dey -dek-deTVc - deTVm -deTVy -deTVk;

										((AppStart) getActivity().getApplication()).setGTL(Double.toString(58));
										((AppStart) getActivity().getApplication()).setGTa(Double.toString(Ta));
										((AppStart) getActivity().getApplication()).setGTb(Double.toString(Tb));
										System.out.println(":GTL!");

										((AppStart) getActivity().getApplication()).setGmL(Double.toString(gmL));
										((AppStart) getActivity().getApplication()).setGma(Double.toString(gma));
										((AppStart) getActivity().getApplication()).setGmb(Double.toString(gmb));

										((AppStart) getActivity().getApplication()).setDeL(Double.toString(dl));
										((AppStart) getActivity().getApplication()).setDea(Double.toString(da));
										((AppStart) getActivity().getApplication()).setDeb(Double.toString(db));
										((AppStart) getActivity().getApplication()).setDef(Double.toString(df));

										((AppStart) getActivity().getApplication()).setGcop(Double.toString(abs(FixC-50)));
										((AppStart) getActivity().getApplication()).setGmop(Double.toString(abs(FixM-40)));
										((AppStart) getActivity().getApplication()).setGyop(Double.toString(abs(FixY-40)));

										if ((FixC-50) > 0) {
											cmp = "+";
										}
										else {
											cmp = "-";
										}
										((AppStart) getActivity().getApplication()).setGcsn(cmp);

										if ((FixM-40) > 0) {
											mmp = "+";
										}
										else {
											mmp = "-";
										}
										((AppStart) getActivity().getApplication()).setGmsn(mmp);

										if ((FixY-40) > 0) {
											ymp = "+";
										}
										else {
											ymp = "-";
										}
										((AppStart) getActivity().getApplication()).setGysn(ymp);

										//score = score - abs(df*6);
										m_score_Value.set(String.valueOf(round(score*100d)/100d));
										//Math.round(df * 100d) / 100d;
										System.out.println("dl"+ dl);
										System.out.println("df"+ df);
										System.out.println("lab:"+ m2L + ","+ m2a + ","+ m2b );
										m_rgbColorIntgray.set(Color.rgb(147, 148, 149));
										cmyFilter = "K";
										DenK50 = Denx;
										k50TV=(((1-(pow(10,(-(DenK50-pdm)))))/(1-(pow(10,(-(SolidK-pdm)))))))*100;
										k50TV=Math.round(k50TV * 100d) / 100d;
										m_TV_Value.set(String.valueOf(0));
									} //gray

									else {
										     ///Spot
											//DenxS = densS(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
											m_DenS_Value.set(String.valueOf(DenxS));
											m_DenA_Value.set(String.valueOf(DenxS));
											System.out.println("filter is S");
											cmyFilter = "S";


										System.out.println("SolidS and DenxS is " + SolidS +":" + DenxS);
										if (flagS == 1 & (SolidS - DenxS) > 0.15    ) {   //STV
											System.out.println("solids filter is STV");
											cX = D50X;
											cY = D50Y;
											cZ = D50Z;

											double ca =D50a;
											double cb =D50b;
											double cTV = 100 * pow(((pow((cX - PXm), 2) + pow((cY - PYm), 2) + pow((cZ - PZm), 2)) / (pow((SXm - PXm), 2) + pow((SYm - PYm), 2) + pow((SZm - PZm), 2))), 0.5); //sctv
											cTV = Math.round(cTV * 100d) / 100d;
											m_TV_Value.set(String.valueOf(cTV));
											double hue2 = Math.atan2(D50b, D50a);
											spoth2 = (hue2 / Math.PI * 180) + (hue2 > 0 ? 0 : 360);
											flagSTV=1;
											//& abs(spoth2-spoth1)<1Mat0

											System.out.println("sctv : cX=" + cX + "cY:" + cY + "cZ:" + cZ + "PXm:" + PXm + "SXm:" + SXm);
											System.out.println("sctv : " + cTV);
										}
										System.out.println("SolidS hue is " + spoth1 +":" + spoth2);
										if (flagSTV == 0 ) { //set spot
											SolidS = DenxS;
											hue = Math.atan2(D50b, D50a);
											spoth1 = (hue / Math.PI * 180) + (hue > 0 ? 0 : 360);
											SXm = D50X;
											SYm = D50Y;
											SZm = D50Z;
											flagS = 1;
										}
										//double chrome2 = pow((pow(m2a, 2) + pow(m2b, 2)), 0.5);
										//double hue = Math.atan2(m2b, m2a);
										//double deg = (hue / Math.PI * 180) + (hue > 0 ? 0 : 360);
									}//spot color




									double mArray[] = new double[31];
									double rArray[] = new double[31];
									double pArray[] = {mp0,mp1,mp2,mp3,mp4,mp5,mp6,mp7,mp8,mp9,mp10,mp11,mp12,mp13,mp14,mp15,mp16,mp17,mp18,mp19,mp20,mp21,mp22,mp23,mp24,mp25,mp26,mp27,mp28,mp29,mp30};
									double ki = 0.820;
									double bestDen = 0;
									double bestid = 0;
									double de00min = 100;
									for (int i = 0; i <= 30; i += 1) {

										for (int s = 0; s <= 30; s += 1) {
											mArray[s] = data.getData()[s];
											rArray[s] = pow(10, -((((-log10(mArray[s])) - (-log10(pArray[s]))) * ki) + (-log10(pArray[s])))) * 1;
										}
										ki=ki+0.015;

										double s0X = spXYZ("X", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2],  rArray[3],  rArray[4],  rArray[5],  rArray[6],  rArray[7],  rArray[8],  rArray[9],  rArray[10],  rArray[11],  rArray[12],  rArray[13],  rArray[14],  rArray[15],  rArray[16],  rArray[17],  rArray[18],  rArray[19],  rArray[20],  rArray[21],  rArray[22],  rArray[23],  rArray[24],  rArray[25],  rArray[26],  rArray[27],  rArray[28],  rArray[29],  rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										double s0Y = spXYZ("Y", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00,  rArray[0],  rArray[1],  rArray[2],  rArray[3],  rArray[4],  rArray[5],  rArray[6],  rArray[7],  rArray[8],  rArray[9],  rArray[10],  rArray[11],  rArray[12],  rArray[13],  rArray[14],  rArray[15],  rArray[16],  rArray[17],  rArray[18],  rArray[19],  rArray[20],  rArray[21],  rArray[22],  rArray[23],  rArray[24],  rArray[25],  rArray[26],  rArray[27],  rArray[28],  rArray[29],  rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										double s0Z = spXYZ("Z", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00,  rArray[0],  rArray[1],  rArray[2],  rArray[3],  rArray[4],  rArray[5],  rArray[6],  rArray[7],  rArray[8],  rArray[9],  rArray[10],  rArray[11],  rArray[12],  rArray[13],  rArray[14],  rArray[15],  rArray[16],  rArray[17],  rArray[18],  rArray[19],  rArray[20],  rArray[21],  rArray[22],  rArray[23],  rArray[24],  rArray[25],  rArray[26],  rArray[27],  rArray[28],  rArray[29],  rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										double s0L = xyz2lab01("l", s0X, s0Y, s0Z);
										double s0a = xyz2lab01("a", s0X, s0Y, s0Z);
										double s0b = xyz2lab01("b", s0X, s0Y, s0Z);
										double Dens0C = 0;

										//System.out.println(" lab  is " + s0L + "," +s0a + ","+s0b);

										if (cmyFilter == "C"  & fixFlag == 0) {
											 Dens0C = densC(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										}
										else if (cmyFilter == "M" & fixFlag == 0) {
											 Dens0C = densM(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										}
										else if (cmyFilter == "Y" & fixFlag == 0) {
											 Dens0C = densY(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										}
										else if (cmyFilter == "K" & fixFlag == 0) {
											 Dens0C = dens(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										}
										else if (cmyFilter == "S" ) {
											Dens0C = densS(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										}

										de00min = Math.min(de00min,de00(m1L,m1a,m1b,s0L,s0a,s0b));
										//System.out.println("Best de00  is " + de00min + "TLab:"+ m1L +","+ m1a +","+ m1b +",");

										if (de00min==de00(m1L,m1a,m1b,s0L,s0a,s0b)) {
											 bestDen = Dens0C;
											 bestid = i;
											if (cmyFilter == "C" & flagC ==1){
											((AppStart) getActivity().getApplication()).setCpL(Double.toString(s0L));
											String CpL = ((AppStart) getActivity().getApplication()).getCpL();
											System.out.println(":CpL!"+ CpL );
											((AppStart) getActivity().getApplication()).setCpa(Double.toString(s0a));
											String Cpa = ((AppStart) getActivity().getApplication()).getCpa();
											((AppStart) getActivity().getApplication()).setCpb(Double.toString(s0b));
											String Cpb = ((AppStart) getActivity().getApplication()).getCpb();
											((AppStart) getActivity().getApplication()).setCpd(Double.toString(bestDen));
											String Cpd = ((AppStart) getActivity().getApplication()).getCpd();
											((AppStart) getActivity().getApplication()).setCpde(Double.toString(de00min));
											String Cpde = ((AppStart) getActivity().getApplication()).getCpde();
											cop = round(abs(bestDen - SolidC)*100d)/100d;

											((AppStart) getActivity().getApplication()).setCop(Double.toString(cop));
											String Cop = ((AppStart) getActivity().getApplication()).getCop();
											//String mp = "";
											if (bestDen > SolidC) {
												mp = "+";
											}
											else {
												mp = "-";
											}
											((AppStart) getActivity().getApplication()).setCsn(mp);
											String Csn = ((AppStart) getActivity().getApplication()).getCsn();

											}
											if (cmyFilter == "M" & flagM ==1){
												((AppStart) getActivity().getApplication()).setMpL(Double.toString(s0L));
												String MpL = ((AppStart) getActivity().getApplication()).getMpL();
												System.out.println(":MpL!"+ MpL );
												((AppStart) getActivity().getApplication()).setMpa(Double.toString(s0a));
												String Mpa = ((AppStart) getActivity().getApplication()).getMpa();
												((AppStart) getActivity().getApplication()).setMpb(Double.toString(s0b));
												String Mpb = ((AppStart) getActivity().getApplication()).getMpb();
												((AppStart) getActivity().getApplication()).setMpd(Double.toString(bestDen));
												String Mpd = ((AppStart) getActivity().getApplication()).getMpd();
												((AppStart) getActivity().getApplication()).setMpde(Double.toString(de00min));
												String Mpde = ((AppStart) getActivity().getApplication()).getMpde();
												mop = round(abs(bestDen - SolidM)*100d)/100d;

												((AppStart) getActivity().getApplication()).setMop(Double.toString(mop));
												String Mop = ((AppStart) getActivity().getApplication()).getMop();
												//String mp = "";
												if (bestDen > SolidM) {
													mp = "+";
												}
												else {
													mp = "-";
												}
												((AppStart) getActivity().getApplication()).setMsn(mp);
												String Msn = ((AppStart) getActivity().getApplication()).getMsn();

											}
											if (cmyFilter == "Y" & flagY ==1){
												((AppStart) getActivity().getApplication()).setYpL(Double.toString(s0L));
												String YpL = ((AppStart) getActivity().getApplication()).getYpL();
												//System.out.println(":YpL!"+ YpL );
												((AppStart) getActivity().getApplication()).setYpa(Double.toString(s0a));
												String Ypa = ((AppStart) getActivity().getApplication()).getYpa();
												((AppStart) getActivity().getApplication()).setYpb(Double.toString(s0b));
												String Ypb = ((AppStart) getActivity().getApplication()).getYpb();
												((AppStart) getActivity().getApplication()).setYpd(Double.toString(bestDen));
												String Ypd = ((AppStart) getActivity().getApplication()).getYpd();
												((AppStart) getActivity().getApplication()).setYpde(Double.toString(de00min));
												String Ypde = ((AppStart) getActivity().getApplication()).getYpde();
												yop = round(abs(bestDen - SolidY)*100d)/100d;

												((AppStart) getActivity().getApplication()).setYop(Double.toString(yop));
												String Yop = ((AppStart) getActivity().getApplication()).getYop();
												//String mp = "";
												if (bestDen > SolidY) {
													mp = "+";
												}
												else {
													mp = "-";
												}
												((AppStart) getActivity().getApplication()).setYsn(mp);
												String Ysn = ((AppStart) getActivity().getApplication()).getYsn();

											}
											if (cmyFilter == "K" & flagK ==1){
												((AppStart) getActivity().getApplication()).setKpL(Double.toString(s0L));
												String KpL = ((AppStart) getActivity().getApplication()).getKpL();
												//System.out.println(":KpL!"+ KpL );
												((AppStart) getActivity().getApplication()).setKpa(Double.toString(s0a));
												String Kpa = ((AppStart) getActivity().getApplication()).getKpa();
												((AppStart) getActivity().getApplication()).setKpb(Double.toString(s0b));
												String Kpb = ((AppStart) getActivity().getApplication()).getKpb();
												((AppStart) getActivity().getApplication()).setKpd(Double.toString(bestDen));
												String Kpd = ((AppStart) getActivity().getApplication()).getKpd();
												((AppStart) getActivity().getApplication()).setKpde(Double.toString(de00min));
												String Kpde = ((AppStart) getActivity().getApplication()).getKpde();
												kop = round(abs(bestDen - SolidK)*100d)/100d;

												((AppStart) getActivity().getApplication()).setKop(Double.toString(kop));
												String Kop = ((AppStart) getActivity().getApplication()).getKop();
												//String mp = "";
												if (bestDen > SolidK) {
													mp = "+";
												}
												else {
													mp = "-";
												}
												((AppStart) getActivity().getApplication()).setKsn(mp);
												String Ksn = ((AppStart) getActivity().getApplication()).getKsn();

											}
										}

									}

									System.out.println("Best Den is " + bestDen);
									System.out.println("Best id  is " + bestid);
									m_BestDen_Value.set(String.valueOf(bestDen));
									m_BestDe00_Value.set(String.valueOf(de00min));
									System.out.println("Best de00  is " + de00min);

								int intColor2 = ColorUtils.LABToColor(data.getLabData()[0], data.getLabData()[1], data.getLabData()[2]);


								int red2 = Color.red(intColor2);
								int green2 = Color.green(intColor2);
								int blue2 = Color.blue(intColor2);



								m_DenS_Value.set(String.valueOf(SolidS));
								m_hidex_Value.set(String.valueOf(DenxHi)+"nm");
								m_R2_Value.set(String.valueOf(red2));
								m_G2_Value.set(String.valueOf(green2));
								m_B2_Value.set(String.valueOf(blue2));
								m_rgbColorInt2.set(Color.rgb(red2, green2, blue2));

								} //color2

								else if (mBtn == "measureTargetColorf") {
									m_L1_Value.set(String.valueOf(D50L));
									m_a1_Value.set(String.valueOf(D50a));
									m_b1_Value.set(String.valueOf(D50b));
									fixFlag = 1;
									m1L = D50L;
									m1a = D50a;
									m1b = D50b;
									m_fix_Value.set(String.valueOf("spot"));
									flagS =1;
									SolidS = DenxS;
									//System.out.println("paper array 6 is : " + mp6);

									chrome2 = pow((pow(m2a, 2) + pow(m2b, 2)), 0.5);
									hue = Math.atan2(m2b, m2a);
									deg = (hue / Math.PI * 180) + (hue > 0 ? 0 : 360);
									System.out.println("deg is : " + deg);
									System.out.println("chrome is : " + chrome2);
									System.out.println("dot 1 is : " + data.getData()[1]);


/*
									double mArray[] = new double[31];
									double rArray[] = new double[31];
									double pArray[] = {mp0, mp1, mp2, mp3, mp4, mp5, mp6, mp7, mp8, mp9, mp10, mp11, mp12, mp13, mp14, mp15, mp16, mp17, mp18, mp19, mp20, mp21, mp22, mp23, mp24, mp25, mp26, mp27, mp28, mp29, mp30};
									double ki = 0.820;
									double bestDen = 0;
									double bestid = 0;
									double de00min = 100;
									for (int i = 0; i <= 30; i += 1) {

										for (int s = 0; s <= 30; s += 1) {
											mArray[s] = data.getData()[s];
											rArray[s] = pow(10, -((((-log10(mArray[s])) - (-log10(pArray[s]))) * ki) + (-log10(pArray[s])))) * 1;
										}
										ki = ki + 0.015;

										double s0X = spXYZ("X", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										double s0Y = spXYZ("Y", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										double s0Z = spXYZ("Z", 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
										double s0L = xyz2lab01("l", s0X, s0Y, s0Z);
										double s0a = xyz2lab01("a", s0X, s0Y, s0Z);
										double s0b = xyz2lab01("b", s0X, s0Y, s0Z);
										double Dens0C = 0;

										//System.out.println(" lab  is " + s0L + "," + s0a + "," + s0b);


											Dens0C = densS(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, rArray[0], rArray[1], rArray[2], rArray[3], rArray[4], rArray[5], rArray[6], rArray[7], rArray[8], rArray[9], rArray[10], rArray[11], rArray[12], rArray[13], rArray[14], rArray[15], rArray[16], rArray[17], rArray[18], rArray[19], rArray[20], rArray[21], rArray[22], rArray[23], rArray[24], rArray[25], rArray[26], rArray[27], rArray[28], rArray[29], rArray[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);


										de00min = Math.min(de00min, de00(m1L, m1a, m1b, s0L, s0a, s0b));
										System.out.println("Best de00  is " + de00min);

										if (de00min == de00(m1L, m1a, m1b, s0L, s0a, s0b)) {
											bestDen = Dens0C;
											bestid = i;
										}

									} //beer's law
*/
									//System.out.println("Best Den is " + bestDen);
									//System.out.println("Best id  is " + bestid);
									//m_BestDen_Value.set(String.valueOf(bestDen));
									//m_BestDe00_Value.set(String.valueOf(de00min));

									int intColor1 = ColorUtils.LABToColor(data.getLabData()[0], data.getLabData()[1], data.getLabData()[2]);

									m_DenS_Value.set(String.valueOf(SolidS));
									int red1 = Color.red(intColor1);
									int green1 = Color.green(intColor1);
									int blue1 = Color.blue(intColor1);
									m_R_Value.set(String.valueOf(red1));
									m_G_Value.set(String.valueOf(green1));
									m_B_Value.set(String.valueOf(blue1));
									m_rgbColorInt.set(Color.rgb(red1, green1, blue1));

								}

									 dE00 = de00(m2L ,m2a,m2b,m1L ,m1a,m1b);
								m_dE00_Value.set(String.valueOf(dE00));
//						IBluetoothManager.getInstance().setOrder(Constant.READ_RGB_MEASURE_DATA);
							}
							else {
								mReadMeasureDataBean = null;
								MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), getString(R.string.measure_fail));
								mBtn = "";
							}
						}
						@Override
						public void onReadRgbMeasureData(ReadRgbMeasureDataBean data) {
						}
					});
		}
		return root;
	}

	//@Override
	public void onClick(View view) {

		boolean checked = ((RadioButton) view).isChecked();
		switch(view.getId()){

			case R.id.mRadioButton0:
				//Toast.makeText(getActivity(), "A", Toast.LENGTH_LONG ).show();
				System.out.println("case button Fogra39!" );
				target = "Fogra39";
				SCTL=55;
				SCTa=-37;
				SCTb=-50;
				SMTL=48;
				SMTa=74;
				SMTb=-3;
				SYTL=89;
				SYTa=-5;
				SYTb=93;
				SKTL=16;
				SKTa=0;
				SKTb=0;
				TC50=64;
				TM50=64;
				TY50=64;
				TK50=67;
				score =120;

				dec = de00(SCTL ,SCTa,SCTb,cLm ,cam,cbm);
				dem = de00(SMTL ,SMTa,SMTb,mLm ,mam,mbm);
				dey = de00(SYTL ,SYTa,SYTb,yLm ,yam,ybm);
				dek = de00(SKTL ,SKTa,SKTb,kLm ,kam,kbm);
				deTVc = abs(TC50-cTm);
				deTVm = abs(TM50-mTm);
				deTVy = abs(TY50-yTm);
				deTVk = abs(TK50-kTm);

				System.out.println("score btn is "+ score + ",dlT:" + dlT + "," + dfT+ ",dec" + dec+ "," + dem+ "," + dey+ "," + dek+ ",detv" + deTVc+ "," + deTVm+ "," + deTVy+ "," + deTVk);
				score = score - abs(dl*2)- abs(df*6)-dec - dem -dey -dek-deTVc - deTVm -deTVy -deTVk;

				//score = score - abs(df*6);
				m_score_Value.set(String.valueOf(round(score*100d)/100d));
				break;
			case R.id.mRadioButton1:
				//Toast.makeText(getActivity(), "B", Toast.LENGTH_LONG ).show();
				System.out.println("case button Fpgra51!" );
				target = "Fogra51";
				SCTL=56;
				SCTa=-36;
				SCTb=-51;
				SMTL=48;
				SMTa=75;
				SMTb=-4;
				SYTL=89;
				SYTa=-4;
				SYTb=93;
				SKTL=16;
				SKTa=0;
				SKTb=0;
				TC50=66;
				TM50=66;
				TY50=66;
				TK50=66;
				score =120;
				dec = de00(SCTL ,SCTa,SCTb,cLm ,cam,cbm);
				dem = de00(SMTL ,SMTa,SMTb,mLm ,mam,mbm);
				dey = de00(SYTL ,SYTa,SYTb,yLm ,yam,ybm);
				dek = de00(SKTL ,SKTa,SKTb,kLm ,kam,kbm);
				deTVc = abs(TC50-cTm);
				deTVm = abs(TM50-mTm);
				deTVy = abs(TY50-yTm);
				deTVk = abs(TK50-kTm);
				System.out.println("score btn is "+ score + ",dl:" + dlT + "," + dfT+ ",dec" + dec+ "," + dem+ "," + dey+ "," + dek+ ",detv" + deTVc+ "," + deTVm+ "," + deTVy+ "," + deTVk);
				score = score - abs(dl*2)- abs(df*6)-dec - dem -dey -dek-deTVc - deTVm -deTVy -deTVk;

				//score = score - abs(df*6);
				m_score_Value.set(String.valueOf(round(score*100d)/100d));
				break;
			case R.id.mRadioButton2:
				//Toast.makeText(getActivity(), "C", Toast.LENGTH_LONG ).show();
				System.out.println("case button CRPC6!" );
				target = "CRPC6";
				SCTL=56;
				SCTa=-37;
				SCTb=-50;
				SMTL=48;
				SMTa=75;
				SMTb=-4;
				SYTL=89;
				SYTa=-4;
				SYTb=93;
				SKTL=16;
				SKTa=0;
				SKTb=0;
				TC50=66;
				TM50=66;
				TY50=66;
				TK50=69;
				score =120;
				dec = de00(SCTL ,SCTa,SCTb,cLm ,cam,cbm);
				dem = de00(SMTL ,SMTa,SMTb,mLm ,mam,mbm);
				dey = de00(SYTL ,SYTa,SYTb,yLm ,yam,ybm);
				dek = de00(SKTL ,SKTa,SKTb,kLm ,kam,kbm);
				deTVc = abs(TC50-cTm);
				deTVm = abs(TM50-mTm);
				deTVy = abs(TY50-yTm);
				deTVk = abs(TK50-kTm);
				System.out.println("score btn is "+ score + ",dl:" + dlT + "," + dfT+ ",dec" + dec+ "," + dem+ "," + dey+ "," + dek+ ",detv" + deTVc+ "," + deTVm+ "," + deTVy+ "," + deTVk);
				score = score - abs(dl*2)- abs(df*6)-dec - dem -dey -dek-deTVc - deTVm -deTVy -deTVk;

				//score = score - abs(df*6);
				m_score_Value.set(String.valueOf(round(score*100d)/100d));
				break;
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double dens(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
		double[] DenVArray = new double[]{0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.01000000, 0.02098940, 0.08203515, 0.27989813, 0.64714262, 1.23026877, 2.21819642, 3.81944271, 6.57657837, 10.99005839, 18.87991349, 32.58367010, 50.35006088, 66.83439176, 80.35261222, 90.57326009, 97.49896377, 100.00000000, 97.49896377, 90.36494737, 79.79946873, 67.14288529, 53.82697825, 39.17418771, 27.10191632, 17.29816359, 10.30386120, 5.61047976, 3.09029543, 1.54170045, 0.79615935, 0.41879357, 0.21577444, 0.10990058, 0.05395106, 0.02697739, 0.01399587, 0.01000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000};

		double[] DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		double sPSum = 0;
		double sProduct = 0;
		//int i = 18;

		for (int i = 0; i < 50; i++) {
			sProduct = DenMxArray[i] * DenVArray[i];
			sPSum = sProduct + sPSum;
		}

		double dx1 = s18* 1 ;
		double sumV= Arrays.stream(DenVArray).sum();
		double den=-log10(sPSum/(sumV));
		//System.out.println("Density values : " + den);

		return Math.round(den * 100d) / 100d;
	}
	@RequiresApi(api = Build.VERSION_CODES.N)
	private double  densC(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
		double[] DenCArray = new double[]{0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00100000, 0.05997911, 0.44977985, 29.99162519, 100.00000000, 84.91804750, 54.95408739, 25.00345362, 10.00000000, 5.00034535, 1.49968484, 0.50003453, 0.29991625, 0.14996848, 0.05000345, 0.01000000, 0.00100000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000};

		double[] DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		double sPSum = 0;
		double sProduct = 0;
		//int i = 18;

		for (int i = 0; i < 50; i++) {
			sProduct = DenMxArray[i] * DenCArray[i];
			sPSum = sProduct + sPSum;
		}

		double sumV= Arrays.stream(DenCArray).sum();
		double denC=-log10(sPSum/(sumV));

		return Math.round(denC * 100d) / 100d;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double densM(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
		double[] DenMArray = new double[]{0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00100000, 1.00000000, 5.00034535, 27.98981320, 68.07693587, 92.04495718, 100.00000000, 87.90225168, 66.06934480, 41.97589840, 21.97859873, 8.99497582, 2.50034536, 0.69984200, 0.08994976, 0.01000000, 0.00100000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000};
		double[] DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		double sPSum = 0;
		double sProduct = 0;
		//int i = 18;

		for (int i = 0; i < 50; i++) {
			sProduct = DenMxArray[i] * DenMArray[i];
			sPSum = sProduct + sPSum;
		}

		double sumV= Arrays.stream(DenMArray).sum();
		double denM=-log10(sPSum/(sumV));

		return Math.round(denM * 100d) / 100d;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double densY(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
		double[] DenYArray = new double[]{0.00000000, 0.01000000, 0.01999862, 0.10000000, 0.29991625, 1.49968484, 5.99791076, 16.98243652, 39.99447498, 59.97910763, 82.03515443, 93.97233106, 100.00000000, 97.05099672, 84.91804750, 65.01296903, 39.99447498, 17.98870915, 5.00034535, 0.19998619, 0.03999447, 0.00100000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000};

		double[] DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		double sPSum = 0;
		double sProduct = 0;
		//int i = 18;

		for (int i = 0; i < 50; i++) {
			sProduct = DenMxArray[i] * DenYArray[i];
			sPSum = sProduct + sPSum;
		}

		double sumV= Arrays.stream(DenYArray).sum();
		double denY=-log10(sPSum/(sumV));
		return Math.round(denY * 100d) / 100d;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double densS(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
		double[] DenSSArray = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0001, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		double[] DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		double sPSum = 0;
		double sProduct = 0;
		//int i = 18;

		int largest = 0;
		int hiIndex = 0;
		for (int j = 1; j < DenMxArray.length; j ++) {
			if (DenMxArray[j] > DenMxArray[largest]) largest = j;
		}
		hiIndex = (largest + 34);
		//System.out.println("Element at largest index " + hiIndex);

		int lowest = 6;
		int loIndex = 0;
		for (int k = 6; k < 37; k ++) {
			if (DenMxArray[k] < DenMxArray[lowest]) lowest = k;
		}
		loIndex = (lowest + 34);
		//System.out.println("Element at loIndex index " + loIndex);


		int indexF = hiIndex + 15;
		if (indexF < 70 ) {
			indexF = indexF;
		}
		else if (indexF >= 70)  {
			indexF = hiIndex - 15;
		}
		//System.out.println("Spot indexF " + indexF);

		int indexLo = loIndex ;

		//System.out.println("Spot indexLo " + indexLo);

		//DenSSArray[indexF-34] = 14.0388;

		DenSSArray[indexLo-36] = 3.0388;
		DenSSArray[indexLo-35] = 7.0388;
		DenSSArray[indexLo-34] = 14.0388;
		DenSSArray[indexLo-33] = 7.0388;
		DenSSArray[indexLo-32] = 3.0388;

		for (int i = 0; i < 50; i++) {
			sProduct = DenMxArray[i] * DenSSArray[i];
			sPSum = sProduct + sPSum;
		}

		double sumV = Arrays.stream(DenSSArray).sum();
		double denSS =-log10(sPSum/(sumV));
		//System.out.println("Spot density " + denSS);
		return Math.round(denSS * 100d) / 100d;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double densHi(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {

		double[] DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};


		int largest = 0;
		int hiIndex = 0;
		for (int j = 1; j < DenMxArray.length; j ++) {
			if (DenMxArray[j] > DenMxArray[largest]) largest = j;
		}
		hiIndex = (largest + 34);
		//System.out.println("Element at largest index " + hiIndex);

		return hiIndex * 10;
	}

	private double de00(double L1, double a1g, double b1, double L2, double a2g, double b2) {

		double C1o = sqrt(Math.pow((a1g + b1),2));
		double C2o = sqrt(Math.pow((a2g + b2),2));
		double meancO = (C1o +C2o) / 2;
		double logmeanc = log10(meancO);

		double log25 = log10(25);
		double cratio = sqrt(pow(10,( logmeanc * 7 - log10((pow(10,(logmeanc * 7)) + pow(10,(log25 * 7)))))));
		double g = 0.5 * (1 - cratio);

		double a1 = (1 + g) * a1g;
		double a2 = (1 + g) * a2g;

		double C1 = sqrt(pow(a1 ,2) + pow(b1,2));
		double C2 =  sqrt(pow(a2 ,2) + pow(b2,2));

		double h1 = 0;
		double h2 = 0;

		if (b1 == 0) {
			if (a1 >= 0) {
				h1 = 0;
			}
			else {
				h1 = 180;}
		}
		else {
			h1 = Math.toDegrees(Math.atan2(b1, a1));
		}

		if (b2 == 0) {
			if (a2 >= 0) {
				h2 = 0;}
			else {
				h2 = 180; }
		}
		else {
			h2 = Math.toDegrees(Math.atan2(b2, a2));
		}

		if (h1 < 0) {
			h1 = h1 + 360;}

		if (h2 < 0) {
			h2 = h2 + 360;}

		double dhm = 0;

		if (Math.abs(h1-h2)<180) {
			dhm = h2-h1;
		}
		else{
			dhm=360-Math.abs(h1-h2);
		}
		double meanh = h1 + h2;

		if (dhm > 180) {
			dhm = dhm - 360;
			meanh = meanh - 360;
		}

		if (dhm < -180) {
			dhm = dhm + 360;
			meanh = meanh - 360;
		}
		meanh = meanh / 2;

		double T = 1-0.17*Math.cos(deg2Rad(meanh-30))+0.24*Math.cos(deg2Rad(2*meanh))+0.32*Math.cos(deg2Rad(3*meanh+6))-0.2*Math.cos(deg2Rad(4*meanh-63));

		double SH=1+0.015*((C2+C1)/2)*T;

		double dH=2*pow((C2*C1),0.5)*Math.sin(deg2Rad(dhm/2));

		double SC=1+0.045*(C1+ C2)/2;
		double dC=C2 - C1;
		double SL=1+(0.015*pow(((L2+L1)/2-50),2))/pow((20+pow(((L2+L1)/2-50),2)),0.5);
		double dL = L1 - L2;
		double KL=1;double KC=1;double KH=1;
		double meanc=(C1+C2)/2;
		double theta=30*Math.exp(-(pow(((meanh-275)/25),2)));
		double RC=2*pow(pow(meanc,7)/(pow(meanc,7)+pow(25,7)),0.5);

		double RT=-Math.sin(2*deg2Rad(theta))*RC;
		double deltaE2000x=pow((pow((dL/(SL*KL)),2)+pow((dC/(SC*KC)),2)+pow((dH/(SH*KH)),2)+RT*(dC/(SC*KC))*(dH/(SH*KH))),0.5);

		return Math.round(deltaE2000x * 100d) / 100d;

	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double de76(double L1, double a1, double b1, double L2, double a2, double b2) {

		double deCal = sqrt(Math.pow((L1 - L2),2)+pow((a1-a2),2)+pow((b1-b2),2));
		return Math.round(deCal * 100d) / 100d;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	public double best(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double  p1, double  p2, double  p3, double  p4, double  p5, double  p6, double  p7, double  p8, double  p9, double  p10, double  p11, double  p12, double  p13, double  p14, double  p15, double  p16, double  p17, double  p18, double  p19, double  p20, double  p21, double  p22, double  p23, double  p24, double  p25, double  p26, double  p27, double  p28, double  p29, double  p30, double  p31) {
		double[] spPaper = new double[]{ p1 * 0.01,  p2 * 0.01,  p3 * 0.01,  p4 * 0.01,  p5 * 0.01,  p6 * 0.01,  p7 * 0.01,  p8 * 0.01,  p9 * 0.01,  p10 * 0.01,  p11 * 0.01,  p12 * 0.01,  p13 * 0.01,  p14 * 0.01,  p15 * 0.01,  p16 * 0.01,  p17 * 0.01,  p18 * 0.01,  p19 * 0.01,  p20 * 0.01,  p21 * 0.01,  p22 * 0.01,  p23 * 0.01,  p24 * 0.01,  p25 * 0.01,  p26 * 0.01,  p27 * 0.01,  p28 * 0.01,  p29 * 0.01,  p30 * 0.01,  p31 * 0.01};

		double[] spInkArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01};

		return Math.round(spPaper[6] * 100d) / 100d;
	}



	private String mBtn = "";
	public void measureTargetColorp() {
		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			WaitDialogUtil.show((AppCompatActivity) getActivity(), "測量中xp", 2000, null);
			IBluetoothManager.getInstance().setOrder(Constant.MEASURE);
			mBtn = "measureTargetColorp";
		}
		else
			MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), "請先與光譜儀做配對!");
	}

	public void measureTargetreset() {
		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			WaitDialogUtil.show((AppCompatActivity) getActivity(), "reset", 2000, null);
			//IBluetoothManager.getInstance().setOrder(Constant.MEASURE);
			mBtn = "measureTargetreset";
		}
		else
			MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), "請先與光譜儀做配對!");
	}
	public void measureTargetColor1() {
		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			WaitDialogUtil.show((AppCompatActivity) getActivity(), "測量中x1", 2000, null);
			IBluetoothManager.getInstance().setOrder(Constant.MEASURE);
			mBtn = "measureTargetColor1";
		}
		else
			MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), "請先與光譜儀做配對!");
	}

	public void measureTargetColor2() {
		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			WaitDialogUtil.show((AppCompatActivity) getActivity(), "測量中x2", 2000, null);
			IBluetoothManager.getInstance().setOrder(Constant.MEASURE);
			mBtn = "measureTargetColor2";
		}
		else
			MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), "請先與光譜儀做配對!");
	}

	public void measureTargetColor3() {
		//WaitDialogUtil.show((AppCompatActivity) getActivity(), "button x3", 2000, null);
		//IBluetoothManager.getInstance().setOrder(Constant.MEASURE);
		System.out.println("button 3:" );
		mBtn = "measureTargetColor3";
		}
/*
	public void toSwipeLeft() {
		System.out.println("button swipe:" );
		Intent intent = new Intent(getActivity(), SwipeLeft.class);
		startActivity(intent);
	}
*/
	public void toCcmnd() {
		System.out.println("button Ccmnd:" );
		Intent intent = new Intent(getActivity(), Ccmnd.class);
		startActivity(intent);
	}

	public void toMcmnd() {
		System.out.println("button Mcmnd:" );
		Intent intent = new Intent(getActivity(), Mcmnd.class);
		startActivity(intent);
	}

	public void toYcmnd() {
		System.out.println("button Ycmnd:" );
		Intent intent = new Intent(getActivity(), Ycmnd.class);
		startActivity(intent);
	}

	public void toKcmnd() {
		System.out.println("button Kcmnd:" );
		Intent intent = new Intent(getActivity(), Kcmnd.class);
		startActivity(intent);
	}

	public void toGcmnd() {
		System.out.println("button Gcmnd:" );
		Intent intent = new Intent(getActivity(), Graymnd.class);
		startActivity(intent);
	}

	public void measureTargetColorf() {
		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			WaitDialogUtil.show((AppCompatActivity) getActivity(), "測量中xf", 2000, null);
			IBluetoothManager.getInstance().setOrder(Constant.MEASURE);
			mBtn = "measureTargetColorf";
		}
		else
			MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), "請先與光譜儀做配對!");
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	public double spXYZ(String filter, double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
		double[] xObs2Array = new double[]{0.00000000,0.00000000,0.00012990,0.00041490,0.00136800,0.00424300,0.01431000,0.04351000,0.13438000,0.28390000,0.34828000,0.33620000,0.29080000,0.19536000,0.09564000,0.03201000,0.00490000,0.00930000,0.06327000,0.16550000,0.29040000,0.43344990,0.59450000,0.76210000,0.91630000,1.02630000,1.06220000,1.00260000,0.85444990,0.64240000,0.44790000,0.28350000,0.16490000,0.08740000,0.04677000,0.02270000,0.01135916,0.00579035,0.00289933,0.00143997,0.00069008,0.00033230,0.00016615,0.00008308,0.00004151,0.00002067,0.00001025,0.00000509,0.00000252,0.00000125};
		double[] yObs2Array = new double[]{0.00000000,0.00000000,0.00000392,0.00001239,0.00003900,0.00012000,0.00039600,0.00121000,0.00400000,0.01160000,0.02300000,0.03800000,0.06000000,0.09098000,0.13902000,0.20802000,0.32300000,0.50300000,0.71000000,0.86200000,0.95400000,0.99495010,0.99500000,0.95200000,0.87000000,0.75700000,0.63100000,0.50300000,0.38100000,0.26500000,0.17500000,0.10700000,0.06100000,0.03200000,0.01700000,0.00821000,0.00410200,0.00209100,0.00104700,0.00052000,0.00024920,0.00012000,0.00006000,0.00003000,0.00001499,0.00000747,0.00000370,0.00000184,0.00000091,0.00000045};
		double[] zObs2Array = new double[]{0.00000000,0.00000000,0.00060610,0.00194600,0.00645000,0.02005001,0.06785001,0.20740000,0.64560000,1.38560000,1.74706000,1.77211000,1.66920000,1.28764000,0.81295010,0.46518000,0.27200000,0.15820000,0.07824999,0.04216000,0.02030000,0.00875000,0.00390000,0.00210000,0.00165000,0.00110000,0.00080000,0.00034000,0.00019000,0.00005000,0.00002000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000};
		double[] Illuminant_D50 = new double[]{17.92359251,20.97984690,23.91155525,25.88721873,24.44787131,29.82884783,49.24662432,56.44974161,59.97316328,57.76397462,74.76665430,87.18888670,90.55862245,91.32148318,95.06664045,91.92938998,95.69666845,96.59098269,97.11313634,102.08728985,100.74727524,102.31336445,100.00000000,97.73786419,98.92449973,93.51129849,97.70746200,99.29398635,99.07048594,95.75347494,98.89672375,95.70870899,98.23790088,103.05864288,99.18921398,87.42787612,91.65706801,92.93751528,76.89465208,86.55502308,92.62591564,78.26849873,57.72160338,82.96554465,78.31323794,79.59465208,73.43831323,63.95178888,70.81215987,74.47902029};

		double[] VxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		double sPSum = 0;
		double sProduct = 0;
		double sPSum2 = 0;
		double sProduct2 = 0;
		double SPV = 0;
		//int i = 18;

		if (filter == "X") {
			for (int i = 0; i < 50; i++) {
				sProduct=VxArray[i] * xObs2Array[i] * Illuminant_D50[i];
				sPSum=sProduct+sPSum;
				sProduct2=yObs2Array[i]*Illuminant_D50[i];
				sPSum2=sProduct2+sPSum2;
			}
			SPV=sPSum/sPSum2;
		}

		if (filter == "Y") {
			for (int i = 0; i < 50; i++) {
				sProduct=VxArray[i] * yObs2Array[i] * Illuminant_D50[i];
				sPSum=sProduct+sPSum;
				sProduct2=yObs2Array[i]*Illuminant_D50[i];
				sPSum2=sProduct2+sPSum2;
			}
			SPV=sPSum/sPSum2;
		}

		if (filter == "Z") {
			for (int i = 0; i < 50; i++) {
				sProduct=VxArray[i] * zObs2Array[i] * Illuminant_D50[i];
				sPSum=sProduct+sPSum;
				sProduct2=yObs2Array[i]*Illuminant_D50[i];
				sPSum2=sProduct2+sPSum2;
			}
			SPV=sPSum/sPSum2;
		}
		return SPV;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	public double xyz2lab01(String labfilter, double x, double y, double z) {
		double[] xObs2Array = new double[]{0.00000000,0.00000000,0.00012990,0.00041490,0.00136800,0.00424300,0.01431000,0.04351000,0.13438000,0.28390000,0.34828000,0.33620000,0.29080000,0.19536000,0.09564000,0.03201000,0.00490000,0.00930000,0.06327000,0.16550000,0.29040000,0.43344990,0.59450000,0.76210000,0.91630000,1.02630000,1.06220000,1.00260000,0.85444990,0.64240000,0.44790000,0.28350000,0.16490000,0.08740000,0.04677000,0.02270000,0.01135916,0.00579035,0.00289933,0.00143997,0.00069008,0.00033230,0.00016615,0.00008308,0.00004151,0.00002067,0.00001025,0.00000509,0.00000252,0.00000125};
		double[] yObs2Array = new double[]{0.00000000,0.00000000,0.00000392,0.00001239,0.00003900,0.00012000,0.00039600,0.00121000,0.00400000,0.01160000,0.02300000,0.03800000,0.06000000,0.09098000,0.13902000,0.20802000,0.32300000,0.50300000,0.71000000,0.86200000,0.95400000,0.99495010,0.99500000,0.95200000,0.87000000,0.75700000,0.63100000,0.50300000,0.38100000,0.26500000,0.17500000,0.10700000,0.06100000,0.03200000,0.01700000,0.00821000,0.00410200,0.00209100,0.00104700,0.00052000,0.00024920,0.00012000,0.00006000,0.00003000,0.00001499,0.00000747,0.00000370,0.00000184,0.00000091,0.00000045};
		double[] zObs2Array = new double[]{0.00000000,0.00000000,0.00060610,0.00194600,0.00645000,0.02005001,0.06785001,0.20740000,0.64560000,1.38560000,1.74706000,1.77211000,1.66920000,1.28764000,0.81295010,0.46518000,0.27200000,0.15820000,0.07824999,0.04216000,0.02030000,0.00875000,0.00390000,0.00210000,0.00165000,0.00110000,0.00080000,0.00034000,0.00019000,0.00005000,0.00002000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000};
		double[] Illuminant_D50 = new double[]{17.92359251,20.97984690,23.91155525,25.88721873,24.44787131,29.82884783,49.24662432,56.44974161,59.97316328,57.76397462,74.76665430,87.18888670,90.55862245,91.32148318,95.06664045,91.92938998,95.69666845,96.59098269,97.11313634,102.08728985,100.74727524,102.31336445,100.00000000,97.73786419,98.92449973,93.51129849,97.70746200,99.29398635,99.07048594,95.75347494,98.89672375,95.70870899,98.23790088,103.05864288,99.18921398,87.42787612,91.65706801,92.93751528,76.89465208,86.55502308,92.62591564,78.26849873,57.72160338,82.96554465,78.31323794,79.59465208,73.43831323,63.95178888,70.81215987,74.47902029};

		double sPSum = 0;
		double sProduct = 0;
		double sPSum2 = 0;
		double sProduct2 = 0;

		for (int i = 0; i < 50; i++) {
			sProduct= xObs2Array[i] * Illuminant_D50[i];
			sPSum=sProduct+sPSum;
			sProduct2=yObs2Array[i]*Illuminant_D50[i];
			sPSum2=sProduct2+sPSum2;
		}
		double Xr=sPSum/sPSum2;

		sPSum = 0;
		sProduct = 0;
		sPSum2 = 0;
		sProduct2 = 0;

		for (int i = 0; i < 50; i++) {
			sProduct= zObs2Array[i] * Illuminant_D50[i];
			sPSum=sProduct+sPSum;
			sProduct2=yObs2Array[i]*Illuminant_D50[i];
			sPSum2=sProduct2+sPSum2;
		}
		double Zr=sPSum/sPSum2;

		double ciel ;
		double ciea ;
		double cieb;
		double RV = 0;
		if (labfilter == "l") {

			if (y>0.00885645) {
				ciel = 116 * (Math.pow(y/1,(0.333333))) - 16;
			}
			else {
				ciel=116*(((903.3*y)+16)/116)-16;
			}
			RV = ciel;
		}

		if (labfilter=="a") {
			if (x/Xr>0.00853957) {
				if (y>0.00885645) {
					ciea=500*((pow((x/Xr),0.333333))-pow((y/1),(0.333333)));}
				else {
					ciea=500*((pow((x/Xr),0.333333))-(((903.3*y)+16)/116));}
			}

			else {
				if (y>0.00885645) {
					ciea=500*((((903.3*x/Xr)+16)/116)-pow((y/1),(0.333333)));}
				else {
					ciea=500*((((903.3*x/Xr)+16)/116)-(((903.3*y)+16)/116));}
			}
			RV = ciea;
		}
		if (labfilter=="b") {
			if (y>0.00885645) {
				if (z/Zr>0.00885645) {
					cieb=200*((pow((y/1),0.333333))-pow((z/Zr),(0.333333)));}
				else {
					cieb=200*((pow((y/1),0.333333))-(((903.3*z/Zr)+16)/116));}
			}

			else {
				if (z/Zr>0.00885645) {
					cieb=200*((((903.3*y)+16)/116)-pow((z/Zr),(0.333333)));}
				else {
					cieb=200*((((903.3*y)+16)/116)-(((903.3*z/Zr)+16)/116));}
			}
			RV = cieb;
		}

		return Math.round( RV * 100d) / 100d;
	}

	public static double deg2Rad(double angle) {
		return angle * Math.PI / 180;
	}




	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}


}