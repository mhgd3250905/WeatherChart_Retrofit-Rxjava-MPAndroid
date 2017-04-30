package com.skkk.okhttp3stydy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.skkk.okhttp3stydy.Gson.Weather;
import com.skkk.okhttp3stydy.Gson.Weather3HoursDetailsInfo;
import com.skkk.okhttp3stydy.Gson.WeatherDetailsInfo;
import com.skkk.okhttp3stydy.Gson.WeatherGson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.activity_main)
    NestedScrollView mActivityMain;
    @BindView(R.id.lc_weather_future)
    LineChart mDayLineChart;
    @BindView(R.id.lc_weather_detail)
    LineChart mInfoLineChart;
    private Call call;
    private String text = "";
    private IAxisValueFormatter xFormatter;
    private IAxisValueFormatter yFormatter;
    private IAxisValueFormatter infoXFormatter;
    private IAxisValueFormatter infoYFormatter;
    private IValueFormatter vFormatter;
    private String[] days = new String[7];
    private String[] times = new String[9];
    private IMarker mIMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mIMarker = new MyMarkerView(MainActivity.this, R.layout.item_markerview);

        //设置Y轴的格式
        yFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "°";
            }
        };

        //设置value的格式
        vFormatter = new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHndler) {
                return value + "°";
            }
        };

    }

    @OnClick(R.id.button2)
    public void onClick() {
        WeatherInterface weatherInterface = HttpUtils.getInstance().getWeatherInterface();

        Observable<WeatherGson> weatherRequest = weatherInterface.getWeather("101020600");
        weatherRequest.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeatherGson, List<LineDataSet>>() {
                    @Override
                    public List<LineDataSet> call(WeatherGson weatherGson) {
                        //获取未来天气
                        WeatherDetailsInfo weatherDetailsInfo = weatherGson.getValue().get(0).getWeatherDetailsInfo();
                        String publishTime = weatherDetailsInfo.getPublishTime();

                        List<Weather3HoursDetailsInfo> weather3HoursDetailsInfos = weatherDetailsInfo.getWeather3HoursDetailsInfos();
                        List<Entry> entryInfoListH=new ArrayList<Entry>();
                        List<Entry> entryInfoListL=new ArrayList<Entry>();

                        for (int i = 0; i < weather3HoursDetailsInfos.size(); i++) {
                            //获取一天中的最高温和最低温的曲线
                            entryInfoListH.add(new Entry(i,Float.valueOf(weather3HoursDetailsInfos.get(i).getHighestTemperature())));
                            entryInfoListL.add(new Entry(i,Float.valueOf(weather3HoursDetailsInfos.get(i).getLowerestTemperature())));
                            //将时间放到数组中
                            times[i]= weather3HoursDetailsInfos.get(i).getStartTime();

                        }
                        //设置X轴的格式
                        infoXFormatter = new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return times[(int) value].split(" ")[1].split(":")[0]+"时";
                            }
                        };

                        List<Weather> weathers = weatherGson.getValue().get(0).getWeathers();
                        List<Entry> entryListD = new ArrayList<Entry>();
                        List<Entry> entryListN = new ArrayList<Entry>();

                        for (int i = 0; i < weathers.size(); i++) {
                            //将天气对象转化为图标中的数据元
                            entryListD.add(new Entry(i, Float.valueOf(weathers.get(i).getTempDayC())));
                            entryListN.add(new Entry(i, Float.valueOf(weathers.get(i).getTempNightC())));
                            //将星期几添加到数组中
                            days[i] = weathers.get(i).getWeek();
                        }

                        //设置X轴的格式
                        xFormatter = new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return days[(int) value];
                            }
                        };

                        List<LineDataSet> lineDataSetList = new ArrayList<LineDataSet>();

                        lineDataSetList.add(new LineDataSet(entryListD, getString(R.string.future_weather_day)));
                        lineDataSetList.add(new LineDataSet(entryListN, getString(R.string.future_weather_night)));
                        lineDataSetList.add(new LineDataSet(entryInfoListH,getString(R.string.future_high_temp)));
                        lineDataSetList.add(new LineDataSet(entryInfoListL,getString(R.string.future_low_temp)));

                        //返回直线图数据对象
                        return lineDataSetList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LineDataSet>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "完成网络！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<LineDataSet> lineDataSetList) {
                        //显示图表
                        showChart(lineDataSetList);
                    }
                });
        //Retrofit同步请求
