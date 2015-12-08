package com.gavegame.tiancisdk.order;

import java.util.List;
import android.widget.ListView;

import com.gavegame.tiancisdk.widget.CommonAdapter;

public class OrderManager {

	private static OrderManager recordManager = null;

	// private Context mContext;

	public static OrderManager getInstance() {
		if (recordManager == null) {
			synchronized (OrderManager.class) {

				if (recordManager == null)
					recordManager = new OrderManager();
			}
		}
		return recordManager;
	}

	// private OrderManager(Context context) {
	// this.mContext = context;
	// }

	private OrderManager() {
	
	}

	public <T> void show(ListView listView, List<T> list, CommonAdapter<T> adapter) {
		listView.setAdapter(adapter);
	}
}
