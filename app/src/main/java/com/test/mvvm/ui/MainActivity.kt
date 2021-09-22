package com.test.mvvm.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.mvvm.R
import com.test.mvvm.adapter.BookListAdapter
import com.test.mvvm.viewmodel.BookListItemModel
import com.test.mvvm.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    lateinit var inputBookName: EditText
    lateinit var recyclerView: RecyclerView

    lateinit var viewModel: MainViewModel

    private lateinit var bookListAdapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputBookName = findViewById(R.id.inputBookName)
        recyclerView = findViewById(R.id.recyclerView)

        initSearchBox()
        initRecyclerView()
    }

    

    fun initSearchBox() {
        inputBookName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData(s.toString())
            }
        })
    }

    private fun loadAPIData(query: String) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getBookListObserver().observe(this, Observer<BookListItemModel> {
            if (it != null) {
                bookListAdapter.bookListData = it.items
                bookListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "No result found", Toast.LENGTH_SHORT)
            }
        })
        viewModel.makeApiCall(query)
    }

    fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(decoration)
            bookListAdapter = BookListAdapter()
            adapter = bookListAdapter
        }
    }
}