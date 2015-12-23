//package com.gavegame.tiancisdk.alipay;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.gavegame.tiancisdk.R;
//
//public class ExternalFragment extends Fragment {
//	
//	private View view;
//	private String subject;
//	private String subject_desc;
//	private String price;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		view = inflater.inflate(R.layout.pay_external, container, false);
//		initData();
//		return view;
//	}
//	
//	private void initData(){
//		Intent data = getActivity().getIntent();
//		subject = data.getStringExtra("subject");
//		subject_desc = data.getStringExtra("subject_desc");
//		price = data.getStringExtra("price");
//		
//		TextView subject_view = (TextView) view.findViewById(R.id.product_subject);
//		subject_view.setText(subject);
//		TextView subject_desc_view = (TextView) view.findViewById(R.id.product_subject_desc);
//		subject_desc_view.setText(subject_desc);
//		TextView subject_price = (TextView) view.findViewById(R.id.product_price);
//		subject_price.setText(price);
//	}
//}
