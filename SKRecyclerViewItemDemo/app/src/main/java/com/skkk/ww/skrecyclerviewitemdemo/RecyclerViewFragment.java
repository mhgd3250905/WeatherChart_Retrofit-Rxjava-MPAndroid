package com.skkk.ww.skrecyclerviewitemdemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends Fragment {

    @InjectView(R.id.rv_fragment)
    RecyclerView mRvFragment;

    private List<String> mDataList;
    private MyItemTouchHelperCallback callback;
    private ItemTouchHelper itemTouchHelper;

    public RecyclerViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        mDataList = loadData();
        //设置RecyclerView...
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        mRvFragment.setLayoutManager(layoutManager);
        MyAdapter adapter=new MyAdapter(getContext(),mDataList);
        mRvFragment.setAdapter(adapter);
        mRvFragment.setItemAnimator(new DefaultItemAnimator());

        callback = new MyItemTouchHelperCallback(getContext(),adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRvFragment);

        adapter.setOnStartDragListener(new OnStartDragListener() {
            @Override
            public void onStartDragListener(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            }
        });
    }

    /**
     * 加载数据
     */
    private List<String> loadData() {
        List<String> dates=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dates.add("这里是第"+i+"条item");
        }
        return dates;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
