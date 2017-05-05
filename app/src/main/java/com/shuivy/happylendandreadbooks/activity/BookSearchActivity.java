package com.shuivy.happylendandreadbooks.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.shuivy.happylendandreadbooks.R;
import com.shuivy.happylendandreadbooks.adapter.BookListAdapter;
import com.shuivy.happylendandreadbooks.database.MyDataBaseHelper;
import com.shuivy.happylendandreadbooks.models.BookInfo;
import com.shuivy.happylendandreadbooks.models.BookInfoList;
import com.shuivy.happylendandreadbooks.util.BitmapToBase64;
import com.shuivy.happylendandreadbooks.util.ListUtility;
import com.shuivy.happylendandreadbooks.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BookSearchActivity extends AppCompatActivity {

    private TextView submit;
    private TextView cancel;
    private Context mContext;
    private List<BookInfoList> searchBooks;
    private BookListAdapter searchBookListAdapter;
    private int flag;
    private ListView listView;
    private ProgressDialog dialog;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        mContext = getApplicationContext();
        dialog = new ProgressDialog(this);
        dialog.setTitle("加载中");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag",0);
        initView();

    }

    public void initView() {
        listView = (ListView) findViewById(R.id.list_search_book);
        submit = (TextView) findViewById(R.id.book_search_ok);
        cancel = (TextView) findViewById(R.id.book_search_cancel);

        searchBooks = new ArrayList<BookInfoList>();
        if(flag==1){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
            dialog.show();
            BmobQuery<BookInfo> query= new BmobQuery<BookInfo>();
            query.findObjects(new FindListener<BookInfo>() {
                @Override
                public void done(List<BookInfo> list, BmobException e) {
                    if(e==null){

                        for(int i=0;i<list.size();i++){
                            BookInfo book = list.get(i);
                            BookInfoList booklist= new BookInfoList();
                            booklist.setTitle(book.getTitle());
                            booklist.setTime(book.getCreateDate());
                            booklist.setLocation(book.getLocation());
                            booklist.setPublishType(book.getPublishType());
                            booklist.setImg(new BitmapToBase64().base64ToBitmap(book.getImg()));
                            booklist.setObjectId(book.getObjectId());
                            searchBooks.add(booklist);

                        }
                        //Toast.makeText(getActivity(),list.get(1).getTitle(),Toast.LENGTH_SHORT).show();

                        searchBookListAdapter =  new BookListAdapter(mContext,searchBooks);
                        listView.setAdapter(searchBookListAdapter);
                        dialog.dismiss();

                    }else{
                        Log.i("bmob","失败:"+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBooks.clear();
                editText = (EditText)findViewById(R.id.title_book_search_key);
                String value = editText.getText().toString();
                ToastUtil.showToast(getApplicationContext(), "查询结果如下~");
                dialog.show();
                BmobQuery<BookInfo> query= new BmobQuery<BookInfo>();
                query.addWhereEqualTo("title",value);
                query.findObjects(new FindListener<BookInfo>() {
                    @Override
                    public void done(List<BookInfo> list, BmobException e) {
                        if(e==null){

                            for(int i=0;i<list.size();i++){
                                BookInfo book = list.get(i);
                                BookInfoList booklist= new BookInfoList();
                                booklist.setTitle(book.getTitle());
                                booklist.setTime(book.getCreateDate());
                                booklist.setLocation(book.getLocation());
                                booklist.setPublishType(book.getPublishType());
                                booklist.setImg(new BitmapToBase64().base64ToBitmap(book.getImg()));
                                booklist.setObjectId(book.getObjectId());
                                searchBooks.add(booklist);

                            }
                            //Toast.makeText(getActivity(),list.get(1).getTitle(),Toast.LENGTH_SHORT).show();

                            searchBookListAdapter =  new BookListAdapter(mContext,searchBooks);
                            listView.setAdapter(searchBookListAdapter);
                            dialog.dismiss();

                        }else{
                            Log.i("bmob","失败:"+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });

                //searchBooks = MyDataBaseHelper.getInstance(mContext).getSearchBooks();
                //searchBookListAdapter = new BookListAdapter(mContext, searchBooks);
                listView.setAdapter(searchBookListAdapter);
                ListUtility.setListViewHeightBasedOnChildren(listView);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookInfoList book = searchBooks.get(i);
                Intent intent = new Intent();
                intent.putExtra("objectId",book.getObjectId());

              /*  BookInfoS book = new BookInfoS();
                book.setTitle(temp.getTitle());
                book.setDes(temp.getDes());
                book.setCreateDate(temp.getCreateDate());
                book.setPublishType(temp.getPublishType());
//                ToastUtil.showToast(getActivity().getApplicationContext(),book.getTitle());
                Intent intent = new Intent();
                intent.setClass(getActivity(),BookDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book",book);*/
                intent.setClass(mContext,BookDetailActivity.class);
                //intent.putExtra("title",book.getTitle());
                startActivityForResult(intent,1);

            }
        });

    }

}
