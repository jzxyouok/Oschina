package com.mydroid.oschina.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mydroid.oschina.adapter.FollowFansAdapter;
import com.mydroid.oschina.base.BaseListFragment;
import com.mydroid.oschina.bean.Friend;
import com.mydroid.oschina.bean.FriendsList;
import com.mydroid.oschina.util.ApiService;
import com.mydroid.oschina.util.XmlUtils;

import java.util.List;

/**
 * 创建者     林伯任
 * 创建时间   2016/5/13 21:04
 * 描述	      ${TODO}
 * <p/>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class FansFragment extends BaseListFragment{
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(getContext());
    }
    @NonNull
    @Override
    protected BaseAdapter getListAdapter() {
        //设置数据适配器
        return new FollowFansAdapter();
    }


    @Override
    protected void requestData() {
        //粉丝的数据list0
        //http://188.188.4.41:8080/oschina/list/friends_list0/page0.xml
        ApiService.getFriends(mRequestQueue, listener, 0);
    }

    @Override
    protected Object parseData(String data) {
        return XmlUtils.toBean(FriendsList.class, data.getBytes());
    }

    @Override
    protected void updateUI(Object obj) {
        FriendsList newsList = (FriendsList) obj;
        List<Friend> list = newsList.getFriendlist();
        ((FollowFansAdapter)baseListAdapter).updateData(list);
    }
}