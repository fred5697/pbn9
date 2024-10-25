package com.pbn.ct9;

import android.app.Application;

import com.clj.fastble.BleManager;
import com.pbn.ct9.ble.IBluetoothManager;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.util.TextInfo;



public class AppStart extends Application
{

	private String btVariable;
	public String getBTVariable() {
		return btVariable;
	}
	public void setBTVariable(String btVariable) {
		this.btVariable = btVariable;
	}

	private String CTL;
	public String getCTL() {
		return CTL;
	}
	public void setCTL(String CTL) {
		this.CTL = CTL;
	}

	private String CTa;
	public String getCTa() {
		return CTa;
	}
	public void setCTa(String CTa) {
		this.CTa = CTa;
	}

	private String CTb;
	public String getCTb() {
		return CTb;
	}
	public void setCTb(String CTb) {
		this.CTb = CTb;
	}

	private String MTL;
	public String getMTL() {
		return MTL;
	}
	public void setMTL(String MTL) {
		this.MTL = MTL;
	}

	private String MTa;
	public String getMTa() {
		return MTa;
	}
	public void setMTa(String MTa) {
		this.MTa = MTa;
	}

	private String MTb;
	public String getMTb() {
		return MTb;
	}
	public void setMTb(String MTb) {
		this.MTb = MTb;
	}


	private String YTL;
	public String getYTL() {
		return YTL;
	}
	public void setYTL(String YTL) {
		this.YTL = YTL;
	}

	private String YTa;
	public String getYTa() {
		return YTa;
	}
	public void setYTa(String YTa) {
		this.YTa = YTa;
	}

	private String YTb;
	public String getYTb() {
		return YTb;
	}
	public void setYTb(String YTb) {
		this.YTb = YTb;
	}

	private String KTL;
	public String getKTL() {
		return KTL;
	}
	public void setKTL(String KTL) {
		this.KTL = KTL;
	}

	private String KTa;
	public String getKTa() {
		return KTa;
	}
	public void setKTa(String KTa) {
		this.KTa = KTa;
	}

	private String KTb;
	public String getKTb() {
		return KTb;
	}
	public void setKTb(String KTb) {
		this.KTb = KTb;
	}

	private String CTd;
	public String getCTd() {
		return CTd;
	}
	public void setCTd(String CTd) {
		this.CTd = CTd;
	}

	private String MTd;
	public String getMTd() {
		return MTd;
	}
	public void setMTd(String MTd) {
		this.MTd = MTd;
	}

	private String YTd;
	public String getYTd() {
		return YTd;
	}
	public void setYTd(String YTd) {
		this.YTd = YTd;
	}

	private String KTd;
	public String getKTd() {
		return KTd;
	}
	public void setKTd(String KTd) {
		this.KTd = KTd;
	}



	private String CmL;
	public String getCmL() {
		return CmL;
	}
	public void setCmL(String CmL) {
		this.CmL = CmL;
	}

	private String Cma;
	public String getCma() {
		return Cma;
	}
	public void setCma(String Cma) {
		this.Cma = Cma;
	}

	private String Cmb;
	public String getCmb() {
		return Cmb;
	}
	public void setCmb(String Cmb) {
		this.Cmb = Cmb;
	}

	private String CpL;
	public String getCpL() {
		return CpL;
	}
	public void setCpL(String CpL) {
		this.CpL = CpL;
	}

	private String Cpa;
	public String getCpa() {
		return Cpa;
	}
	public void setCpa(String Cpa) {
		this.Cpa = Cpa;
	}

	private String Cpb;
	public String getCpb() {
		return Cpb;
	}
	public void setCpb(String Cpb) {
		this.Cpb = Cpb;
	}

	private String MmL;
	public String getMmL() {
		return MmL;
	}
	public void setMmL(String MmL) {
		this.MmL = MmL;
	}

	private String Mma;
	public String getMma() {
		return Mma;
	}
	public void setMma(String Mma) {
		this.Mma = Mma;
	}

	private String Mmb;
	public String getMmb() {
		return Mmb;
	}
	public void setMmb(String Mmb) {
		this.Mmb = Mmb;
	}

	private String MpL;
	public String getMpL() {
		return MpL;
	}
	public void setMpL(String MpL) {
		this.MpL = MpL;
	}

	private String Mpa;
	public String getMpa() {
		return Mpa;
	}
	public void setMpa(String Mpa) {
		this.Mpa = Mpa;
	}