//        try {
//            Response response = call.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Retrofit异步请求
//        weather.enqueue(new retrofit2.Callback<WeatherGson>() {
//            @Override
//            public void onResponse(retrofit2.Call<WeatherGson> call, retrofit2.Response<WeatherGson> response) {
//                WeatherGson weather = response.body();
//                Toast.makeText(MainActivity.this, weather.getCode(), Toast.LENGTH_SHORT).show();
//                Log.d("MainActivity", "response.headers():" + response.headers());
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<WeatherGson> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 设置图表
     *
     * @param dataSetList 折线图数据
     */
    private void showChart(List<LineDataSet> dataSetList) {
        //设置日间温度曲线
        dataSetList.get(0).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSetList.get(0).setColor(ContextCompat.getColor(this,R.color.colorAccent));
        dataSetList.get(0).setDrawCircleHole(false);
        dataSetList.get(0).setDrawCircles(false);

        dataSetList.get(2).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSetList.get(2).setColor(ContextCompat.getColor(this,R.color.colorAccent));
        dataSetList.get(2).setDrawCircleHole(false);
        dataSetList.get(2).setDrawCircles(false);

        //设置晚间温度曲线
        dataSetList.get(1).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSetList.get(1).setColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        dataSetList.get(1).setDrawCircleHole(false);
        dataSetList.get(1).setDrawCircles(false);

        dataSetList.get(3).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSetList.get(3).setColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        dataSetList.get(3).setDrawCircleHole(false);
        dataSetList.get(3).setDrawCircles(false);

        //设置数据
        LineData dayLineData = new LineData();
        LineData infoLineData = new LineData();
        for (int i = 0; i < dataSetList.size(); i++) {
            if (i<2){
                dayLineData.addDataSet(dataSetList.get(i));
            }else {
                infoLineData.addDataSet(dataSetList.get(i));
            }
        }

        dayLineData.setValueFormatter(vFormatter);
        dayLineData.setValueTextSize(8f);
        dayLineData.setValueTextColor(Color.BLACK);

        infoLineData.setValueFormatter(vFormatter);
        infoLineData.setValueTextSize(8f);
        infoLineData.setValueTextColor(Color.BLACK);


        //设置X轴
        XAxis dayXAxis = mDayLineChart.getXAxis();
        dayXAxis.setDrawGridLines(false);
        dayXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        dayXAxis.setValueFormatter(xFormatter);

        //设置Y轴right
        YAxis axisRight = mDayLineChart.getAxisRight();
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);

        //设置Y轴left
        YAxis axisLeft = mDayLineChart.getAxisLeft();
        axisLeft.setDrawAxisLine(false);
        axisLeft.setDrawGridLines(false);
        axisLeft.setDrawLabels(false);

        //设置chart
        Description description = new Description();
        description.setText("气温预测图");
        description.setTextSize(15f);
        description.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        mDayLineChart.setDescription(description);
        mDayLineChart.setData(dayLineData);
        mDayLineChart.setMarker(mIMarker);
        mDayLineChart.invalidate();

        //设置X轴
        XAxis infoXAxis = mInfoLineChart.getXAxis();
        infoXAxis.setDrawAxisLine(true);
        infoXAxis.setDrawGridLines(false);
        infoXAxis.setDrawLabels(true);
        infoXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        infoXAxis.setValueFormatter(infoXFormatter);

        //设置Y轴right
        YAxis infoAxisRight = mInfoLineChart.getAxisRight();
        infoAxisRight.setDrawAxisLine(false);
        infoAxisRight.setDrawGridLines(false);
        infoAxisRight.setDrawLabels(false);

        //设置Y轴left
        YAxis infoAxisLeft = mInfoLineChart.getAxisLeft();
        infoAxisLeft.setDrawAxisLine(true);
        infoAxisLeft.setDrawGridLines(false);
        infoAxisLeft.setDrawLabels(true);
        infoAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);


        //设置chart
        Description description2 = new Description();
        description2.setText("气温预测图");
        description2.setTextSize(15f);
        description2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        mInfoLineChart.setDescription(description2);
        mInfoLineChart.setData(infoLineData);
        mInfoLineChart.setMarker(mIMarker);

        mInfoLineChart.fitScreen();
        mInfoLineChart.setViewPortOffsets(0,0,0,0);
        mInfoLineChart.invalidate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        call.cancel();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
