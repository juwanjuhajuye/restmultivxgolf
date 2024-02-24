package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.serial.FTDriver;
import com.example.jjbfather.jjjqsrt2go2gorestkakaovx.serial.FTDriverUtil;

public class UsbReceiver extends BroadcastReceiver {
	private Boolean SHOW_DEBUG = false;
	private String TAG = "HDJ";

	private Context mContext;
	private Activity mActivity;
	private FTDriver mSerial;
	private Handler mHandler = new Handler();
	private StringBuilder mText;

	private static final String ACTION_USB_PERMISSION = "kr.co.andante.mobiledgs.USB_PERMISSION";

	private boolean mStop = false;
	private boolean mRunningMainLoop = false;

	private static final int TEXT_MAX_SIZE = 8192;

	// Default settings
	private int mTextFontSize       = 12;
	private Typeface mTextTypeface  = Typeface.MONOSPACE;
	private int mDisplayType        = FTDriverUtil.DISP_CHAR;
	private int mBaudrate           = FTDriver.BAUD9600;
	private int mDataBits           = FTDriver.FTDI_SET_DATA_BITS_8;
	private int mParity             = FTDriver.FTDI_SET_DATA_PARITY_NONE;
	private int mStopBits           = FTDriver.FTDI_SET_DATA_STOP_BITS_1;
	private int mFlowControl        = FTDriver.FTDI_SET_FLOW_CTRL_NONE;
	private int mBreak              = FTDriver.FTDI_SET_NOBREAK;

	public UsbReceiver(Activity activity, FTDriver serial)
	{
		mActivity = MainActivity.mActivity;
		mContext = MainActivity.mContext;
		mSerial = serial;
	}

	public int GetTextFontSize()
	{
		return mTextFontSize;
	}

	public int GetBaudrate()
	{
		return mBaudrate;
	}

