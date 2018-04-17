package com.lznby.baidumapdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lznby.baidumapdemo.entity.RequestType;
import com.lznby.baidumapdemo.entity.URL;
import com.lznby.baidumapdemo.network.RequestInformation;
import com.lznby.baidumapdemo.util.CheckLoginInformation;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener,View.OnFocusChangeListener{

    //登录界面控件对象创建
    private EditText mUsernameEdit;
    private EditText mPasswordEdit;
    private ImageButton mUsernameDelete;
    private ImageButton mPasswordVisible;
    private View mUsernameLine;
    private View mPasswordLine;
    private Button mSignInButton;
    private Button mForgetButton;
    private Button mLogInButton;
    private String mUsername = "";
    private String mPassword = "";
    private boolean mUsernameFlag = false;
    private boolean mPasswordFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //绑定控件
        mUsernameEdit = (EditText) findViewById(R.id.username_et);
        mPasswordEdit = (EditText) findViewById(R.id.password_et);

        mUsernameDelete = (ImageButton) findViewById(R.id.username_delete);
        mPasswordVisible = (ImageButton) findViewById(R.id.password_visible);

        mUsernameLine = findViewById(R.id.username_bl);
        mPasswordLine = findViewById(R.id.password_bl);

        mSignInButton = (Button) findViewById(R.id.sign_in_bt);
        mForgetButton = (Button) findViewById(R.id.forget_bt);
        mLogInButton = (Button) findViewById(R.id.log_in_bt);

        //设置onClick事件
        mUsernameDelete.setOnClickListener(this);
        mPasswordVisible.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        mForgetButton.setOnClickListener(this);
        mLogInButton.setOnClickListener(this);
        //设置onTouch事件
        mSignInButton.setOnTouchListener(this);
        mForgetButton.setOnTouchListener(this);
        mLogInButton.setOnTouchListener(this);
        //设置onFocusChange事件
        mUsernameEdit.setOnFocusChangeListener(this);
        mPasswordEdit.setOnFocusChangeListener(this);
        //设置ChangedListener事件
        mUsernameEdit.addTextChangedListener(watcherTel);
        mPasswordEdit.addTextChangedListener(watcherPwd);

    }

    /**
     * Button的onTouch事件，改变界面UI。
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.sign_in_bt:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    mSignInButton.setTextColor(Color.GRAY);
                if (event.getAction() == MotionEvent.ACTION_UP)
                    mSignInButton.setTextColor(Color.BLACK);
                break;
            case R.id.forget_bt:
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    mForgetButton.setTextColor(Color.GRAY);
                if (event.getAction() == MotionEvent.ACTION_UP)
                    mForgetButton.setTextColor(Color.BLACK);
                break;
            case R.id.log_in_bt:
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if (mUsernameFlag && mPasswordFlag) {
                        mLogInButton.setBackgroundResource(R.color.logButtonDarkBlue);
                    } else {
                        mLogInButton.setBackgroundResource(R.color.logButtonGray);
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mUsernameFlag && mPasswordFlag) {
                        mLogInButton.setBackgroundResource(R.color.logButtonLightBlue);
                    }
                    else {
                        mLogInButton.setBackgroundResource(R.color.logButtonGray);
                    }
                }
                break;
        }
        return false;
    }

    /**
     * Button的点击事件，处理登录逻辑。
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.username_delete:
                mUsernameEdit.setText("");
                break;
            case R.id.password_visible:
                if (mPasswordEdit.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    mPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordVisible.setBackgroundResource(R.drawable.pwd_invisible);
                } else {
                    mPasswordEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPasswordVisible.setBackgroundResource(R.drawable.pwd_visible);
                }
                break;
            case R.id.log_in_bt:
/*                //登录测试使用。
                if (mUsername.equals("13750880401") && mPassword.equals("abcd2015")) {
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this,"用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }*/

                //post请求登录
                RequestBody requestBody = new FormBody.Builder()
                        .add("name",mUsernameEdit.getText().toString())
                        .add("password",mPasswordEdit.getText().toString())
                        .build();
                RequestInformation.requestLogin(URL.LOGIN_INFORMATION_JSON_URL,this,requestBody, RequestType.POST);
                break;
            case R.id.sign_in_bt:
/*                Intent intent = new Intent(this,SignInActivity.class);
                startActivity(intent);*/
                Toast.makeText(this,"注册没了！", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    /**
     * EditText的onFocusChange事件，界面UI变化
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.username_et:
                if (hasFocus){
                    mUsernameLine.setBackgroundResource(R.drawable.blue_line);
                    mUsernameDelete.setBackgroundResource(R.drawable.falseicon);
                } else {
                    mUsernameLine.setBackgroundResource(R.drawable.gray_line);
                    if (!mUsernameFlag){
                        Toast.makeText(this,"请输入正确的号码！", Toast.LENGTH_SHORT).show();
                        mUsernameDelete.setBackgroundResource(R.drawable.user_warning);
                    }
                }
                break;
            case R.id.password_et:
                if (hasFocus){
                    mPasswordLine.setBackgroundResource(R.drawable.blue_line);
                } else {
                    mPasswordLine.setBackgroundResource(R.drawable.gray_line);
                }
        }
    }

    /**
     * 设置电话内容变化
     */
    private TextWatcher watcherTel = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mUsername = s.toString();
            if (CheckLoginInformation.isMobileNO(mUsername)) {
                mUsernameFlag = true;
                if (mPasswordFlag)
                    mLogInButton.setBackgroundResource(R.color.logButtonLightBlue);
            } else {
                mUsernameFlag = false;
                mLogInButton.setBackgroundResource(R.color.logButtonGray);
            }
            if (s.length() == 0){
                mUsernameDelete.setVisibility(View.INVISIBLE);
            } else {
                mUsernameDelete.setVisibility(View.VISIBLE);
            }
            mUsernameDelete.setBackgroundResource(R.drawable.falseicon);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * 监听密码内容变化
     */
    private TextWatcher watcherPwd = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mPassword = s.toString();
            if (CheckLoginInformation.isPasswordFormat(mPassword)){
                mPasswordFlag = true;
                if (mUsernameFlag)
                    mLogInButton.setBackgroundResource(R.color.logButtonLightBlue);
            } else {
                mPasswordFlag = false;
                mLogInButton.setBackgroundResource(R.color.logButtonGray);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}