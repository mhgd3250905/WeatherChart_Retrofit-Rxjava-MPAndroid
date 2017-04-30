package com.skkk.okhttp3stydy;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomChartActivity extends AppCompatActivity {

    @BindView(R.id.chart_custom)
    LineChart mChartCustom;
    Handler handler=new Handler();
    private Runnable valueChooseRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_chart);
        ButterKnife.bind(this);

        valueChooseRunnable = new Runnable() {
            @Override
            public void run() {
                stopHighLight(mChartCustom);
            }
        };

        //获取数据
        List<Entry> entries = loadData();

        LineDataSet dataSet = new LineDataSet(entries, "custom Data");
        //设置数据集合
        setDataset(dataSet);
        LineData lineData = new LineData(dataSet);
        //设置XY轴
        setXYAxis(mChartCustom);
        //设置chart
        setChart(mChartCustom);
        mChartCustom.setData(lineData);
        //设置chart拖拽逻辑
        setChartDragMode(mChartCustom);
        mChartCustom.invalidate();
    }

    /**
     * 设置chart
     * @param
     */
    private void setChart(LineChart mChartCustom) {
        MyMarkerView myMarkerView=new MyMarkerView(this, R.layout.item_markerview);
        myMarkerView.setChartView(mChartCustom);
        mChartCustom.setMarker(myMarkerView);
    }

    /**
     * 设置DataSet
     * @param dataSet
     */
    private void setDataset(LineDataSet dataSet) {
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setDrawValues(false);   //不绘制数值
        dataSet.setDrawCircles(false);  //不画小圆圈
        dataSet.setColor(ContextCompat.getColor(this, R.color.colorAccent));    //设置数据颜色

        dataSet.setHighLightColor(Color.BLACK); //设置高亮颜色
        dataSet.setHighlightEnabled(true);  //打开高亮开关
        dataSet.setHighlightLineWidth(1f);  //设置高亮宽度
        dataSet.setDrawHighlightIndicators(true);   //绘制高亮
        dataSet.setDrawVerticalHighlightIndicator(true);    //绘制垂直高亮
        dataSet.setDrawHorizontalHighlightIndicator(false); //不绘制水平高亮

    }

    /**
     * 设置XY轴
     * @param mChartCustom
     */
    private static void setXYAxis(LineChart mChartCustom) {
        //设置又Y轴不显示
        YAxis axisRight = mChartCustom.getAxisRight();
        axisRight.setDrawLabels(false);
        axisRight.setDrawGridLines(false);

        //设置X轴
        XAxis xAxis = mChartCustom.getXAxis();
        xAxis.setDrawGridLines(false);  //不显示网格线
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);      //设置X轴label位置
        //设置Y轴
        YAxis axisLeft = mChartCustom.getAxisLeft();
        axisLeft.setDrawGridLines(false);   //不显示网格线
        axisLeft.setAxisMinimum(-2f);   //最小值为-2
        axisLeft.setAxisMaximum(2f);    //最大值为2
    }

    /**
     * 初始化数据
     * @return 数据
     */
    private List<Entry> loadData() {
        List<Entry> entries=new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            entries.add(new Entry(i, (float) Math.sin((i%80)*(2*Math.PI/80))));
        }
        return entries;
    }


    public void setChartDragMode(final LineChart mChartCustom) {
        //设置数据选择样式
        mChartCustom.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //选中数据高亮之后5秒消失
                if (handler!=null){
                    handler.removeCallbacks(valueChooseRunnable);
                    handler.postDelayed(valueChooseRunnable,3000);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //设置拖拽模式
        mChartCustom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Highlight[] highlights = mChartCustom.getHighlighted();     //获取highLight
                        if (highlights==null){
                            break;
                        }
                        float highlightPx = highlights[0].getXPx();     //获取highlight横坐标
                        if (Math.abs(highlightPx-event.getX())<100){    //如果触摸距离在highlight范围内
                            //设置chart无法拖拽，highLight可以被拖拽
                            mChartCustom.setDragEnabled(false);
                            mChartCustom.setHighlightPerDragEnabled(true);
                        }else {     //如果不在highLight范围内那么就设置为chart拖拽，highLight无法被拖拽
                            mChartCustom.setDragEnabled(true);
                            stopHighLight(mChartCustom);    //取消高亮
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void stopHighLight(LineChart mChartCustom) {
        mChartCustom.highlightValues(null);
    }
}
