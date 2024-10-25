package com.pbn.ct9.ui.memberCenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.pbn.ct9.R;
import com.pbn.ct9.bean.BluetoothBean;
import com.pbn.ct9.ble.IBluetoothManager;
import com.pbn.ct9.databinding.ActivityBleDeviceSearchBinding;
import com.pbn.ct9.ui.adapter.BluetoothInfoAdapter;
import com.pbn.ct9.utils.WaitDialogUtil;
import com.haoge.easyandroid.easy.EasyPermissions;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BleDeviceSearch extends AppCompatActivity
{
	private ActivityBleDeviceSearchBinding binding;
	private final ObservableField<String> mMsg = new ObservableField<>("beforeInit");
	
	private BluetoothInfoAdapter bluetoothInfoAdapter;
	private final List<BluetoothBean> bluetoothBeanList = new ArrayList<>();
	private BluetoothClient mClient;
	private SearchRequest request;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_ble_device_search);
		binding.setActivityMsg(mMsg); //綁定 Layout 上 Data 的成員
//		binding.setActivity(this);
		
		binding.getActivityMsg().set("onCreate");
		acceptPermissions();
		initBluetoothInfoAdapter();
		mClient = new BluetoothClient(this); //需搭配 acceptPermissions()
		request = new SearchRequest.Builder()
								.searchBluetoothLeDevice(3500, 1)
//				.searchBluetoothClassicDevice(3000, 2)
				.build();
		
		search();
		mMsg.set("BluetoothSearch");
	}
	
	@Override protected void onDestroy() {
		super.onDestroy();
		mClient.stopSearch();
	}
	
	/**
	 * 搜尋藍芽設備。找到後新增到 List
	 */
	public void search() {
//		if(mClient.isBluetoothOpened())
		{
			WaitDialogUtil.show(BleDeviceSearch.this, getString(R.string.search_device));
			WaitDialogUtil.dismiss(2000);
			mClient.search(request, new SearchResponse()
			{
				@Override
				public void onSearchStarted() {}
				
				@SuppressLint("NotifyDataSetChanged") @Override
				public void onDeviceFounded(SearchResult device) {
					Beacon beacon = new Beacon(device.scanRecord);
					BluetoothBean bluetoothBean = new BluetoothBean();
					bluetoothBean.setName(device.getName());
					bluetoothBean.setMac(device.getAddress());
					bluetoothBean.setRssi(String.valueOf(device.rssi));
					bluetoothBean.setPreParse(Arrays.toString(beacon.mBytes));
					boolean isRepeat = false;
					//System.out.println("bluetoothBeanList: " + bluetoothBeanList.size());
					if(!device.getName().equals("NULL") && device.getName().startsWith("CM")) {
						for(int i = 0; i < bluetoothBeanList.size(); i++) {
							if(bluetoothBeanList.get(i).getMac().equals(device.getName())) {
								isRepeat = true;
							}
						}
						if(!isRepeat) {
							bluetoothBeanList.add(bluetoothBean);
						}
					}
					bluetoothInfoAdapter.setNewInstance(removeDuplicate(bluetoothBeanList));
					bluetoothInfoAdapter.notifyDataSetChanged();
				}
				
				@Override
				public void onSearchStopped() {
				}
				
				@Override
				public void onSearchCanceled() {}
			});
		}
//		else {
//			mClient.openBluetooth();
//		}
		
	}
	
	/**
	 * 移除重复的设备名(Remove duplicate device name)
	 */
	private List<BluetoothBean> removeDuplicate(List<BluetoothBean> list) {
		for(int i = 0; i < list.size() - 1; i++) {
			for(int j = list.size() - 1; j > i; j--) {
				if(list.get(j).getMac().equals(list.get(i).getMac())) {
					list.remove(j);
				}
			}
		}
		return list;
	}
	
	private void connectBluetoothDevice(String mac) {
		BleManager.getInstance().connect(mac, new BleGattCallback()
		{
			@Override
			public void onStartConnect() {
				WaitDialogUtil.show(BleDeviceSearch.this, getString(R.string.connect));
			}
			
			@Override
			public void onConnectFail(BleDevice bleDevice, BleException exception) {
				WaitDialogUtil.dismiss();
			}
			
			@Override
			public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
				WaitDialogUtil.dismiss();
				initDevice(bleDevice);
				
				Toast.makeText(BleDeviceSearch.this, getString(R.string.connect_success), Toast.LENGTH_SHORT).show();
				WaitDialogUtil.show(BleDeviceSearch.this, "準備開始校正", 2000, () -> {
					startActivity(new Intent(BleDeviceSearch.this, BleDeviceAdjust.class));
					finish();
				});
			}
			
			@Override
			public void onDisConnected(boolean isActiveDisConnected, BleDevice device, BluetoothGatt gatt, int status) {
				Log.i("deep", "onDisConnected");
			}
		});
	}
	
	//region 初始化用的方法
	private void initBluetoothInfoAdapter() {
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BleDeviceSearch.this);
		bluetoothInfoAdapter = new BluetoothInfoAdapter(R.layout.recyclerview_item_bluetooth_info);
		bluetoothInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
			BluetoothBean bluetoothBean = (BluetoothBean) adapter.getItem(position);
			mClient.stopSearch();
			connectBluetoothDevice(bluetoothBean.getMac());
		});
		
		binding.rvBluetoothInfo.setLayoutManager(linearLayoutManager);
		binding.rvBluetoothInfo.setAdapter(bluetoothInfoAdapter);
	}
	
	/**
	 * 初始化設備紀錄(Initialize device)
	 */
	private void initDevice(BleDevice bleDevice) {
		IBluetoothManager.getInstance().connectDevice = bleDevice;
		IBluetoothManager.getInstance().setNotify();
		IBluetoothManager.getInstance().connect_init = true;
	}
	
	/**
	 * 申請權限(acceptPermissions)
	 * 必須申請，不確定原因是什麼。但拿掉找不到光譜儀。
	 */
	private void acceptPermissions() {
		EasyPermissions.create(
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.BLUETOOTH_SCAN,
				Manifest.permission.BLUETOOTH_ADMIN,
				Manifest.permission.BLUETOOTH_CONNECT
		).request(this);
	}
	//endregion
	
}