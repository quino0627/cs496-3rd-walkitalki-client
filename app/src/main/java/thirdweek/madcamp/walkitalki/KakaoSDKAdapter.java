package thirdweek.madcamp.walkitalki;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.IPushConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.SharedPreferencesCache;

import android.provider.Settings.Secure;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class KakaoSDKAdapter extends KakaoAdapter {

    /**
     * Session Config에 대해서는 default값들이 존재한다.
     * 필요한 상황에서만 override해서 사용하면 됨.
     * @return Session의 설정값.
     */

    private String PROPERTY_DEVICE_ID = Secure.getString(getApplicationConfig().getApplicationContext().getContentResolver(),
            Secure.ANDROID_ID);


    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            @Override
            public AuthType[] getAuthTypes() {
                return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
            }

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            @Override
            public boolean isSecureMode() {
                return false;
            }

            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }

    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {


            @Override
            public Context getApplicationContext() {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
    }

    //
    @Override
    public IPushConfig getPushConfig() {
        return new IPushConfig() {
            /**
             * [주의!] 아래 예제는 샘플앱에서 사용되는 것으로 기기정보 일부가 포함될 수 있습니다. 실제 릴리즈 되는 앱에서 사용하기 위해서는 사용자로부터 개인정보 취급에 대한 동의를 받으셔야 합니다.
             *
             * 한 사용자에게 여러 기기를 허용하기 위해 기기별 id가 필요하다.
             * ANDROID_ID가 기기마다 다른 값을 준다고 보장할 수 없어, 보완된 로직이 포함되어 있다.
             * @return 기기의 unique id
             */
            @Override
            public String getDeviceUUID() {
                String deviceUUID;
                final SharedPreferencesCache cache = Session.getCurrentSession().getAppCache();
                final String id = cache.getString(PROPERTY_DEVICE_ID);

                if (id != null) {
                    deviceUUID = id;
                    return deviceUUID;
                } else {
                    UUID uuid = null;
                    Context context = getApplicationConfig().getApplicationContext();
                    final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    try {
                        if (!"9774d56d682e549c".equals(androidId)) {
                            uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                        } else {
                            if (ActivityCompat.checkSelfPermission(getApplicationConfig().getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return null;
                            }
                            final String deviceId = ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE)).getDeviceId();
                            uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString(PROPERTY_DEVICE_ID, uuid.toString());
                    cache.save(bundle);

                    deviceUUID = uuid.toString();
                    return deviceUUID;
                }
            }

            @Override
            public ApiResponseCallback<Integer> getTokenRegisterCallback() {
                return new ApiResponseCallback<Integer>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        // FCM 토큰 등록 실패 처리.
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        // 현재 로그인이 되어 있지 않은 상태.
                    }

                    @Override
                    public void onNotSignedUp() {
                        // 앱에 카카오톡 계정으로 가입이 되어있지 않은 상태.
                    }

                    @Override
                    public void onSuccess(Integer result) {
                        // 성공적으로 토큰이 등록된 상태
                        Log.e("tokenSuccess", "helloToken");
                        Log.e("tokenSuccess", String.valueOf(result));
                    }
                };
            }
        };
    }
}