	private String Mpb;
	public String getMpb() {
		return Mpb;
	}
	public void setMpb(String Mpb) {
		this.Mpb = Mpb;
	}


	private String YmL;
	public String getYmL() {
		return YmL;
	}
	public void setYmL(String YmL) {
		this.YmL = YmL;
	}

	private String Yma;
	public String getYma() {
		return Yma;
	}
	public void setYma(String Yma) {
		this.Yma = Yma;
	}

	private String Ymb;
	public String getYmb() {
		return Ymb;
	}
	public void setYmb(String Ymb) {
		this.Ymb = Ymb;
	}

	private String YpL;
	public String getYpL() {
		return YpL;
	}
	public void setYpL(String YpL) {
		this.YpL = YpL;
	}

	private String Ypa;
	public String getYpa() {
		return Ypa;
	}
	public void setYpa(String Ypa) {
		this.Ypa = Ypa;
	}

	private String Ypb;
	public String getYpb() {
		return Ypb;
	}
	public void setYpb(String Ypb) {
		this.Ypb = Ypb;
	}

	private String KmL;
	public String getKmL() {
		return KmL;
	}
	public void setKmL(String KmL) {
		this.KmL = KmL;
	}

	private String Kma;
	public String getKma() {
		return Kma;
	}
	public void setKma(String Kma) {
		this.Kma = Kma;
	}

	private String Kmb;
	public String getKmb() {
		return Kmb;
	}
	public void setKmb(String Kmb) {
		this.Kmb = Kmb;
	}

	private String KpL;
	public String getKpL() {
		return KpL;
	}
	public void setKpL(String KpL) {
		this.KpL = KpL;
	}

	private String Kpa;
	public String getKpa() {
		return Kpa;
	}
	public void setKpa(String Kpa) {
		this.Kpa = Kpa;
	}

	private String Kpb;
	public String getKpb() {
		return Kpb;
	}
	public void setKpb(String Kpb) {
		this.Kpb = Kpb;
	}

	private String Cmd;
	public String getCmd() {
		return Cmd;
	}
	public void setCmd(String Cmd) {
		this.Cmd = Cmd;
	}

	private String Cpd;
	public String getCpd() {
		return Cpd;
	}
	public void setCpd(String Cpd) {
		this.Cpd = Cpd;
	}

	private String Cmde;
	public String getCmde() {
		return Cmde;
	}
	public void setCmde(String Cmde) {
		this.Cmde = Cmde;
	}

	private String Cpde;
	public String getCpde() {
		return Cpde;
	}
	public void setCpde(String Cpde) {
		this.Cpde = Cpde;
	}

	private String Cop;
	public String getCop() {
		return Cop;
	}
	public void setCop(String Cop) {
		this.Cop = Cop;
	}

	private String Csn;
	public String getCsn() {
		return Csn;
	}
	public void setCsn(String Csn) {
		this.Csn = Csn;
	}

	private String Msn;
	public String getMsn() {
		return Msn;
	}
	public void setMsn(String Msn) {
		this.Msn = Msn;
	}

	private String Ysn;
	public String getYsn() {
		return Ysn;
	}
	public void setYsn(String Ysn) {
		this.Ysn = Ysn;
	}

	private String Ksn;
	public String getKsn() {
		return Ksn;
	}
	public void setKsn(String Ksn) {
		this.Ksn = Ksn;
	}

	private String Mmd;
	public String getMmd() {
		return Mmd;
	}
	public void setMmd(String Mmd) {
		this.Mmd = Mmd;
	}

	private String Mpd;
	public String getMpd() {
		return Mpd;
	}
	public void setMpd(String Mpd) {
		this.Mpd = Mpd;
	}

	private String Mmde;
	public String getMmde() {
		return Mmde;
	}
	public void setMmde(String Mmde) {
		this.Mmde = Mmde;
	}

	private String Mpde;
	public String getMpde() {
		return Mpde;
	}
	public void setMpde(String Mpde) {
		this.Mpde = Mpde;
	}

	private String Mop;
	public String getMop() {
		return Mop;
	}
	public void setMop(String Mop) {
		this.Mop = Mop;
	}

	private String Ymd;
	public String getYmd() {
		return Ymd;
	}
	public void setYmd(String Ymd) {
		this.Ymd = Ymd;
	}

	private String Ypd;
	public String getYpd() {
		return Ypd;
	}
	public void setYpd(String Ypd) {
		this.Ypd = Ypd;
	}

