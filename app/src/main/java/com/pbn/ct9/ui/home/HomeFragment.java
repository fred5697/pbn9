package com.pbn.ct9.ui.home;

import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;

import com.pbn.ct9.R;
import com.pbn.ct9.bean.MeasureBean;
import com.pbn.ct9.bean.ReadMeasureDataBean;
import com.pbn.ct9.bean.ReadRgbMeasureDataBean;
import com.pbn.ct9.ble.IBluetoothManager;
import com.pbn.ct9.broadcast.SpectrometerBroadcast;
import com.pbn.ct9.databinding.FragmentHomeBinding;
import com.pbn.ct9.utils.Constant;
import com.pbn.ct9.utils.MessageDialogUtil;
import com.pbn.ct9.utils.WaitDialogUtil;

import java.text.DecimalFormat;
import java.util.Arrays;

public class HomeFragment extends Fragment
{
	private FragmentHomeBinding binding;
	public final ObservableField<String> m_L_Value = new ObservableField<>("-");
	public final ObservableField<String> m_a_Value = new ObservableField<>("-");
	public final ObservableField<String> m_b_Value = new ObservableField<>("-");
	public final ObservableField<String> m_R_Value = new ObservableField<>("-");
	public final ObservableField<String> m_G_Value = new ObservableField<>("-");
	public final ObservableField<String> m_B_Value = new ObservableField<>("-");

	public final ObservableField<String> m_L2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_a2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_b2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_R2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_G2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_B2_Value = new ObservableField<>("-");

	public final ObservableField<String> m_dEab_Value = new ObservableField<>("-");
	public final ObservableField<String> m_dE00_Value = new ObservableField<>("-");

	private SpectrometerBroadcast mSpectrometerBroadcast = null;
 	public final ObservableField<String> mColorHex = new ObservableField<>("#");
	public final ObservableField<String> mColorHex2 = new ObservableField<>("#");
	public final ObservableField<String> m_SP40_Value = new ObservableField<>("-");
	public final ObservableField<String> m_SP41_Value = new ObservableField<>("-");
	public final ObservableField<String> m_SP42_Value = new ObservableField<>("-");
	public final ObservableField<String> m_Den_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenC_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenM_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenY_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenS_Value = new ObservableField<>("-");
	public final ObservableField<String> m_Hi_Value = new ObservableField<>("-");
	public final ObservableField<String> m_Den2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenC2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenM2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenY2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_DenS2_Value = new ObservableField<>("-");
	public final ObservableField<String> m_Hi2_Value = new ObservableField<>("-");
	public ObservableInt m_rgbColorInt = new ObservableInt(Color.rgb(255, 255, 255));
	public ObservableInt m_rgbColorInt2 = new ObservableInt(Color.rgb(255, 255, 255));

	public double m2L = 0;
	public double m2a = 0;
	public double m2b = 0;

	public double m1L = 0;
	public double m1a = 0;
	public double m1b = 0;

	public double total  = 0;
	/**
	 * 量測成功時儲存的數據，失敗轉為 null
	 */
	private ReadMeasureDataBean mReadMeasureDataBean;

	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View root = binding.getRoot();
		binding.setFragment(this);

		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			//MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), "開始測量！");
			final DecimalFormat format = new DecimalFormat();
			format.setMaximumFractionDigits(2);
			mSpectrometerBroadcast = new SpectrometerBroadcast(requireActivity(), new SpectrometerBroadcast.onBroadcastReceived()
			{
				@Override public void onMeasure(MeasureBean data) {
					//Log.d("HomeFragment", "onReadMeasureData: " + data.toString());

					if(data.getMeasureSuccesses())
						IBluetoothManager.getInstance().setOrder(Constant.READ_MEASURE_DATA);
					else {
						mReadMeasureDataBean = null;
						MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), getString(R.string.measure_fail));
					}
				}

