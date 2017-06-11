package vn.home.com.bottombar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseUser;

public class DangNhapActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText edtEmail, edtMatKhau;
    private Button btnDangNhap, btnDenDangKy, btnQuenMatKhau;
    private ProgressBar pgBar;
    private LoginButton btnLoginFacebook;
    CallbackManager mCallbackManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnDenDangKy = (Button) findViewById(R.id.btnDenDangKy);
        btnQuenMatKhau = (Button) findViewById(R.id.btnQuenMatKhau);
        pgBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        btnLoginFacebook = (LoginButton) findViewById(R.id.login_button);

        auth = FirebaseAuth.getInstance();


        btnQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhapActivity.this, QuenMatKhauActivity.class);
                startActivity(intent);
            }
        });

        btnDenDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String matkhau = edtMatKhau.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền vào địa chỉ email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(matkhau)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền vào mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (matkhau.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu quá ngắn, tối thiểu 6 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                pgBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, matkhau).addOnCompleteListener(DangNhapActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pgBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại: email hoặc mật khẩu không chính xác, vui lòng kiểm tra lại.", Toast.LENGTH_SHORT).show();
                        } else {
                            checkIfEmailVerified();
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mCallbackManager = CallbackManager.Factory.create();
        btnLoginFacebook.setReadPermissions("email", "public_profile");
        btnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Facebook","Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebook",error.getMessage());
            }
        });
    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.isEmailVerified()){
            Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(DangNhapActivity.this, "Vui lòng kiểm tra email để kích hoạt tài khoản", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("Facebook",task.getException().getMessage());
                            Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}


