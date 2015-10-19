package com.gavegame.tiancisdkdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.gavegame.tiancisdk.Config;
import com.gavegame.tiancisdk.TianCi;
import com.gavegame.tiancisdk.activity.LoginPageActivity;
import com.gavegame.tiancisdk.alipay.AliPayActivity;
import com.gavegame.tiancisdk.network.RequestCallBack;
import com.gavegame.tiancisdk.network.ResponseMsg;
import com.gavegame.tiancisdk.utils.TCLogUtils;

public class MainActivity extends ActionBarActivity {

	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		
		findViewById(R.id.jump_login_page).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(MainActivity.this,LoginPageActivity.class),0);
			}
		});;
		
		findViewById(R.id.bt_auto_login).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method 
				TianCi.getInstance().autoLogin("123", new RequestCallBack() {
					
					@Override
					public void onSuccessed() {
						Toast.makeText(getApplicationContext(), "200", 0).show();	
					}
					
					@Override
					public void onFailure(ResponseMsg arg0) {
						// TODO Auto-generated method stub
						TCLogUtils.d(arg0.getRetMsg());
					}
				});
			}
		});
		
		findViewById(R.id.bt_create_role).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method 
				TianCi.getInstance().roleAutoLogin("123", new RequestCallBack() {
					
					@Override
					public void onSuccessed() {
						Toast.makeText(getApplicationContext(), "200", 0).show();	
					}
					
					@Override
					public void onFailure(ResponseMsg arg0) {
						// TODO Auto-generated method stub
						TCLogUtils.d(arg0.getRetMsg());
					}
				});
			}
		});
		
		
		
		
		
		findViewById(R.id.bt_create_order).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method 
				TianCi.getInstance().createOrder("123","123","213", new RequestCallBack() {
					
					@Override
					public void onSuccessed() {
						Toast.makeText(getApplicationContext(), "200", 0).show();	
					}
					
					@Override
					public void onFailure(ResponseMsg arg0) {
						// TODO Auto-generated method stub
						TCLogUtils.d(arg0.getRetMsg());
					}
				});
			}
		});
		findViewById(R.id.bt_finish_order).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method 
				TianCi.getInstance().finishOrder("123","123","213", new RequestCallBack() {
					
					@Override
					public void onSuccessed() {
						Toast.makeText(getApplicationContext(), "200", 0).show();	
					}
					
					@Override
					public void onFailure(ResponseMsg arg0) {
						// TODO Auto-generated method stub
						TCLogUtils.d(arg0.getRetMsg());
					}
				});
			}
		});
		
		findViewById(R.id.bt_alipay).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,AliPayActivity.class));
			}
		});
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Config.REQUEST_STATUS_CODE_SUC) {
            String tcsso = data.getStringExtra("tcsso");
            TCLogUtils.toastShort(context, "tcsso=" +tcsso);
        } else if (resultCode == Config.REQUEST_STATUS_CODE_FAILURE) {
            TCLogUtils.toastShort(context, data.getExtras().getSerializable("result").toString());
        }
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		TianCi.init(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