	// Load default baud rate
	int loadDefaultBaudrate() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.mContext);
		String res = pref.getString("baudrate_list", Integer.toString(FTDriver.BAUD9600));
		return Integer.valueOf(res);
	}

	void loadDefaultSettingValues() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.mContext);
		String res = pref.getString("display_list", Integer.toString(FTDriverUtil.DISP_CHAR));
		mDisplayType = Integer.valueOf(res);

		res = pref.getString("fontsize_list", Integer.toString(12));
		mTextFontSize = Integer.valueOf(res);

		res = pref.getString("typeface_list", Integer.toString(3));
		switch(Integer.valueOf(res)){
		case 0:
			mTextTypeface = Typeface.DEFAULT;
			break;
		case 1:
			mTextTypeface = Typeface.SANS_SERIF;
			break;
		case 2:
			mTextTypeface = Typeface.SERIF;
			break;
		case 3:
			mTextTypeface = Typeface.MONOSPACE;
			break;
		}

		res = pref.getString("readlinefeedcode_list", Integer.toString(FTDriverUtil.LINEFEED_CODE_CRLF));
		FTDriverUtil.mReadLinefeedCode = Integer.valueOf(res);

		res = pref.getString("writelinefeedcode_list", Integer.toString(FTDriverUtil.LINEFEED_CODE_CRLF));
		FTDriverUtil.mWriteLinefeedCode = Integer.valueOf(res);

		res = pref.getString("email_edittext", "@gmail.com");

		res = pref.getString("databits_list", Integer.toString(FTDriver.FTDI_SET_DATA_BITS_8));
		mDataBits = Integer.valueOf(res);
		mSerial.setSerialPropertyDataBit(mDataBits, FTDriver.CH_A);

		res = pref.getString("parity_list", Integer.toString(FTDriver.FTDI_SET_DATA_PARITY_NONE));
		mParity = Integer.valueOf(res) << 8; // parity_list's number is 0 to 4
		mSerial.setSerialPropertyParity(mParity, FTDriver.CH_A);

		res = pref.getString("stopbits_list", Integer.toString(FTDriver.FTDI_SET_DATA_STOP_BITS_1));
		mStopBits = Integer.valueOf(res) << 11; // stopbits_list's number is 0 to 2
		mSerial.setSerialPropertyStopBits(mStopBits, FTDriver.CH_A);

		res = pref.getString("flowcontrol_list", Integer.toString(FTDriver.FTDI_SET_FLOW_CTRL_NONE));
		mFlowControl = Integer.valueOf(res) << 8;
		mSerial.setFlowControl(FTDriver.CH_A, mFlowControl);

		res = pref.getString("break_list", Integer.toString(FTDriver.FTDI_SET_NOBREAK));
		mBreak = Integer.valueOf(res) << 14;
		mSerial.setSerialPropertyBreak(mBreak, FTDriver.CH_A);

		mSerial.setSerialPropertyToChip(FTDriver.CH_A);
	}

	public void openUsbSerial() {
		if (!mSerial.isConnected()) {
			if (SHOW_DEBUG) {
				GlobalMemberValues.logWrite(TAG, "onNewIntent begin");
			}
			mBaudrate = loadDefaultBaudrate();
			if (!mSerial.begin(mBaudrate)) {
				Toast.makeText(MainActivity.mContext, "cannot open", Toast.LENGTH_SHORT).show();
				return;
			} else {
				//Toast.makeText(MainActivity.mContext, "connected", Toast.LENGTH_SHORT).show();
			}
		}

		if (!mRunningMainLoop) {
			mainloop();
		}

	}

	public void closeUsbSerial() {
		detachedUi();
		mStop = true;
		mSerial.end();
	}

	public void writeDataToSerial(String strWrite) {
		GlobalMemberValues.logWrite(TAG, "strWrite : " + strWrite);

		strWrite = FTDriverUtil.changeLinefeedcode(strWrite);

		GlobalMemberValues.logWrite(TAG, "SHOW_DEBUG : " + SHOW_DEBUG.toString());

		if (SHOW_DEBUG) {
			GlobalMemberValues.logWrite(TAG, "FTDriver Write(" + strWrite.length() + ") : " + strWrite);
		}

		GlobalMemberValues.logWrite(TAG, "mSerial : " + mSerial.toString());

		if(mSerial.isConnected())
			mSerial.write(strWrite.getBytes(), strWrite.length());
		else {
			GlobalMemberValues.logWrite(TAG, "FTDriver Write(" + strWrite.length() + ") : " + strWrite);
//			Toast.makeText(mContext, "Usb is disconnection", Toast.LENGTH_SHORT).show();
		}
	}

	public void mainloop() {
		mStop = false;
		mRunningMainLoop = true;
		//Toast.makeText(mContext, "connected", Toast.LENGTH_SHORT).show();
		if (SHOW_DEBUG) {
			GlobalMemberValues.logWrite(TAG, "start mainloop");
		}
		new Thread(mLoop).start();
	}

	private Runnable mLoop = new Runnable() {
		@Override
		public void run() {
			int len;
			byte[] rbuf = new byte[4096];
			int bMsgCnt = 0;

			if (mSerial == null) {
				return;
			}
			
			for (;;) {// this is the main loop for transferring

				// ////////////////////////////////////////////////////////
				// Read and Display to Terminal
				// ////////////////////////////////////////////////////////
				len = mSerial.read(rbuf);
				rbuf[len] = 0;
				
				if (len > 0) {

					if(bMsgCnt == 0)
						mText = new StringBuilder();

					if(len < 10) {
						OnReadMessage(rbuf, len);
						bMsgCnt += len;
					}
					else if(len == 10) {
						OnReadMessage(rbuf, len);
						bMsgCnt = len;
					}

					GlobalMemberValues.logWrite("scalelog", "원래 무게1 : " + mText.toString() + "\n");

					GlobalMemberValues.logWrite("scalelog", "숫자 무게1 : " + mText.toString().replaceAll("[^0-9,.]", "") + "\n");

					if(bMsgCnt >= 10)
					{
						bMsgCnt = 0;
					
						mHandler.post(new Runnable() {
							public void run() {
								//((MainActivity)mActivity).onSetText(mText.toString());
								String tempWeight = mText.toString().replaceAll("[^0-9,.]", "");
								if (tempWeight.length() != 0){
									double d_tempWeight = Double.valueOf(tempWeight);
									tempWeight = String.format("%.1f",d_tempWeight);
								}
								GlobalMemberValues.logWrite("scalelog", "무게1 : " + tempWeight + "\n");
								MainMiddleService_ScaleWeight.calOnScaleWeight(tempWeight, "Y", MainMiddleService_ScaleWeight.mNoTareYN);
							}
						});
					}
				}

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (mStop) {
					mRunningMainLoop = false;
					return;
				}
			}
		}
	};
	
	private void OnReadMessage(byte[] rbuf, int len)
	{
		switch (mDisplayType) {
		case FTDriverUtil.DISP_CHAR:
			FTDriverUtil.setSerialDataToTextView(mText, mDisplayType, rbuf, len, "", "");
			break;
		case FTDriverUtil.DISP_DEC:
			FTDriverUtil.setSerialDataToTextView(mText, mDisplayType, rbuf, len, "013", "010");
			break;
		case FTDriverUtil.DISP_HEX:
			FTDriverUtil.setSerialDataToTextView(mText, mDisplayType, rbuf, len, "0d", "0a");
			break;
		}
		
		if (SHOW_DEBUG) {
			GlobalMemberValues.logWrite(TAG, "Read Length : " + len +"/" +mText);
		}
	}

	private void detachedUi() {
		if(mSerial.isConnected())
			Toast.makeText(mContext, "disconnect", Toast.LENGTH_SHORT).show();
	}

	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
			if (SHOW_DEBUG) {
				GlobalMemberValues.logWrite(TAG, "Device attached");
				Toast.makeText(mContext, "Device attached", Toast.LENGTH_SHORT).show();
			}
			if (!mSerial.isConnected()) {
				if (SHOW_DEBUG) {
					GlobalMemberValues.logWrite(TAG, "Device attached begin");
					Toast.makeText(mContext, "Device attached begin", Toast.LENGTH_SHORT).show();
				}
				mBaudrate = loadDefaultBaudrate();
				mSerial.begin(mBaudrate);
				loadDefaultSettingValues();
			}
			if (!mRunningMainLoop) {
				if (SHOW_DEBUG) {
					GlobalMemberValues.logWrite(TAG, "Device attached mainloop");
					Toast.makeText(mContext, "Device attached mainloop", Toast.LENGTH_SHORT).show();
				}
				mainloop();
			}
		} else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
			if (SHOW_DEBUG) {
				GlobalMemberValues.logWrite(TAG, "Device detached");
				Toast.makeText(mContext, "Device detached", Toast.LENGTH_SHORT).show();
			}
			mStop = true;
			detachedUi();
			mSerial.usbDetached(intent);
			mSerial.end();
		} else if (ACTION_USB_PERMISSION.equals(action)) {
			if (SHOW_DEBUG) {
				GlobalMemberValues.logWrite(TAG, "Request permission");
				Toast.makeText(mContext, "Request permission", Toast.LENGTH_SHORT).show();
			}
			synchronized (this) {
				if (!mSerial.isConnected()) {
					if (SHOW_DEBUG) {
						GlobalMemberValues.logWrite(TAG, "Request permission begin");
						Toast.makeText(mContext, "Request permission begin", Toast.LENGTH_SHORT).show();
					}
					mBaudrate = loadDefaultBaudrate();
					mSerial.begin(mBaudrate);
					loadDefaultSettingValues();
				}
			}
			if (!mRunningMainLoop) {
				if (SHOW_DEBUG) {
					GlobalMemberValues.logWrite(TAG, "Request permission mainloop");
					Toast.makeText(mContext, "Request permission mainloop", Toast.LENGTH_SHORT).show();
				}
				mainloop();
			}
		}
	}


	// add up

	private Runnable custom_mLoop = new Runnable() {
		@Override
		public void run() {
			int len;
			byte[] rbuf = new byte[4096];
			int bMsgCnt = 0;

			for (;;) {// this is the main loop for transferring

				// ////////////////////////////////////////////////////////
				// Read and Display to Terminal
				// ////////////////////////////////////////////////////////
				len = mSerial.read(rbuf);
				rbuf[len] = 0;

				if (len > 0) {
					if(bMsgCnt == 0)
						mText = new StringBuilder();
					if(len < 10) {
						OnReadMessage(rbuf, len);
						bMsgCnt += len;
					}
					else if(len == 10)
					{
						OnReadMessage(rbuf, len);
						bMsgCnt = len;
					}

					Log.e("text",mText.toString());

					GlobalMemberValues.logWrite("scalelog", "원래 무게2 : " + mText.toString() + "\n");

					GlobalMemberValues.logWrite("scalelog", "숫자 무게2 : " + mText.toString().replaceAll("[^0-9,.]", "") + "\n");

					if(bMsgCnt >= 10)
					{
						bMsgCnt = 0;

						mHandler.post(new Runnable() {
							public void run() {
								//((MainActivity)mActivity).onSetText(mText.toString());
								//((MainActivity)mActivity).onSetText2(mText.toString().replaceAll("[^0-9,.]", ""));
								String tempWeight = mText.toString().replaceAll("[^0-9,.]", "");
								GlobalMemberValues.logWrite("scalelog", "무게2 : " + tempWeight + "\n");
								MainMiddleService_ScaleWeight.calOnScaleWeight(tempWeight, "Y", MainMiddleService_ScaleWeight.mNoTareYN);
							}
						});
					}
				}

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (mStop) {
					mRunningMainLoop = false;
					return;
				}
			}
		}
	};

	public void custom_mainloop() {
		mStop = false;
		mRunningMainLoop = true;
		Toast.makeText(mContext, "connected", Toast.LENGTH_SHORT).show();
		if (SHOW_DEBUG) {
			GlobalMemberValues.logWrite(TAG, "start mainloop");
		}
		new Thread(custom_mLoop).start();
	}
	public void custom_openUsbSerial() {
		if (!mSerial.isConnected()) {
			if (SHOW_DEBUG) {
				GlobalMemberValues.logWrite(TAG, "onNewIntent begin");
			}
			mBaudrate = loadDefaultBaudrate();
			if (!mSerial.begin(mBaudrate)) {
				Toast.makeText(mContext, "cannot open", Toast.LENGTH_SHORT).show();
				return;
			} else {
				Toast.makeText(mContext, "connected", Toast.LENGTH_SHORT).show();
			}
		}

		if (!mRunningMainLoop) {
			custom_mainloop();
		}

	}
}
