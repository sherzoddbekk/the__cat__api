package com.example.thecatapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.thecatapi.R
import com.example.thecatapi.adapter.AllCatAdapter
import com.example.thecatapi.model.CatElement
import com.example.thecatapi.retrofit.RetrofitHttp
import com.example.thecatapi.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentAllCat : Fragment() {
    lateinit var adapter: AllCatAdapter
    lateinit var recyclerView: RecyclerView
    var a = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_cat, container, false)
        initView(view)
        return view

    }

    fun initView(view: View) {
        recyclerView = view?.findViewById(R.id.recyclerView_home)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        getPhotos(a)//bu birinchi martta rasmlar to`plamini chiqaradi

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    getPhotos(++a)
                }
            }
        })
    }

    fun refreshAdapter(photo: ArrayList<CatElement>) {
        adapter = AllCatAdapter(requireContext(), photo)
        recyclerView.adapter = adapter
    }


    private fun getPhotos(a: Int) {
        RetrofitHttp.photoService.listPost(page = a).enqueue(object :
            Callback<ArrayList<CatElement>> {
            override fun onResponse(
                call: Call<ArrayList<CatElement>>,
                response: Response<ArrayList<CatElement>>,
            ) {
                if (a == 1) {
                    refreshAdapter(response.body()!!)
                } else {
                    adapter.items.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                }
                Logger.d("@@@", "PhotosList -> ${response.body()}")

            }

            override fun onFailure(call: Call<ArrayList<CatElement>>, t: Throwable) {
                Logger.d("@@@", "PhotoList -> ${t.localizedMessage}")
            }
        })
    }
}