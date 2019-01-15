package thirdweek.madcamp.walkitalki;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;

import thirdweek.madcamp.walkitalki.Model.User;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    public static String KAKAONAME;
    public static long KAKAOID;

    public static User myUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        getHashKey(mContext);
        Log.d("THIS IS MAINACTIVITY ", "ONCREATE");
        this.requestMe();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            Fragment1 newFrag = new Fragment1();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    newFrag).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_tab1:
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("user_name", myUser.getUser_name());
                            selectedFragment = new Fragment1();
                            selectedFragment.setArguments(bundle1);
                            break;
                        case R.id.nav_tab2:
                            selectedFragment = new Fragment2();
                            break;
                        case R.id.nav_tab3:
                            Bundle bundle = new Bundle();
                            bundle.putString("user_name", myUser.getUser_name());
                            selectedFragment = new Fragment3();
                            selectedFragment.setArguments(bundle);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };


    @Nullable
    public static String getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }

    protected void requestMe() { //유저의 정보를 받아오는 함수

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                int result = errorResult.getErrorCode();
                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Logger.e("onSessionClosed");
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response result) {
                KAKAOID = result.getId();
                KAKAONAME = result.getNickname();

                Log.d("KAKAOID", String.valueOf(KAKAOID));
                Log.d("KAKAOAME", KAKAONAME);

                myUser = new User(KAKAONAME, KAKAOID);
            }
        });
    }

    private void redirectMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        Log.d("REDIRECTMAINACTIVITY", "ACTIVATED");
        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}