	private String Ymde;
	public String getYmde() {
		return Ymde;
	}
	public void setYmde(String Ymde) {
		this.Ymde = Ymde;
	}

	private String Ypde;
	public String getYpde() {
		return Ypde;
	}
	public void setYpde(String Ypde) {
		this.Ypde = Ypde;
	}

	private String Yop;
	public String getYop() {
		return Yop;
	}
	public void setYop(String Yop) {
		this.Yop = Yop;
	}



	private String Kmd;
	public String getKmd() {
		return Kmd;
	}
	public void setKmd(String Kmd) {
		this.Kmd = Kmd;
	}

	private String Kpd;
	public String getKpd() {
		return Kpd;
	}
	public void setKpd(String Kpd) {
		this.Kpd = Kpd;
	}

	private String Kmde;
	public String getKmde() {
		return Kmde;
	}
	public void setKmde(String Kmde) {
		this.Kmde = Kmde;
	}

	private String Kpde;
	public String getKpde() {
		return Kpde;
	}
	public void setKpde(String Kpde) {
		this.Kpde = Kpde;
	}

	private String Kop;
	public String getKop() {
		return Kop;
	}
	public void setKop(String Kop) {
		this.Kop = Kop;
	}

	private String GTL;
	public String getGTL() {
		return GTL;
	}
	public void setGTL(String GTL) {
		this.GTL = GTL;
	}

	private String GTa;
	public String getGTa() {
		return GTa;
	}
	public void setGTa(String GTa) {
		this.GTa = GTa;
	}

	private String GTb;
	public String getGTb() {
		return GTb;
	}
	public void setGTb(String GTb) {
		this.GTb = GTb;
	}

	private String GmL;
	public String getGmL() {
		return GmL;
	}
	public void setGmL(String GmL) {
		this.GmL = GmL;
	}

	private String Gma;
	public String getGma() {
		return Gma;
	}
	public void setGma(String Gma) {
		this.Gma = Gma;
	}

	private String Gmb;
	public String getGmb() {
		return Gmb;
	}
	public void setGmb(String Gmb) {
		this.Gmb = Gmb;
	}

	private String DeL;
	public String getDeL() {
		return DeL;
	}
	public void setDeL(String DeL) {
		this.DeL = DeL;
	}

	private String Dea;
	public String getDea() {
		return Dea;
	}
	public void setDea(String Dea) {
		this.Dea = Dea;
	}

	private String Deb;
	public String getDeb() {
		return Deb;
	}
	public void setDeb(String Deb) {
		this.Deb = Deb;
	}

	private String Def;
	public String getDef() {
		return Def;
	}
	public void setDef(String Def) {
		this.Def = Def;
	}

	private String Gmde;
	public String getGmde() {
		return Gmde;
	}
	public void setGmde(String Gmde) {
		this.Gmde = Gmde;
	}

	private String Gcsn;
	public String getGcsn() {
		return Gcsn;
	}
	public void setGcsn(String Gcsn) {
		this.Gcsn = Gcsn;
	}

	private String Gmsn;
	public String getGmsn() {
		return Gmsn;
	}
	public void setGmsn(String Gmsn) {
		this.Gmsn = Gmsn;
	}

	private String Gysn;
	public String getGysn() {
		return Gysn;
	}
	public void setGysn(String Gysn) {
		this.Gysn = Gysn;
	}

	private String Gcop;
	public String getGcop() {
		return Gcop;
	}
	public void setGcop(String Gcop) {
		this.Gcop = Gcop;
	}

	private String Gmop;
	public String getGmop() {
		return Gmop;
	}
	public void setGmop(String Gmop) {
		this.Gmop = Gmop;
	}

	private String Gyop;
	public String getGyop() {
		return Gyop;
	}
	public void setGyop(String Gyop) {
		this.Gyop = Gyop;
	}

	@Override public void onCreate() {
		super.onCreate();
		
		IBluetoothManager.getInstance().init(this);
		BleManager.getInstance().init(this);
		BleManager.getInstance()
				.enableLog(true)
				.setReConnectCount(3, 5000)
				.setSplitWriteNum(244)
				.setConnectOverTime(30000)
				.setOperateTimeout(40000);
		
		DialogSettings.cancelable = false;
		DialogSettings.buttonTextInfo = new TextInfo().setFontColor(R.color.purple_500);
		DialogSettings.init();
	}
	
}
