package com.tokyoanimation.app;

import com.tokyoanimation.models.Client;
import com.tokyoanimation.models.ClientException;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {

	private EditText username4register;
	private EditText email4register;
	private EditText password4register;
	private EditText confirm4register;
	private EditText username4login;
	private EditText password4login;
	private Client client;
	
	public static LoginFragment newInstance(){
		LoginFragment fragment = new LoginFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}
	public LoginFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		client = new Client(this.getActivity());
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        username4register = (EditText) rootView.findViewById(R.id.username_for_register);
        email4register = (EditText) rootView.findViewById(R.id.email_for_register);
        password4register = (EditText) rootView.findViewById(R.id.password_for_register);
        confirm4register = (EditText) rootView.findViewById(R.id.confirm_for_register);
        username4login = (EditText) rootView.findViewById(R.id.username_for_login);
        password4login = (EditText) rootView.findViewById(R.id.password_for_login);
        
        ((Button) rootView.findViewById(R.id.register)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
			}});
        
        ((Button) rootView.findViewById(R.id.login)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
			}});
        
        ((Button) rootView.findViewById(R.id.facebook)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				try {
					client.loginWithFacebook();
				} catch (ClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}});
        
        ((Button) rootView.findViewById(R.id.twitter)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
			}});
        return rootView;
    }
}
