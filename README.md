# 使用Retrofit+Rxjava+MPAndroid来显示气温曲线图 #

> 这里是抓包抓来API接口：http://aider.meizu.com/app/weather/listWeather?cityIds=101020600

先看一下最终的结果：

![](https://ws1.sinaimg.cn/large/006aPzcjgy1fdozqfnrbxj30nq0l9jt5.jpg)

好吧，下面就正式开始项目吧~

## 1.导入第三方库 ##
	
	//okhttp
    compile 'com.squareup.okhttp3:okhttp:3.6.0'
    compile 'com.squareup.okio:okio:1.11.0'
    compile 
	//butterknife
    compile 'com.jakewharton:butterknife:8.5.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.5.1'
	//Rxjava
    compile 'io.reactivex:rxjava:1.1.3'
    compile 'io.reactivex:rxandroid:1.1.0'
	//Retrofit
    compile 'com.squareup.retrofit2:retrofit:2.0.2'
    compile 'com.squareup.retrofit2:converter-gson:2.0.2'
    compile 'com.squareup.retrofit2:converter-scalars:2.0.2'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
	//gson
    compile files('libs/gson-2.6.2.jar')
	//MPAndroid
    compile 'com.github.PhilJay:MPAndroidChart:v3.0.1'

## 2.初始化设置 ##

- 布局文件

		<?xml version="1.0" encoding="utf-8"?>
		<android.support.v4.widget.NestedScrollView
		    xmlns:android="http://schemas.android.com/apk/res/android"
		    xmlns:tools="http://schemas.android.com/tools"
		    android:id="@+id/activity_main"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent"
		    android:paddingBottom="@dimen/activity_vertical_margin"
		    android:paddingLeft="@dimen/activity_horizontal_margin"
		    android:paddingRight="@dimen/activity_horizontal_margin"
		    android:paddingTop="@dimen/activity_vertical_margin"
		    tools:context="com.skkk.okhttp3stydy.MainActivity">
		
		    <LinearLayout
		        android:layout_width="match_parent"
		        android:layout_height="match_parent"
		        android:orientation="vertical">
		        <EditText
		            android:layout_width="match_parent"
		            android:layout_height="wrap_content"
		            android:inputType="textPersonName"
		            android:text="Demo"
		            android:gravity="center"
		            android:ems="10"
		            android:id="@+id/editText"
		            />
		        <Button
		            android:text="下载图片"
		            android:layout_width="match_parent"
		            android:layout_height="wrap_content"
		            android:id="@+id/button2"
		            />
		        <com.github.mikephil.charting.charts.LineChart
		            android:id="@+id/lc_weather_future"
		            android:layout_width="match_parent"
		            android:layout_height="300dp"
		            ></com.github.mikephil.charting.charts.LineChart>
		
		        <com.github.mikephil.charting.charts.LineChart
		            android:id="@+id/lc_weather_detail"
		           
		    </LinearLayout>
		</android.support.v4.widget.NestedScrollView>

	**很简单，一个标题，一个按钮，一个折线图**

- 然后我们设置一个Gson的接收类

	...（此处省略）

- Retrofit的接口文件

		public interface WeatherInterface {
		    //http://aider.meizu.com/app/weather/listWeather?cityIds=101010100
		    @GET("app/weather/listWeather")
		    Observable<WeatherGson> getWeather(@Query("cityIds")String cityIds);
		}

- 初始化网络请求

		String baseUrl = "http://aider.meizu.com/";
	        retrofit = new Retrofit.Builder()
	                .addConverterFactory(GsonConverterFactory.create())
	                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
	                .baseUrl(baseUrl)
	                .build();
		WeatherInterface weatherInterface=retrofit.create(WeatherInterface.class)

## 3.逻辑编写 ##

- 获取被监听者

		Observable<WeatherGson> weatherRequest = weatherInterface.getWeather("101020600");

- 网络请求

		weatherRequest.subscribeOn(Schedulers.newThread())

- 数据处理（将获取到的Gson数据转化为MPAndroid需求的数据）
	
	>这个demo中我们仅仅需要数据中的未来六天日夜间气温变化数据就

	1.获取目标数据
	
		//获取未来天气
        WeatherDetailsInfo weatherDetailsInfo = weatherGson.getValue().get(0).getWeatherDetailsInfo();
		List<Weather> weathers = weatherGson.getValue().get(0).getWeathers();
	
	2.转化为MPAndroid需求值List<Entry>

		List<Entry> entryListD = new ArrayList<Entry>();
        List<Entry> entryListN = new ArrayList<Entry>();

        for (int i = 0; i < weathers.size(); i++) {
        	//将天气对象转化为图标中的数据元
            entryListD.add(new Entry(i, Float.valueOf(weathers.get(i).getTempDayC())));
            entryListN.add(new Entry(i, Float.valueOf(weathers.get(i).getTempNightC())));
            //将星期几添加到数组中
            days[i] = weathers.get(i).getWeek();
		}

	3.这里还需要设置一下chart中的横轴坐标格式器
		
		//设置X轴的格式
		xFormatter = new IAxisValueFormatter() {
       	@Override
       	public String getFormattedValue(float value, AxisBase axis) {
           return days[(int) value];
       		}
		};
	
	4.设置折线图数据并返回

		List<LineDataSet> lineDataSetList = new ArrayList<LineDataSet>();
		lineDataSetList.add(new LineDataSet(entryListD, getString(R.string.future_weather_day)));
		lineDataSetList.add(new LineDataSet(entryListN, getString
		//返回直线图数据对象
		return lineDataSetList;

## 4.监听者主线程更新UI ##
	
- 接收数据

	    Observable<WeatherGson> weatherRequest = weatherInterface.getWeather("101020600");
	    weatherRequest.subscribeOn(Schedulers.newThread())
	            .subscribeOn(Schedulers.io())
	            .map(new Func1<WeatherGson, List<LineDataSet>>() {
	                @Override
	                public List<LineDataSet> call(WeatherGson weatherGson) {
						...
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

- 更新chart

	    private void showChart(List<LineDataSet> dataSetList) {
	        //设置日间温度曲线
	        dataSetList.get(0).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
	        dataSetList.get(0).setColor(ContextCompat.getColor(this,R.color.colorAccent));
	        dataSetList.get(0).setDrawCircleHole(false);
	        dataSetList.get(0).setDrawCircles(false);
	        
	        //设置晚间温度曲线
	        dataSetList.get(1).setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
	        dataSetList.get(1).setColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
	        dataSetList.get(1).setDrawCircleHole(false);
	        dataSetList.get(1).setDrawCircles(false);
	        
	        //设置数据
	        LineData dayLineData = new LineData();
	        LineData infoLineData = new LineData();
	        for (int i = 0; i < dataSetList.size(); i++) {
	            dayLineData.addDataSet(dataSetList.get(i));
	        }
	        dayLineData.setValueFormatter(vFormatter);
	        dayLineData.setValueTextSize(8f);
	        dayLineData.setValueTextColor(Color.BLACK);
	
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

## 5.查看源码 ##

**一起进步吧，少年~**   
[点击这里查看源码](https://github.com/mhgd3250905/okhttp3_Study)


