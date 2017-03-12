package com.skkk.okhttp3stydy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.skkk.okhttp3stydy.Gson.WeatherGson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.activity_main)
    ConstraintLayout mActivityMain;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.lineChart)
    LineChart mLineChart;
    private Call call;
    private String text = "";
    private IAxisValueFormatter xFormatter;
    private IAxisValueFormatter yFormatter;
    private IValueFormatter vFormatter;
    private String[] days = new String[7];
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
        String baseUrl = "http://aider.meizu.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();

        Log.d("MainActivity", "Retrofit build 完毕");

        WeatherInterface weatherInterface = retrofit.create(WeatherInterface.class);
        Observable<WeatherGson> weatherRequest = weatherInterface.getWeather("101020600");

        weatherRequest.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<WeatherGson, List<LineDataSet>>() {
                    @Override
                    public List<LineDataSet> call(WeatherGson weatherGson) {
                        //获取未来天气
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

     * @param dataSetList 折线图数据
     */
    private void showChart(List<LineDataSet> dataSetList) {
        //设置日间温度曲线
        dataSetList.get(0).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSetList.get(0).setColor(getResources().getColor(R.color.colorAccent));
        dataSetList.get(0).setDrawCircleHole(false);
        dataSetList.get(0).setDrawCircles(false);

        //设置晚间温度曲线
        dataSetList.get(1).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSetList.get(1).setColor(getResources().getColor(R.color.colorPrimaryDark));
        dataSetList.get(1).setDrawCircleHole(false);
        dataSetList.get(1).setDrawCircles(false);

        //设置数据
        LineData lineData = new LineData();
        for (LineDataSet lineDataSet : dataSetList) {
            lineData.addDataSet(lineDataSet);
        }
        lineData.setValueFormatter(vFormatter);
        lineData.setValueTextSize(8f);
        lineData.setValueTextColor(Color.BLACK);


        //设置X轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(xFormatter);

        //设置Y轴right
        YAxis axisRight = mLineChart.getAxisRight();
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);

        //设置Y轴left
        YAxis axisLeft = mLineChart.getAxisLeft();
        axisLeft.setDrawAxisLine(false);
        axisLeft.setDrawGridLines(false);
        axisLeft.setDrawLabels(false);

        //设置chart
        Description description = new Description();
        description.setText("气温预测图");
        description.setTextSize(15f);
        description.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        mLineChart.setDescription(description);
        mLineChart.setData(lineData);
        mLineChart.setMarker(mIMarker);
        mLineChart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        call.cancel();
    }
}
