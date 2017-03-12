2017/3/12 11:01:54 
## Retrofit+okhttp网络请求 ##

### 1.添加依赖 ###
- ```okhttp```

	    compile 'com.squareup.okhttp3:okhttp:3.6.0'
    	compile 'com.squareup.okio:okio:1.11.0'

- ```Retrofit```

		compile 'com.squareup.retrofit2:retrofit:2.0.2'
    	compile 'com.squareup.retrofit2:converter-gson:2.0.2'
    	compile 'com.squareup.retrofit2:converter-scalars:2.0.2'
    	compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'

- ```Gson```
 
		compile files('libs/gson-2.6.2.jar')

### 2.接口申明 ###

**这里的测试url：**   
http://aider.meizu.com/app/weather/listWeather?cityIds=101010100

我们可以如下划分：
   
- baseUrl：   
http://aider.meizu.com/

- 功能区域:   
app/weather/listWeather

- 索引：   
cityIds=101010100

**所以接口设计如下：**

	public interface WeatherInterface {
	    @GET("app/weather/listWeather")
	    Call<WeatherGson> getWeather(@Query("cityIds")String cityIds);
	}

### 3.创建Retrofit实例 ###

	String baseUrl="http://aider.meizu.com/";
    Retrofit retrofit=new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build();
    Log.d("MainActivity", "Retrofit build 完毕");

### 4.根据接口实例化请求 ###

    WeatherInterface weatherInterface=retrofit.create(WeatherInterface.class);
    call=weatherInterface.getWeather("101010100");

### 处理请求 ###

- 同步请求：

        Response response = call.execute();

- 异步请求：

	**注意这里需要分别实现请求失败和请求成功**

        call.enqueue(new retrofit2.Callback<WeatherGson>() {
            @Override
            public void onResponse(retrofit2.Call<WeatherGson> call, retrofit2.Response<WeatherGson> response) {
                WeatherGson weather = response.body();
                Toast.makeText(MainActivity.this, weather.getCode(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(retrofit2.Call<WeatherGson> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

## Rxjava+Retrofit+okhttp ##

**这是我们的重点**   
采用rxjava的方式重新实现一下上面的网络请求：

### 1.依赖添加 ###

    compile 'io.reactivex:rxjava:1.1.3'
    compile 'io.reactivex:rxandroid:1.1.0'


### 2.接口方法设置 ###

**返回值设置为Observable<GsonBean>**

- 接口中

		@GET("app/weather/listWeather")
    	Observable<WeatherGson> getWeather(@Query("cityIds")String cityIds);

- 主函数中

	    Observable<WeatherGson> weather = weatherInterface.getWeather("101010100");

### 3.请求处理 ###
**这里假定需求是从网络获取数据然后显示到UI中的textView中**
所以我们首先需要在```Scheduler.newThread()```中获取网络信息,然后再返回主线程```AndroidSchedulers.mainThread()```处理UI修改：

    weather.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<WeatherGson>() {
                @Override
                public void onCompleted() {
                    Toast.makeText(MainActivity.this, "完成网络！", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onError(Throwable e) {
                    Toast.makeText(MainActivity.this, "网络错误！", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNext(WeatherGson weatherGson) {
                    mTextView.setText(weatherGson.getCode());
                    Toast.makeText(MainActivity.this,weatherGson.getCode(), Toast.LENGTH_SHORT).show();
                }
            });

**简单粗暴~**



