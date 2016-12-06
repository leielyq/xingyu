package com.leielyq.sign;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myxingyu.R;
import com.leielyq.base.BaseFragment;
import com.leielyq.bean.MyUser;
import com.leielyq.show.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    @Bind(R.id.etname)
    EditText etname;
    @Bind(R.id.etpassword)
    EditText etpassword;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.login, R.id.register, R.id.forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                final SweetAlertDialog pDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(true);
                pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pDialog.cancel();
                    }
                });
                pDialog.show();
                BmobUser.loginByAccount(etname.getText().toString().trim(), etpassword.getText().toString().trim(), new LogInListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (myUser != null) {
                            login();
                        } else {
                            Toast.makeText(mActivity, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        pDialog.dismiss();
                    }
                });
                break;
            case R.id.register:
                start(SignFragment.newInstance());
                break;
            case R.id.forget:
                break;
        }
    }

    private void login() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        mActivity.finish();
    }

}
