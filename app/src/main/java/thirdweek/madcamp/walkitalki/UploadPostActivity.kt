package thirdweek.madcamp.walkitalki

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.github.nkzawa.socketio.client.IO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thirdweek.madcamp.walkitalki.Model.Post
import thirdweek.madcamp.walkitalki.Retrofit.APIUtils
import thirdweek.madcamp.walkitalki.Retrofit.IMyService
import java.io.FileNotFoundException
import java.io.IOException
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.UserManagement
import java.net.Socket
import java.net.URISyntaxException
import java.security.AccessController.getContext


class UploadPostActivity : AppCompatActivity() {

    lateinit var KAKAOID:String
    lateinit var KAKAONAME:String
    lateinit var socket:Socket
    var clicked = false
    lateinit var retrofitApi:IMyService
    lateinit var editContent: EditText
    lateinit var editTitle : EditText
    lateinit var btnSave: Button



    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_post)
        this.requestMe()
        var latitude: Double = 0.0
        var longitude:Double = 0.0
        //현재 위치 정보 받는 기능
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                //위치 바뀔때마다 latitude, longitude  바뀜
                Log.d("LocationL: ", location.toString())
                latitude = location.latitude
                longitude = location.longitude
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

            }

            override fun onProviderEnabled(provider: String) {

            }

            override fun onProviderDisabled(provider: String) {

            }
        }

        //위치 업데이트 물어보기 (0초마다, 0만큼 움직였을때)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)

        var socket = IO.socket("http://socrip4.kaist.ac.kr:1380/")
        socket.connect()

        editTitle = findViewById(R.id.editTitle)
        editContent = findViewById<EditText>(R.id.editContent)
        //tag1 = findViewById(R.id.tag1)
        //tag2 = findViewById(R.id.tag2)
        //tag3 = findViewById(R.id.tag3)
        btnSave = findViewById(R.id.upload_btn)

        retrofitApi = APIUtils.getUserService()

        btnSave.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(clicked==false) {
                    clicked = true
//                    var bitmap: Bitmap? = null
//
//
//                    try {
//                        Log.d("I'm IN TRY", (bitmap == null).toString())
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picture_uri)
//                    } catch (e: FileNotFoundException) {
//                        e.printStackTrace()
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
                    socket.emit("post detection", KAKAONAME, KAKAOID.toLong(), editTitle.text.toString(),editContent.text.toString(), latitude, longitude)



//                    post.setPictureUrl(encoder(bitmap))
//                    post.setTags(arrayOf("I WILL", "IMPLEMENT", "LATER"))
//                    post.contents = editContent.text.toString()
//                    addPost(post)
                    finish()
                }
            }
        })
    }

    //유저의 정보를 받아오는 함수
    protected fun requestMe() {

        UserManagement.getInstance().me(object : MeV2ResponseCallback() {
            override fun onFailure(errorResult: ErrorResult?) {}

            override fun onSessionClosed(errorResult: ErrorResult) {}

            override fun onSuccess(result: MeV2Response) {
                KAKAOID = result.id.toString()
                KAKAONAME = result.nickname

                //TODO: 카카오톡 프로필 사진 받아오기

                Log.d("KAKAOID", KAKAOID.toString())
                Log.d("KAKAOAME", KAKAONAME)

            }
        })
    }
}
