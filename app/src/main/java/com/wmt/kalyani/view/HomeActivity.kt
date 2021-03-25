package com.wmt.kalyani.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wmt.kalyani.R
import com.wmt.kalyani.adapter.UserAdapter
import com.wmt.kalyani.api.ApiRequest
import com.wmt.kalyani.api.ResponceCallback
import com.wmt.kalyani.model.UserData
import com.wmt.kalyani.model.UserResModel
import com.wmt.kalyani.utils.ConstantCode
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var adapter: UserAdapter? = null
    var manager: LinearLayoutManager? = null
    var list: ArrayList<UserResModel.DataList>? = ArrayList()
    private var page = 0
    private val page_size = 5
    private var isLastPage = false
    private var isLoading = false
    var token: String? = null
    var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupUI()
        Setpagination();
    }

    private fun Setpagination() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItem: Int = manager!!.getChildCount()
                val totalItem: Int = manager!!.getItemCount()
                val firstVisibleItemPosition: Int = manager!!.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItem + firstVisibleItemPosition >= totalItem && firstVisibleItemPosition >= 0 && totalItem >= page_size
                    ) {
                        page++
                        GetUserList()
                    }
                }
            }
        })

    }

    private fun setupUI() {
        token = ConstantCode.getsharedpreference(this, "token", "");
        type = ConstantCode.getsharedpreference(this, "token_type", "");
        page = 1
        manager = LinearLayoutManager(this);
        recyclerView.layoutManager = manager
        GetUserList()
        adapter = UserAdapter(this@HomeActivity, list!!)
        recyclerView.adapter = adapter
    }

    private fun GetUserList() {
        GetList(page)
    }

    private fun GetList(page: Int) {
        progressBar.visibility = View.VISIBLE
        isLoading = true;
        val model = UserData()
        model.page = page
        model.token = token
        model.type = type
        val request = ApiRequest()
        request.getusers(model, callback)
    }

    val callback: ResponceCallback = object : ResponceCallback {
        override fun ResponceSuccessCallback(o: Any?) {
            try {
                if (o is UserResModel) {
                    val main = o as UserResModel
                    val status = main.meta!!.status;
                    val msg = main.data;
                    list = main.data!!.users
                    if (status.equals("ok")) {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        isLoading = false;
                        if (list!!.size > 0) {
                            adapter!!.addList(list!!);
                            isLastPage = list!!.size < page_size;
                        } else {
                            isLastPage = true;
                        }
                    } else {
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun ResponceFailCallback(o: Any?) {
            isLoading = false;
        }

    }

}