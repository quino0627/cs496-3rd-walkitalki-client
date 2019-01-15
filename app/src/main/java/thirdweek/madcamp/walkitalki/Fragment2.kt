package thirdweek.madcamp.walkitalki

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_layout2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thirdweek.madcamp.walkitalki.Model.Post
import thirdweek.madcamp.walkitalki.Retrofit.APIUtils
import thirdweek.madcamp.walkitalki.Retrofit.IMyService

class Fragment2 : Fragment(){

    lateinit var retrofitApi:IMyService
    lateinit var postList :MutableList<Post>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        retrofitApi = APIUtils.getUserService()

        val rootView = inflater.inflate(R.layout.fragment_layout2, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.timeline_recycler_view) as RecyclerView
        var initialPost = Post()
//        initialPost.myDate = "20181010"
        initialPost.content = "This is initial CONTENT"
        var list2 = listOf<Post>(initialPost)
        postList = list2.toMutableList()
        postList.add(initialPost)
        //getPostsList()


        var call = retrofitApi.posts
        call.enqueue(object: Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                Log.d("IN THE CALLBACK", "asdf")
                if (response.isSuccessful){
                    Log.d("IN THE IF", "asdf")
                    postList = response.body() as MutableList<Post>
                    postList.reverse()
                    // Log.e("ASDFASDF",postList[0].uploader_name.toString())
                    val adapter = PostItemAdapter(postList, (activity as Context?)!!)
                    val formanage = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                    recyclerView.layoutManager = formanage
                    recyclerView.adapter = adapter
                    recyclerView.setHasFixedSize(false)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("Error: " ,t.message)
            }
        })
        return rootView
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = timeline_recycler_view
        var call = retrofitApi.posts
        call.enqueue(object:Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful){
                    postList = response.body() as MutableList<Post>
                    postList.reverse()

                    // Log.e("ASDFASDF",postList[0].uploader_name.toString())
                    val adapter = PostItemAdapter(postList, (activity as Context?)!!)
                    val formanage = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
                    recyclerView.layoutManager = formanage
                    recyclerView.adapter = adapter
                    recyclerView.setHasFixedSize(false)
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("Error: " ,t.message)
            }
        })
    }


}