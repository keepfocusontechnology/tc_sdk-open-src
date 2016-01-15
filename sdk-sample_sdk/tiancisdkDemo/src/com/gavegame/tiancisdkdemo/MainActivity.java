package com.gavegame.tiancisdkdemo;

import java.io.File;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.TianCiSDK;
import com.gavegame.tiancisdk.activity.TCLoginActivity;
import com.gavegame.tiancisdk.activity.TCPayActivity;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.bean.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class MainActivity extends Activity {

	private Context context;

	private static final int PAY_RESULT = 2233;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// true为竖屏，false 为横屏
			TianCiSDK.setScreenIsPortrait(true);
		} else {
			// true为竖屏，false 为横屏
			TianCiSDK.setScreenIsPortrait(false);
		}
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		findViewById(R.id.jump_login_page).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivityForResult(new Intent(MainActivity.this,
								TCLoginActivity.class), 0);
					}
				});
		findViewById(R.id.switch_account).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								TCLoginActivity.class);
						intent.setAction("switch_account");
						startActivityForResult(intent, 0);
					}
				});

		findViewById(R.id.pay).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						TCPayActivity.class);
				intent.putExtra("subject", "支付宝测试名称");
				intent.putExtra("body", "测试：描述信息");
				intent.putExtra("price", "0.01");
				intent.putExtra("roleId", "123");
				intent.putExtra("serverId", "152001");
				// orderId 请传入String
				intent.putExtra("cp_orderId", new Random().nextInt(10000) + 1
						+ "");
				startActivityForResult(intent, 0);
			}
		});

		findViewById(R.id.login_role).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TianCi.getInstance().roleAutoLogin("123", "123",
						new RequestCallBack() {

							@Override
							public void onSuccessed(ResponseMsg responseMsg) {
								TCLogUtils.showToast(getApplicationContext(),
										"角色登陆成功");
							}

							@Override
							public void onFailure(String msg) {
								TCLogUtils.showToast(getApplicationContext(),
										msg);
							}
						});
			}
		});

		findViewById(R.id.bt_download).setOnClickListener(
				new OnClickListener() {

					@SuppressLint("NewApi")
					@Override
					public void onClick(View v) {
						String url = "http://mobile.unionpay.com/getclient?platform=android&type=securepayplugin";
						long fileId = download(url);
						TCLogUtils.showToast(context, fileId + "");
						DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
						Uri uri = manager.getUriForDownloadedFile(fileId);
						installApk(uri, MainActivity.this);
						// File sdDir = null;
						// boolean sdCardExist = Environment
						// .getExternalStorageState().equals(
						// android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
						// if (sdCardExist) {
						// sdDir = Environment.getExternalStorageDirectory();//
						// 获取跟目录
						// File file = isExists(sdDir);
						// if (null != null) {
						// installApk(file);
						// }
						// }
					}
				});

	}

	/**
	 * @param context
	 *            used to check the device version and DownloadManager
	 *            information
	 * @return true if the download manager is available
	 */
	private boolean isDownloadManagerAvailable(Context context) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
			return false;
		}
		return true;
	}

	@SuppressLint("NewApi")
	long download(String url) {
		if (isDownloadManagerAvailable(context)) {
			DownloadManager.Request request = new DownloadManager.Request(
					Uri.parse(url));
			request.setDescription("正在下载...");
			request.setTitle("银联支付控件");
			// in order for this if to run, you must use the android 3.2 to
			// compile your app
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			}
			request.setDestinationInExternalPublicDir(Environment
					.getExternalStorageDirectory().getPath(), "银联支付.apk");
			// get download service and enqueue file
			DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
			return manager.enqueue(request);
		}
		return 0;
	}

	/**
	 * 安装APK文件
	 */
	private void installApk(Uri uri, Context context) {
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		// i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
		// "application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(i);
	}

	File isExists(File files) {

		// 手机上的文件,目前只判断SD卡上的APK文件
		// file = Environment.getDataDirectory();
		// SD卡上的文件目录

		if (files.exists()) {
			if (files.isFile()) {
				if (files.getName().equals("银联支付.apk")) {
					return files;
				}
				TCLogUtils.e("文件名:" + files.getName());
				TCLogUtils.e("绝对路径:" + files.getAbsolutePath());
				TCLogUtils.e("相对路径:" + files.getPath());
			} else if (files.isDirectory()) {
				File[] file_list = files.listFiles();
				if (file_list != null && file_list.length > 0) {
					for (File file : file_list) {
						isExists(file);
					}
				}
			}
		}
		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			int aliCode = data.getIntExtra("pay_resultcode", 0);
			if (aliCode == 200) {
				Toast.makeText(this, "alipay支付成功", 0).show();
			} else if (aliCode == 300) {
				Toast.makeText(this, "alipay支付中", 0).show();
			} else if (aliCode == 400) {
				Toast.makeText(this, "alipay支付失败", 0).show();
			}
		}

		if (resultCode == Config.REQUEST_STATUS_CODE_SUC) {
			String tcsso = data.getStringExtra("tcsso");
			Toast.makeText(this, tcsso, 0).show();
		} else if (resultCode == Config.REQUEST_STATUS_CODE_FAILURE) {
			Toast.makeText(this,
					data.getExtras().getSerializable("result").toString(), 0)
					.show();
		} else if (resultCode == Config.BIND_SUC) {
			Toast.makeText(this,
					data.getExtras().getSerializable("result").toString(), 0)
					.show();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		TianCi.init(this);
	}
	// if (resultCode == Config.REQUEST_STATUS_CODE_SUC) {
	// String tcsso = data.getStringExtra("tcsso");
	// TCLogUtils.toastShort(context, "tcsso=" +tcsso);
	// } else if (resultCode == Config.REQUEST_STATUS_CODE_FAILURE) {
	// TCLogUtils.toastShort(context,
	// data.getExtras().getSerializable("result").toString());
	// }else if(resultCode == PAY_RESULT){
	// int payResultCode = data.getExtras().getInt("pay_resultcode");
	// if(payResultCode == 200){
	// Toast.makeText(MainActivity.this, "支付成功",
	// Toast.LENGTH_SHORT).show();
	// }else if(payResultCode == 300){
	// Toast.makeText(MainActivity.this, "支付中",
	// Toast.LENGTH_SHORT).show();
	// }else if(payResultCode == 400){
	// Toast.makeText(MainActivity.this, "支付失败",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// }
	// }

}