				@RequiresApi(api = Build.VERSION_CODES.N)
				@Override public void onReadMeasureData(ReadMeasureDataBean data) {
					Log.d("HomeFragment", "onReadMeasureData: " + data.toString());

					if(data.getLabData() != null) {
						mReadMeasureDataBean = data;

						m_SP40_Value.set(format.format(data.getData()[0]));
						m_SP41_Value.set(format.format(data.getData()[1]));
						m_SP42_Value.set(format.format(data.getData()[2]));
						// m_Den_Value.set(format.format(getDensity(0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00, 0.00,data.getData()[0] ,data.getData()[1] ,data.getData()[2] ,data.getData()[3] ,data.getData()[4] ,data.getData()[5] ,data.getData()[6] ,data.getData()[7] ,data.getData()[8] ,data.getData()[9] ,data.getData()[10] ,data.getData()[11] ,data.getData()[12] ,data.getData()[13] ,data.getData()[14] ,data.getData()[15] ,data.getData()[16] ,data.getData()[17] ,data.getData()[18] ,data.getData()[19] ,data.getData()[20] ,data.getData()[21] ,data.getData()[22] ,data.getData()[23] ,data.getData()[24] ,data.getData()[25] ,data.getData()[26] ,data.getData()[27] ,data.getData()[28] ,data.getData()[29] ,data.getData()[30]  ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000)));


						//double[] DenMxArray = new double[]{0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00, 0.00,data.getData()[0] ,data.getData()[1] ,data.getData()[2] ,data.getData()[3] ,data.getData()[4] ,data.getData()[5] ,data.getData()[6] ,data.getData()[7] ,data.getData()[8] ,data.getData()[9] ,data.getData()[10] ,data.getData()[11] ,data.getData()[12] ,data.getData()[13] ,data.getData()[14] ,data.getData()[15] ,data.getData()[16] ,data.getData()[17] ,data.getData()[18] ,data.getData()[19] ,data.getData()[20] ,data.getData()[21] ,data.getData()[22] ,data.getData()[23] ,data.getData()[24] ,data.getData()[25] ,data.getData()[26] ,data.getData()[27] ,data.getData()[28] ,data.getData()[29] ,data.getData()[30]  ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 };
						//getDensity(0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00, 0.00,data.getData()[0] ,data.getData()[1] ,data.getData()[2] ,data.getData()[3] ,data.getData()[4] ,data.getData()[5] ,data.getData()[6] ,data.getData()[7] ,data.getData()[8] ,data.getData()[9] ,data.getData()[10] ,data.getData()[11] ,data.getData()[12] ,data.getData()[13] ,data.getData()[14] ,data.getData()[15] ,data.getData()[16] ,data.getData()[17] ,data.getData()[18] ,data.getData()[19] ,data.getData()[20] ,data.getData()[21] ,data.getData()[22] ,data.getData()[23] ,data.getData()[24] ,data.getData()[25] ,data.getData()[26] ,data.getData()[27] ,data.getData()[28] ,data.getData()[29] ,data.getData()[30]  ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000);

						//double Denx = getDensity(0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00, 0.00,data.getData()[0] ,data.getData()[1] ,data.getData()[2] ,data.getData()[3] ,data.getData()[4] ,data.getData()[5] ,data.getData()[6] ,data.getData()[7] ,data.getData()[8] ,data.getData()[9] ,data.getData()[10] ,data.getData()[11] ,data.getData()[12] ,data.getData()[13] ,data.getData()[14] ,data.getData()[15] ,data.getData()[16] ,data.getData()[17] ,data.getData()[18] ,data.getData()[19] ,data.getData()[20] ,data.getData()[21] ,data.getData()[22] ,data.getData()[23] ,data.getData()[24] ,data.getData()[25] ,data.getData()[26] ,data.getData()[27] ,data.getData()[28] ,data.getData()[29] ,data.getData()[30]  ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000);
						double[] numbers = {data.getData()[0] ,data.getData()[1] ,data.getData()[2] ,data.getData()[3] ,data.getData()[4] ,data.getData()[5] ,data.getData()[6] ,data.getData()[7] ,data.getData()[8] ,data.getData()[9] ,data.getData()[10] ,data.getData()[11] ,data.getData()[12] ,data.getData()[13] ,data.getData()[14] ,data.getData()[15] ,data.getData()[16] ,data.getData()[17] ,data.getData()[18] ,data.getData()[19] ,data.getData()[20] ,data.getData()[21] ,data.getData()[22] ,data.getData()[23] ,data.getData()[24] ,data.getData()[25] ,data.getData()[26] ,data.getData()[27] ,data.getData()[28] ,data.getData()[29] ,data.getData()[30]};
						total = Arrays.stream(numbers).sum()*0.01;
						System.out.println("Total : " + total); // Output: 17.5
						total = 0;


							double Denx = dens(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
							double DenxC = densC(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
							double DenxM = densM(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
							double DenxY = densY(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
							double DenxS = densS(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
							double DenxHi = densHi(0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00, 0.00, data.getData()[0], data.getData()[1], data.getData()[2], data.getData()[3], data.getData()[4], data.getData()[5], data.getData()[6], data.getData()[7], data.getData()[8], data.getData()[9], data.getData()[10], data.getData()[11], data.getData()[12], data.getData()[13], data.getData()[14], data.getData()[15], data.getData()[16], data.getData()[17], data.getData()[18], data.getData()[19], data.getData()[20], data.getData()[21], data.getData()[22], data.getData()[23], data.getData()[24], data.getData()[25], data.getData()[26], data.getData()[27], data.getData()[28], data.getData()[29], data.getData()[30], 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000);
							double D50X = spXYZ("X",0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00, 0.00,data.getData()[0] ,data.getData()[1] ,data.getData()[2] ,data.getData()[3] ,data.getData()[4] ,data.getData()[5] ,data.getData()[6] ,data.getData()[7] ,data.getData()[8] ,data.getData()[9] ,data.getData()[10] ,data.getData()[11] ,data.getData()[12] ,data.getData()[13] ,data.getData()[14] ,data.getData()[15] ,data.getData()[16] ,data.getData()[17] ,data.getData()[18] ,data.getData()[19] ,data.getData()[20] ,data.getData()[21] ,data.getData()[22] ,data.getData()[23] ,data.getData()[24] ,data.getData()[25] ,data.getData()[26] ,data.getData()[27] ,data.getData()[28] ,data.getData()[29] ,data.getData()[30]  ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000);
							double D50Y = spXYZ("Y",0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00, 0.00,data.getData()[0] ,data.getData()[1] ,data.getData()[2] ,data.getData()[3] ,data.getData()[4] ,data.getData()[5] ,data.getData()[6] ,data.getData()[7] ,data.getData()[8] ,data.getData()[9] ,data.getData()[10] ,data.getData()[11] ,data.getData()[12] ,data.getData()[13] ,data.getData()[14] ,data.getData()[15] ,data.getData()[16] ,data.getData()[17] ,data.getData()[18] ,data.getData()[19] ,data.getData()[20] ,data.getData()[21] ,data.getData()[22] ,data.getData()[23] ,data.getData()[24] ,data.getData()[25] ,data.getData()[26] ,data.getData()[27] ,data.getData()[28] ,data.getData()[29] ,data.getData()[30]  ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000);
							double D50Z = spXYZ("Z",0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00, 0.00,data.getData()[0] ,data.getData()[1] ,data.getData()[2] ,data.getData()[3] ,data.getData()[4] ,data.getData()[5] ,data.getData()[6] ,data.getData()[7] ,data.getData()[8] ,data.getData()[9] ,data.getData()[10] ,data.getData()[11] ,data.getData()[12] ,data.getData()[13] ,data.getData()[14] ,data.getData()[15] ,data.getData()[16] ,data.getData()[17] ,data.getData()[18] ,data.getData()[19] ,data.getData()[20] ,data.getData()[21] ,data.getData()[22] ,data.getData()[23] ,data.getData()[24] ,data.getData()[25] ,data.getData()[26] ,data.getData()[27] ,data.getData()[28] ,data.getData()[29] ,data.getData()[30]  ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.0 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000 ,0.00000000);







						double D50L = xyz2lab01("l",D50X, D50Y, D50Z);
						double D50a = xyz2lab01("a",D50X, D50Y, D50Z);
						double D50b = xyz2lab01("b",D50X, D50Y, D50Z);


						if(mBtn == "measureTargetColor") {

							m_L_Value.set(String.valueOf(D50L));
							m_a_Value.set(String.valueOf(D50a));
							m_b_Value.set(String.valueOf(D50b));

							m1L = D50L;
							m1a = D50a;
							m1b = D50b;

							m_Den_Value.set(String.valueOf(Denx));
							m_DenC_Value.set(String.valueOf(DenxC));
							m_DenM_Value.set(String.valueOf(DenxM));
							m_DenY_Value.set(String.valueOf(DenxY));
							m_DenS_Value.set(String.valueOf(DenxS));
							m_Hi_Value.set(String.valueOf(DenxHi));
							int intColor = ColorUtils.LABToColor(data.getLabData()[ 0 ], data.getLabData()[ 1 ], data.getLabData()[ 2 ]);
							String colorHex = String.format("#%06X", (0xFFFFFF & intColor));
							mColorHex.set(colorHex);

							int red = Color.red(intColor);
							int green = Color.green(intColor);
							int blue = Color.blue(intColor);
							m_R_Value.set(String.valueOf(red));
							m_G_Value.set(String.valueOf(green));
							m_B_Value.set(String.valueOf(blue));
							m_rgbColorInt.set(Color.rgb(red, green, blue));
						}
						else if (mBtn == "measureTargetColor2") {
							m_L2_Value.set(String.valueOf(D50L));
							m_a2_Value.set(String.valueOf(D50a));
							m_b2_Value.set(String.valueOf(D50b));

							m2L = D50L;
							m2a = D50a;
							m2b = D50b;

							m_Den2_Value.set(String.valueOf(Denx));
							m_DenC2_Value.set(String.valueOf(DenxC));
							m_DenM2_Value.set(String.valueOf(DenxM));
							m_DenY2_Value.set(String.valueOf(DenxY));
							m_DenS2_Value.set(String.valueOf(DenxS));
							m_Hi2_Value.set(String.valueOf(DenxHi));



							int intColor2 = ColorUtils.LABToColor(data.getLabData()[ 0 ], data.getLabData()[ 1 ], data.getLabData()[ 2 ]);
							String colorHex2 = String.format("#%06X", (0xFFFFFF & intColor2));
							mColorHex2.set(colorHex2);

							int red2 = Color.red(intColor2);
							int green2 = Color.green(intColor2);
							int blue2 = Color.blue(intColor2);
							m_R2_Value.set(String.valueOf(red2));
							m_G2_Value.set(String.valueOf(green2));
							m_B2_Value.set(String.valueOf(blue2));
							m_rgbColorInt2.set(Color.rgb(red2, green2, blue2));
						}

						double dEab = de76(m2L ,m2a,m2b,m1L ,m1a,m1b);
						m_dEab_Value.set(String.valueOf(dEab));

						double dE00 = de00(m2L ,m2a,m2b,m1L ,m1a,m1b);
						m_dE00_Value.set(String.valueOf(dE00));

//						IBluetoothManager.getInstance().setOrder(Constant.READ_RGB_MEASURE_DATA);
					}
					else {
						mReadMeasureDataBean = null;
						MessageDialogUtil.okMsgBox((AppCompatActivity) getActivity(), getString(R.string.msg_box_title), getString(R.string.measure_fail));
						mBtn = "";
					}
				}

				@Override public void onReadRgbMeasureData(ReadRgbMeasureDataBean data) {
					Log.d("HomeFragment", "onReadRgbMeasureData: " + data.toString());
				}
			});
		}

		return root;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double dens(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
	double[] DenVArray = new double[]{0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.01000000, 0.02098940, 0.08203515, 0.27989813, 0.64714262, 1.23026877, 2.21819642, 3.81944271, 6.57657837, 10.99005839, 18.87991349, 32.58367010, 50.35006088, 66.83439176, 80.35261222, 90.57326009, 97.49896377, 100.00000000, 97.49896377, 90.36494737, 79.79946873, 67.14288529, 53.82697825, 39.17418771, 27.10191632, 17.29816359, 10.30386120, 5.61047976, 3.09029543, 1.54170045, 0.79615935, 0.41879357, 0.21577444, 0.10990058, 0.05395106, 0.02697739, 0.01399587, 0.01000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000};
	double[] DenMxArray;
	if (total < 2) {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}
		else {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, 0.04449748449445699*pow((s7*0.01),2)+1.0969646792422518*(s7*0.01)+-0.0045802018463300094,0.0435473053206719*pow((s8*0.01),2)+1.043205143576784*(s8*0.01)+-0.0008283451478069535,0.18031764919678767*pow((s9*0.01),2)+1.0536652283805898*(s9*0.01)+0.0018161917663597719,0.15964517668575695*pow((s10*0.01),2)+1.0600730468428727*(s10*0.01)+0.003339577681857032,0.09580745927714485*pow((s11*0.01),2)+1.0640897404662655*(s11*0.01)+0.005872337698217471,0.06364810206354489*pow((s12*0.01),2)+1.0375625272176405*(s12*0.01)+0.006150719599038778,0.08777023929649212*pow((s13*0.01),2)+1.0024352651407027*(s13*0.01)+0.00042938356561656826,0.13326329928837083*pow((s14*0.01),2)+0.9756039698002351*(s14*0.01)+-0.007779203411893992,0.14677041370498659*pow((s15*0.01),2)+0.964424985632366*(s15*0.01)+-0.013456045841272586,0.06298313287314049*pow((s16*0.01),2)+1.0206470057290133*(s16*0.01)+-0.006593488409464791,-0.13753812183099273*pow((s17*0.01),2)+1.1605701015622119*(s17*0.01)+0.007347567315840674,-0.2582911613971735*pow((s18*0.01),2)+1.2607079941848796*(s18*0.01)+0.002129098883366361,-0.02615989775059746*pow((s19*0.01),2)+1.0923366724419663*(s19*0.01)+-0.003596062058319823,0.1479456100025586*pow((s20*0.01),2)+0.9294362034314502*(s20*0.01)+-0.00032990959884967615,0.035934330124550974*pow((s21*0.01),2)+0.9755268287385284*(s21*0.01)+0.005189397517304168,-0.0028131837362426627*pow((s22*0.01),2)+0.9992945336221309*(s22*0.01)+0.010093638711894006,0.08832965274239842*pow((s23*0.01),2)+0.9575911253035775*(s23*0.01)+0.005200859976600176,0.03550469168156484*pow((s24*0.01),2)+1.0350390292811857*(s24*0.01)+-0.004051787931130814,-0.11165151405365492*pow((s25*0.01),2)+1.1600654192199589*(s25*0.01)+-0.008616682745680249,-0.10051303693775036*pow((s26*0.01),2)+1.1395583812210373*(s26*0.01)+-0.0017252453980707526,-0.03462139986475935*pow((s27*0.01),2)+1.0715130809669517*(s27*0.01)+0.0009580192374397909,0.04078618264477882*pow((s28*0.01),2)+1.0041247132835487*(s28*0.01)+-0.0017894604079388332,0.095961400814888*pow((s29*0.01),2)+0.9587313522647314*(s29*0.01)+-0.002894035238268991,0.10869717749752078*pow((s30*0.01),2)+0.9503155720370845*(s30*0.01)+-0.0015562807495065139,0.07294371373712895*pow((s31*0.01),2)+0.9848766238056941*(s31*0.01)+-0.0002401967753373157,-0.011036617429695484*pow((s32*0.01),2)+1.0511205828115493*(s32*0.01)+0.0007287371893424769,-0.10048067587275451*pow((s33*0.01),2)+1.1248876502060874*(s33*0.01)+0.001260975703158131,-0.12209357596661863*pow((s34*0.01),2)+1.1421669319510668*(s34*0.01)+0.000979458239497387,-0.05389849583810386*pow((s35*0.01),2)+1.0892668147690294*(s35*0.01)+0.0001737877162674047,0.056986413695109844*pow((s36*0.01),2)+1.0023797272578052*(s36*0.01)+-0.000651227724841773,0.19638236802504194*pow((s37*0.01),2)+0.8934951510829301*(s37*0.01)+-0.0002004399816615908,  s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}


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
	private double densC(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
		double[] DenCArray = new double[]{0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00100000, 0.05997911, 0.44977985, 29.99162519, 100.00000000, 84.91804750, 54.95408739, 25.00345362, 10.00000000, 5.00034535, 1.49968484, 0.50003453, 0.29991625, 0.14996848, 0.05000345, 0.01000000, 0.00100000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000, 0.00000000};
		double[] DenMxArray;
		if (total < 2) {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}
		else {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, 0.04449748449445699*pow((s7*0.01),2)+1.0969646792422518*(s7*0.01)+-0.0045802018463300094,0.0435473053206719*pow((s8*0.01),2)+1.043205143576784*(s8*0.01)+-0.0008283451478069535,0.18031764919678767*pow((s9*0.01),2)+1.0536652283805898*(s9*0.01)+0.0018161917663597719,0.15964517668575695*pow((s10*0.01),2)+1.0600730468428727*(s10*0.01)+0.003339577681857032,0.09580745927714485*pow((s11*0.01),2)+1.0640897404662655*(s11*0.01)+0.005872337698217471,0.06364810206354489*pow((s12*0.01),2)+1.0375625272176405*(s12*0.01)+0.006150719599038778,0.08777023929649212*pow((s13*0.01),2)+1.0024352651407027*(s13*0.01)+0.00042938356561656826,0.13326329928837083*pow((s14*0.01),2)+0.9756039698002351*(s14*0.01)+-0.007779203411893992,0.14677041370498659*pow((s15*0.01),2)+0.964424985632366*(s15*0.01)+-0.013456045841272586,0.06298313287314049*pow((s16*0.01),2)+1.0206470057290133*(s16*0.01)+-0.006593488409464791,-0.13753812183099273*pow((s17*0.01),2)+1.1605701015622119*(s17*0.01)+0.007347567315840674,-0.2582911613971735*pow((s18*0.01),2)+1.2607079941848796*(s18*0.01)+0.002129098883366361,-0.02615989775059746*pow((s19*0.01),2)+1.0923366724419663*(s19*0.01)+-0.003596062058319823,0.1479456100025586*pow((s20*0.01),2)+0.9294362034314502*(s20*0.01)+-0.00032990959884967615,0.035934330124550974*pow((s21*0.01),2)+0.9755268287385284*(s21*0.01)+0.005189397517304168,-0.0028131837362426627*pow((s22*0.01),2)+0.9992945336221309*(s22*0.01)+0.010093638711894006,0.08832965274239842*pow((s23*0.01),2)+0.9575911253035775*(s23*0.01)+0.005200859976600176,0.03550469168156484*pow((s24*0.01),2)+1.0350390292811857*(s24*0.01)+-0.004051787931130814,-0.11165151405365492*pow((s25*0.01),2)+1.1600654192199589*(s25*0.01)+-0.008616682745680249,-0.10051303693775036*pow((s26*0.01),2)+1.1395583812210373*(s26*0.01)+-0.0017252453980707526,-0.03462139986475935*pow((s27*0.01),2)+1.0715130809669517*(s27*0.01)+0.0009580192374397909,0.04078618264477882*pow((s28*0.01),2)+1.0041247132835487*(s28*0.01)+-0.0017894604079388332,0.095961400814888*pow((s29*0.01),2)+0.9587313522647314*(s29*0.01)+-0.002894035238268991,0.10869717749752078*pow((s30*0.01),2)+0.9503155720370845*(s30*0.01)+-0.0015562807495065139,0.07294371373712895*pow((s31*0.01),2)+0.9848766238056941*(s31*0.01)+-0.0002401967753373157,-0.011036617429695484*pow((s32*0.01),2)+1.0511205828115493*(s32*0.01)+0.0007287371893424769,-0.10048067587275451*pow((s33*0.01),2)+1.1248876502060874*(s33*0.01)+0.001260975703158131,-0.12209357596661863*pow((s34*0.01),2)+1.1421669319510668*(s34*0.01)+0.000979458239497387,-0.05389849583810386*pow((s35*0.01),2)+1.0892668147690294*(s35*0.01)+0.0001737877162674047,0.056986413695109844*pow((s36*0.01),2)+1.0023797272578052*(s36*0.01)+-0.000651227724841773,0.19638236802504194*pow((s37*0.01),2)+0.8934951510829301*(s37*0.01)+-0.0002004399816615908,  s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}


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
		double[] DenMxArray;
		if (total < 2) {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}
		else {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, 0.04449748449445699*pow((s7*0.01),2)+1.0969646792422518*(s7*0.01)+-0.0045802018463300094,0.0435473053206719*pow((s8*0.01),2)+1.043205143576784*(s8*0.01)+-0.0008283451478069535,0.18031764919678767*pow((s9*0.01),2)+1.0536652283805898*(s9*0.01)+0.0018161917663597719,0.15964517668575695*pow((s10*0.01),2)+1.0600730468428727*(s10*0.01)+0.003339577681857032,0.09580745927714485*pow((s11*0.01),2)+1.0640897404662655*(s11*0.01)+0.005872337698217471,0.06364810206354489*pow((s12*0.01),2)+1.0375625272176405*(s12*0.01)+0.006150719599038778,0.08777023929649212*pow((s13*0.01),2)+1.0024352651407027*(s13*0.01)+0.00042938356561656826,0.13326329928837083*pow((s14*0.01),2)+0.9756039698002351*(s14*0.01)+-0.007779203411893992,0.14677041370498659*pow((s15*0.01),2)+0.964424985632366*(s15*0.01)+-0.013456045841272586,0.06298313287314049*pow((s16*0.01),2)+1.0206470057290133*(s16*0.01)+-0.006593488409464791,-0.13753812183099273*pow((s17*0.01),2)+1.1605701015622119*(s17*0.01)+0.007347567315840674,-0.2582911613971735*pow((s18*0.01),2)+1.2607079941848796*(s18*0.01)+0.002129098883366361,-0.02615989775059746*pow((s19*0.01),2)+1.0923366724419663*(s19*0.01)+-0.003596062058319823,0.1479456100025586*pow((s20*0.01),2)+0.9294362034314502*(s20*0.01)+-0.00032990959884967615,0.035934330124550974*pow((s21*0.01),2)+0.9755268287385284*(s21*0.01)+0.005189397517304168,-0.0028131837362426627*pow((s22*0.01),2)+0.9992945336221309*(s22*0.01)+0.010093638711894006,0.08832965274239842*pow((s23*0.01),2)+0.9575911253035775*(s23*0.01)+0.005200859976600176,0.03550469168156484*pow((s24*0.01),2)+1.0350390292811857*(s24*0.01)+-0.004051787931130814,-0.11165151405365492*pow((s25*0.01),2)+1.1600654192199589*(s25*0.01)+-0.008616682745680249,-0.10051303693775036*pow((s26*0.01),2)+1.1395583812210373*(s26*0.01)+-0.0017252453980707526,-0.03462139986475935*pow((s27*0.01),2)+1.0715130809669517*(s27*0.01)+0.0009580192374397909,0.04078618264477882*pow((s28*0.01),2)+1.0041247132835487*(s28*0.01)+-0.0017894604079388332,0.095961400814888*pow((s29*0.01),2)+0.9587313522647314*(s29*0.01)+-0.002894035238268991,0.10869717749752078*pow((s30*0.01),2)+0.9503155720370845*(s30*0.01)+-0.0015562807495065139,0.07294371373712895*pow((s31*0.01),2)+0.9848766238056941*(s31*0.01)+-0.0002401967753373157,-0.011036617429695484*pow((s32*0.01),2)+1.0511205828115493*(s32*0.01)+0.0007287371893424769,-0.10048067587275451*pow((s33*0.01),2)+1.1248876502060874*(s33*0.01)+0.001260975703158131,-0.12209357596661863*pow((s34*0.01),2)+1.1421669319510668*(s34*0.01)+0.000979458239497387,-0.05389849583810386*pow((s35*0.01),2)+1.0892668147690294*(s35*0.01)+0.0001737877162674047,0.056986413695109844*pow((s36*0.01),2)+1.0023797272578052*(s36*0.01)+-0.000651227724841773,0.19638236802504194*pow((s37*0.01),2)+0.8934951510829301*(s37*0.01)+-0.0002004399816615908,  s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}

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

		double[] DenMxArray;
		if (total < 2) {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}
		else {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, 0.04449748449445699*pow((s7*0.01),2)+1.0969646792422518*(s7*0.01)+-0.0045802018463300094,0.0435473053206719*pow((s8*0.01),2)+1.043205143576784*(s8*0.01)+-0.0008283451478069535,0.18031764919678767*pow((s9*0.01),2)+1.0536652283805898*(s9*0.01)+0.0018161917663597719,0.15964517668575695*pow((s10*0.01),2)+1.0600730468428727*(s10*0.01)+0.003339577681857032,0.09580745927714485*pow((s11*0.01),2)+1.0640897404662655*(s11*0.01)+0.005872337698217471,0.06364810206354489*pow((s12*0.01),2)+1.0375625272176405*(s12*0.01)+0.006150719599038778,0.08777023929649212*pow((s13*0.01),2)+1.0024352651407027*(s13*0.01)+0.00042938356561656826,0.13326329928837083*pow((s14*0.01),2)+0.9756039698002351*(s14*0.01)+-0.007779203411893992,0.14677041370498659*pow((s15*0.01),2)+0.964424985632366*(s15*0.01)+-0.013456045841272586,0.06298313287314049*pow((s16*0.01),2)+1.0206470057290133*(s16*0.01)+-0.006593488409464791,-0.13753812183099273*pow((s17*0.01),2)+1.1605701015622119*(s17*0.01)+0.007347567315840674,-0.2582911613971735*pow((s18*0.01),2)+1.2607079941848796*(s18*0.01)+0.002129098883366361,-0.02615989775059746*pow((s19*0.01),2)+1.0923366724419663*(s19*0.01)+-0.003596062058319823,0.1479456100025586*pow((s20*0.01),2)+0.9294362034314502*(s20*0.01)+-0.00032990959884967615,0.035934330124550974*pow((s21*0.01),2)+0.9755268287385284*(s21*0.01)+0.005189397517304168,-0.0028131837362426627*pow((s22*0.01),2)+0.9992945336221309*(s22*0.01)+0.010093638711894006,0.08832965274239842*pow((s23*0.01),2)+0.9575911253035775*(s23*0.01)+0.005200859976600176,0.03550469168156484*pow((s24*0.01),2)+1.0350390292811857*(s24*0.01)+-0.004051787931130814,-0.11165151405365492*pow((s25*0.01),2)+1.1600654192199589*(s25*0.01)+-0.008616682745680249,-0.10051303693775036*pow((s26*0.01),2)+1.1395583812210373*(s26*0.01)+-0.0017252453980707526,-0.03462139986475935*pow((s27*0.01),2)+1.0715130809669517*(s27*0.01)+0.0009580192374397909,0.04078618264477882*pow((s28*0.01),2)+1.0041247132835487*(s28*0.01)+-0.0017894604079388332,0.095961400814888*pow((s29*0.01),2)+0.9587313522647314*(s29*0.01)+-0.002894035238268991,0.10869717749752078*pow((s30*0.01),2)+0.9503155720370845*(s30*0.01)+-0.0015562807495065139,0.07294371373712895*pow((s31*0.01),2)+0.9848766238056941*(s31*0.01)+-0.0002401967753373157,-0.011036617429695484*pow((s32*0.01),2)+1.0511205828115493*(s32*0.01)+0.0007287371893424769,-0.10048067587275451*pow((s33*0.01),2)+1.1248876502060874*(s33*0.01)+0.001260975703158131,-0.12209357596661863*pow((s34*0.01),2)+1.1421669319510668*(s34*0.01)+0.000979458239497387,-0.05389849583810386*pow((s35*0.01),2)+1.0892668147690294*(s35*0.01)+0.0001737877162674047,0.056986413695109844*pow((s36*0.01),2)+1.0023797272578052*(s36*0.01)+-0.000651227724841773,0.19638236802504194*pow((s37*0.01),2)+0.8934951510829301*(s37*0.01)+-0.0002004399816615908,  s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}
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

		double[] DenMxArray;
		double[] DenMxArrayo;

		if (total < 2) {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
			DenMxArrayo = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};

		}
		else {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, 0.04449748449445699*pow((s7*0.01),2)+1.0969646792422518*(s7*0.01)+-0.0045802018463300094,0.0435473053206719*pow((s8*0.01),2)+1.043205143576784*(s8*0.01)+-0.0008283451478069535,0.18031764919678767*pow((s9*0.01),2)+1.0536652283805898*(s9*0.01)+0.0018161917663597719,0.15964517668575695*pow((s10*0.01),2)+1.0600730468428727*(s10*0.01)+0.003339577681857032,0.09580745927714485*pow((s11*0.01),2)+1.0640897404662655*(s11*0.01)+0.005872337698217471,0.06364810206354489*pow((s12*0.01),2)+1.0375625272176405*(s12*0.01)+0.006150719599038778,0.08777023929649212*pow((s13*0.01),2)+1.0024352651407027*(s13*0.01)+0.00042938356561656826,0.13326329928837083*pow((s14*0.01),2)+0.9756039698002351*(s14*0.01)+-0.007779203411893992,0.14677041370498659*pow((s15*0.01),2)+0.964424985632366*(s15*0.01)+-0.013456045841272586,0.06298313287314049*pow((s16*0.01),2)+1.0206470057290133*(s16*0.01)+-0.006593488409464791,-0.13753812183099273*pow((s17*0.01),2)+1.1605701015622119*(s17*0.01)+0.007347567315840674,-0.2582911613971735*pow((s18*0.01),2)+1.2607079941848796*(s18*0.01)+0.002129098883366361,-0.02615989775059746*pow((s19*0.01),2)+1.0923366724419663*(s19*0.01)+-0.003596062058319823,0.1479456100025586*pow((s20*0.01),2)+0.9294362034314502*(s20*0.01)+-0.00032990959884967615,0.035934330124550974*pow((s21*0.01),2)+0.9755268287385284*(s21*0.01)+0.005189397517304168,-0.0028131837362426627*pow((s22*0.01),2)+0.9992945336221309*(s22*0.01)+0.010093638711894006,0.08832965274239842*pow((s23*0.01),2)+0.9575911253035775*(s23*0.01)+0.005200859976600176,0.03550469168156484*pow((s24*0.01),2)+1.0350390292811857*(s24*0.01)+-0.004051787931130814,-0.11165151405365492*pow((s25*0.01),2)+1.1600654192199589*(s25*0.01)+-0.008616682745680249,-0.10051303693775036*pow((s26*0.01),2)+1.1395583812210373*(s26*0.01)+-0.0017252453980707526,-0.03462139986475935*pow((s27*0.01),2)+1.0715130809669517*(s27*0.01)+0.0009580192374397909,0.04078618264477882*pow((s28*0.01),2)+1.0041247132835487*(s28*0.01)+-0.0017894604079388332,0.095961400814888*pow((s29*0.01),2)+0.9587313522647314*(s29*0.01)+-0.002894035238268991,0.10869717749752078*pow((s30*0.01),2)+0.9503155720370845*(s30*0.01)+-0.0015562807495065139,0.07294371373712895*pow((s31*0.01),2)+0.9848766238056941*(s31*0.01)+-0.0002401967753373157,-0.011036617429695484*pow((s32*0.01),2)+1.0511205828115493*(s32*0.01)+0.0007287371893424769,-0.10048067587275451*pow((s33*0.01),2)+1.1248876502060874*(s33*0.01)+0.001260975703158131,-0.12209357596661863*pow((s34*0.01),2)+1.1421669319510668*(s34*0.01)+0.000979458239497387,-0.05389849583810386*pow((s35*0.01),2)+1.0892668147690294*(s35*0.01)+0.0001737877162674047,0.056986413695109844*pow((s36*0.01),2)+1.0023797272578052*(s36*0.01)+-0.000651227724841773,0.19638236802504194*pow((s37*0.01),2)+0.8934951510829301*(s37*0.01)+-0.0002004399816615908,  s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
			DenMxArrayo = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};

		}
		double[] Denthird = new double[]{ s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01};




		double sPSum = 0;
		double sProduct = 0;
		//int i = 18;

		int largest = 0;
		int hiIndex = 0;
		for (int j = 1; j < DenMxArray.length; j ++) {
			if (DenMxArray[j] > DenMxArray[largest]) largest = j;
		}
		hiIndex = (largest + 34);
		System.out.println("Element at Hi index " + hiIndex);

		int lowest = largest;
		int loIndex = 0;
		double spmax=100;

		for (int k = 6; k < 37; k ++) {
			//System.out.println(k +" "+ DenMxArray[k]);
		}
		System.out.println("gutter");

		for (int k = 6; k < 37; k ++) {
			//System.out.println(k +" "+ DenMxArrayo[k]);
		}

		for (int k = 6; k < 37; k ++) {

			if (DenMxArray[k] <= spmax) {
				spmax = DenMxArray[k];
				//System.out.println("spmax " + spmax);
				loIndex = k;
			}
		}
		System.out.println("loIndex " + loIndex);
		//loIndex = (lowest + 34);
		System.out.println("Element at loIndex index " + DenMxArray[loIndex]);


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

//		DenSSArray[indexLo-36] = 3.0388;
//		DenSSArray[indexLo-35] = 7.0388;
//		DenSSArray[indexLo-34] = 14.0388;
//		DenSSArray[indexLo-33] = 7.0388;
//		DenSSArray[indexLo-32] = 3.0388;

/*
		DenSSArray[indexLo-36] = 1.0388;
		DenSSArray[indexLo-35] = 5.0388;
		DenSSArray[indexLo-34] = 14.0388;
		DenSSArray[indexLo-33] = 5.0388;
		DenSSArray[indexLo-32] = 1.0388;
*/


		for (int i = 0; i < 50; i++) {
			sProduct = DenMxArray[i] * DenSSArray[i];
			sPSum = sProduct + sPSum;
		}

		double sumV = Arrays.stream(DenSSArray).sum();
		//double denSS =-log10(sPSum/(sumV));
		//double denSS =-log10(DenMxArray[loIndex]);
		//System.out.println("Spot density " + denSS);


		Arrays.sort(Denthird);
		double[] thirdLowest = Arrays.copyOfRange(Denthird, 2, 3);
		System.out.println("Third lowest element: " + Arrays.toString(thirdLowest));


		double denSS =-log10(Denthird[2]);
		return Math.round(denSS * 100d) / 100d;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double densHi(double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {

		double[] DenMxArray;
		if (total < 2) {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}
		else {
			DenMxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, 0.04449748449445699*pow((s7*0.01),2)+1.0969646792422518*(s7*0.01)+-0.0045802018463300094,0.0435473053206719*pow((s8*0.01),2)+1.043205143576784*(s8*0.01)+-0.0008283451478069535,0.18031764919678767*pow((s9*0.01),2)+1.0536652283805898*(s9*0.01)+0.0018161917663597719,0.15964517668575695*pow((s10*0.01),2)+1.0600730468428727*(s10*0.01)+0.003339577681857032,0.09580745927714485*pow((s11*0.01),2)+1.0640897404662655*(s11*0.01)+0.005872337698217471,0.06364810206354489*pow((s12*0.01),2)+1.0375625272176405*(s12*0.01)+0.006150719599038778,0.08777023929649212*pow((s13*0.01),2)+1.0024352651407027*(s13*0.01)+0.00042938356561656826,0.13326329928837083*pow((s14*0.01),2)+0.9756039698002351*(s14*0.01)+-0.007779203411893992,0.14677041370498659*pow((s15*0.01),2)+0.964424985632366*(s15*0.01)+-0.013456045841272586,0.06298313287314049*pow((s16*0.01),2)+1.0206470057290133*(s16*0.01)+-0.006593488409464791,-0.13753812183099273*pow((s17*0.01),2)+1.1605701015622119*(s17*0.01)+0.007347567315840674,-0.2582911613971735*pow((s18*0.01),2)+1.2607079941848796*(s18*0.01)+0.002129098883366361,-0.02615989775059746*pow((s19*0.01),2)+1.0923366724419663*(s19*0.01)+-0.003596062058319823,0.1479456100025586*pow((s20*0.01),2)+0.9294362034314502*(s20*0.01)+-0.00032990959884967615,0.035934330124550974*pow((s21*0.01),2)+0.9755268287385284*(s21*0.01)+0.005189397517304168,-0.0028131837362426627*pow((s22*0.01),2)+0.9992945336221309*(s22*0.01)+0.010093638711894006,0.08832965274239842*pow((s23*0.01),2)+0.9575911253035775*(s23*0.01)+0.005200859976600176,0.03550469168156484*pow((s24*0.01),2)+1.0350390292811857*(s24*0.01)+-0.004051787931130814,-0.11165151405365492*pow((s25*0.01),2)+1.1600654192199589*(s25*0.01)+-0.008616682745680249,-0.10051303693775036*pow((s26*0.01),2)+1.1395583812210373*(s26*0.01)+-0.0017252453980707526,-0.03462139986475935*pow((s27*0.01),2)+1.0715130809669517*(s27*0.01)+0.0009580192374397909,0.04078618264477882*pow((s28*0.01),2)+1.0041247132835487*(s28*0.01)+-0.0017894604079388332,0.095961400814888*pow((s29*0.01),2)+0.9587313522647314*(s29*0.01)+-0.002894035238268991,0.10869717749752078*pow((s30*0.01),2)+0.9503155720370845*(s30*0.01)+-0.0015562807495065139,0.07294371373712895*pow((s31*0.01),2)+0.9848766238056941*(s31*0.01)+-0.0002401967753373157,-0.011036617429695484*pow((s32*0.01),2)+1.0511205828115493*(s32*0.01)+0.0007287371893424769,-0.10048067587275451*pow((s33*0.01),2)+1.1248876502060874*(s33*0.01)+0.001260975703158131,-0.12209357596661863*pow((s34*0.01),2)+1.1421669319510668*(s34*0.01)+0.000979458239497387,-0.05389849583810386*pow((s35*0.01),2)+1.0892668147690294*(s35*0.01)+0.0001737877162674047,0.056986413695109844*pow((s36*0.01),2)+1.0023797272578052*(s36*0.01)+-0.000651227724841773,0.19638236802504194*pow((s37*0.01),2)+0.8934951510829301*(s37*0.01)+-0.0002004399816615908,  s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
		}

		int largest = 0;
		int hiIndex = 0;
		for (int j = 1; j < DenMxArray.length; j ++) {
			if (DenMxArray[j] > DenMxArray[largest]) largest = j;
		}
		hiIndex = (largest + 34);
		//System.out.println("Element at largest index " + hiIndex);

		return hiIndex * 10;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private double de76(double L1, double a1, double b1, double L2, double a2, double b2) {

	double deCal = sqrt(Math.pow((L1 - L2),2)+pow((a1-a2),2)+pow((b1-b2),2));
		return Math.round(deCal * 100d) / 100d;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
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
	public double spXYZ(String filter, double s1, double s2, double s3, double s4, double s5, double s6, double s7, double s8, double s9, double s10, double s11, double s12, double s13, double s14, double s15, double s16, double s17, double s18, double s19, double s20, double s21, double s22, double s23, double s24, double s25, double s26, double s27, double s28, double s29, double s30, double s31, double s32, double s33, double s34, double s35, double s36, double s37, double s38, double s39, double s40, double s41, double s42, double s43, double s44, double s45, double s46, double s47, double s48, double s49, double s50) {
		double[] xObs2Array = new double[]{0.00000000,0.00000000,0.00012990,0.00041490,0.00136800,0.00424300,0.01431000,0.04351000,0.13438000,0.28390000,0.34828000,0.33620000,0.29080000,0.19536000,0.09564000,0.03201000,0.00490000,0.00930000,0.06327000,0.16550000,0.29040000,0.43344990,0.59450000,0.76210000,0.91630000,1.02630000,1.06220000,1.00260000,0.85444990,0.64240000,0.44790000,0.28350000,0.16490000,0.08740000,0.04677000,0.02270000,0.01135916,0.00579035,0.00289933,0.00143997,0.00069008,0.00033230,0.00016615,0.00008308,0.00004151,0.00002067,0.00001025,0.00000509,0.00000252,0.00000125};
		double[] yObs2Array = new double[]{0.00000000,0.00000000,0.00000392,0.00001239,0.00003900,0.00012000,0.00039600,0.00121000,0.00400000,0.01160000,0.02300000,0.03800000,0.06000000,0.09098000,0.13902000,0.20802000,0.32300000,0.50300000,0.71000000,0.86200000,0.95400000,0.99495010,0.99500000,0.95200000,0.87000000,0.75700000,0.63100000,0.50300000,0.38100000,0.26500000,0.17500000,0.10700000,0.06100000,0.03200000,0.01700000,0.00821000,0.00410200,0.00209100,0.00104700,0.00052000,0.00024920,0.00012000,0.00006000,0.00003000,0.00001499,0.00000747,0.00000370,0.00000184,0.00000091,0.00000045};
		double[] zObs2Array = new double[]{0.00000000,0.00000000,0.00060610,0.00194600,0.00645000,0.02005001,0.06785001,0.20740000,0.64560000,1.38560000,1.74706000,1.77211000,1.66920000,1.28764000,0.81295010,0.46518000,0.27200000,0.15820000,0.07824999,0.04216000,0.02030000,0.00875000,0.00390000,0.00210000,0.00165000,0.00110000,0.00080000,0.00034000,0.00019000,0.00005000,0.00002000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000,0.00000000};
		double[] Illuminant_D50 = new double[]{17.92359251,20.97984690,23.91155525,25.88721873,24.44787131,29.82884783,49.24662432,56.44974161,59.97316328,57.76397462,74.76665430,87.18888670,90.55862245,91.32148318,95.06664045,91.92938998,95.69666845,96.59098269,97.11313634,102.08728985,100.74727524,102.31336445,100.00000000,97.73786419,98.92449973,93.51129849,97.70746200,99.29398635,99.07048594,95.75347494,98.89672375,95.70870899,98.23790088,103.05864288,99.18921398,87.42787612,91.65706801,92.93751528,76.89465208,86.55502308,92.62591564,78.26849873,57.72160338,82.96554465,78.31323794,79.59465208,73.43831323,63.95178888,70.81215987,74.47902029};
		System.out.println("Total 576 : " + total); // Output: 17.5

		double[] VxArray;
		if (total < 2) {
			VxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
			System.out.println("Total <4 " + total); // Output: 17.5
		}
		else {
			VxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, 0.04449748449445699*pow((s7*0.01),2)+1.0969646792422518*(s7*0.01)+-0.0045802018463300094,0.0435473053206719*pow((s8*0.01),2)+1.043205143576784*(s8*0.01)+-0.0008283451478069535,0.18031764919678767*pow((s9*0.01),2)+1.0536652283805898*(s9*0.01)+0.0018161917663597719,0.15964517668575695*pow((s10*0.01),2)+1.0600730468428727*(s10*0.01)+0.003339577681857032,0.09580745927714485*pow((s11*0.01),2)+1.0640897404662655*(s11*0.01)+0.005872337698217471,0.06364810206354489*pow((s12*0.01),2)+1.0375625272176405*(s12*0.01)+0.006150719599038778,0.08777023929649212*pow((s13*0.01),2)+1.0024352651407027*(s13*0.01)+0.00042938356561656826,0.13326329928837083*pow((s14*0.01),2)+0.9756039698002351*(s14*0.01)+-0.007779203411893992,0.14677041370498659*pow((s15*0.01),2)+0.964424985632366*(s15*0.01)+-0.013456045841272586,0.06298313287314049*pow((s16*0.01),2)+1.0206470057290133*(s16*0.01)+-0.006593488409464791,-0.13753812183099273*pow((s17*0.01),2)+1.1605701015622119*(s17*0.01)+0.007347567315840674,-0.2582911613971735*pow((s18*0.01),2)+1.2607079941848796*(s18*0.01)+0.002129098883366361,-0.02615989775059746*pow((s19*0.01),2)+1.0923366724419663*(s19*0.01)+-0.003596062058319823,0.1479456100025586*pow((s20*0.01),2)+0.9294362034314502*(s20*0.01)+-0.00032990959884967615,0.035934330124550974*pow((s21*0.01),2)+0.9755268287385284*(s21*0.01)+0.005189397517304168,-0.0028131837362426627*pow((s22*0.01),2)+0.9992945336221309*(s22*0.01)+0.010093638711894006,0.08832965274239842*pow((s23*0.01),2)+0.9575911253035775*(s23*0.01)+0.005200859976600176,0.03550469168156484*pow((s24*0.01),2)+1.0350390292811857*(s24*0.01)+-0.004051787931130814,-0.11165151405365492*pow((s25*0.01),2)+1.1600654192199589*(s25*0.01)+-0.008616682745680249,-0.10051303693775036*pow((s26*0.01),2)+1.1395583812210373*(s26*0.01)+-0.0017252453980707526,-0.03462139986475935*pow((s27*0.01),2)+1.0715130809669517*(s27*0.01)+0.0009580192374397909,0.04078618264477882*pow((s28*0.01),2)+1.0041247132835487*(s28*0.01)+-0.0017894604079388332,0.095961400814888*pow((s29*0.01),2)+0.9587313522647314*(s29*0.01)+-0.002894035238268991,0.10869717749752078*pow((s30*0.01),2)+0.9503155720370845*(s30*0.01)+-0.0015562807495065139,0.07294371373712895*pow((s31*0.01),2)+0.9848766238056941*(s31*0.01)+-0.0002401967753373157,-0.011036617429695484*pow((s32*0.01),2)+1.0511205828115493*(s32*0.01)+0.0007287371893424769,-0.10048067587275451*pow((s33*0.01),2)+1.1248876502060874*(s33*0.01)+0.001260975703158131,-0.12209357596661863*pow((s34*0.01),2)+1.1421669319510668*(s34*0.01)+0.000979458239497387,-0.05389849583810386*pow((s35*0.01),2)+1.0892668147690294*(s35*0.01)+0.0001737877162674047,0.056986413695109844*pow((s36*0.01),2)+1.0023797272578052*(s36*0.01)+-0.000651227724841773,0.19638236802504194*pow((s37*0.01),2)+0.8934951510829301*(s37*0.01)+-0.0002004399816615908,  s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
			System.out.println("Total>4 " + total); // Output: 17.5
		}


		//double[] VxArray = new double[]{s1 * 0.01, s2 * 0.01, s3 * 0.01, s4 * 0.01, s5 * 0.01, s6 * 0.01, s7 * 0.01, s8 * 0.01, s9 * 0.01, s10 * 0.01, s11 * 0.01, s12 * 0.01, s13 * 0.01, s14 * 0.01, s15 * 0.01, s16 * 0.01, s17 * 0.01, s18 * 0.01, s19 * 0.01, s20 * 0.01, s21 * 0.01, s22 * 0.01, s23 * 0.01, s24 * 0.01, s25 * 0.01, s26 * 0.01, s27 * 0.01, s28 * 0.01, s29 * 0.01, s30 * 0.01, s31 * 0.01, s32 * 0.01, s33 * 0.01, s34 * 0.01, s35 * 0.01, s36 * 0.01, s37 * 0.01, s38 * 0.01, s39 * 0.01, s40 * 0.01, s41 * 0.01, s42 * 0.01, s43 * 0.01, s44 * 0.01, s45 * 0.01, s46 * 0.01, s47 * 0.01, s48 * 0.01, s49 * 0.01, s50 * 0.01};
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
		return Math.round(SPV * 10000d) / 10000d;
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
		if(mSpectrometerBroadcast != null)
			mSpectrometerBroadcast.unregisterReceiver();
		binding = null;
	}


	private String mBtn = "";
	public void measureTargetColor() {
		if(IBluetoothManager.getInstance().connect_init && IBluetoothManager.getInstance().isConnected()) {
			WaitDialogUtil.show((AppCompatActivity) getActivity(), "測量中x", 2000, null);
			IBluetoothManager.getInstance().setOrder(Constant.MEASURE);

			mBtn = "measureTargetColor";
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


	
}