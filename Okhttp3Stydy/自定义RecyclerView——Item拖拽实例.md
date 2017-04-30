## 自定义RecyclerView——Item拖拽实例

>这次要模仿的是类似于锤子便签中的图片点击拖拽特效

![效果](https://github.com/mhgd3250905/SKRecyclerViewItemDemo/blob/master/img/SKRecyclerViewItemShow.gif?raw=true)

### 1.使用ItemTouchHelper工具类

为了更好的完成Item的拖拽，我们使用官方提供的实现拖拽工具类：```ItemTouchHelper```

详细使用可以学习这个系列：[RecyclerView的拖动和滑动 第一部分 ：基本的ItemTouchHelper示例](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0630/3123.html)

### 2.简单的拖拽实例

我尝试用一个我自己认为比较合适的顺序来实现；
#### 2.1 直接使用
```java
callback = new MyItemTouchHelperCallback(getContext(),adapter);
itemTouchHelper = new ItemTouchHelper(callback);
itemTouchHelper.attachToRecyclerView(mRvFragment);
```
上面的代码告诉了我们如何对我们的```RecyclerView```直接使用这个工具类，可以看到我们需要实例化```ItemTouchHelper```,还需要其对应的Callback，代码很简单，直接贴了；

#### 2.2 实现ItemTouchHelperCallback
```java
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback{
    private Context context;
    private ItemTouchHelperAdapter itemTouchHelperAdapter;


    public MyItemTouchHelperCallback(Context context,ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.context=context;
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
    }

    //设置拖拽方向以及左右滑动:这里默认实现了上下左右各个方向的拖动，可以按照需求使用
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag=ItemTouchHelper.UP|ItemTouchHelper.DOWN
                |ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        int swipeFlag=ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(dragFlag,swipeFlag);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //拖拽移动的时候处理
        if (itemTouchHelperAdapter !=null){
            itemTouchHelperAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //左右滑动的时候处理
        if (itemTouchHelperAdapter !=null){
            itemTouchHelperAdapter.onitemSwipe(viewHolder.getAdapterPosition());
        }
    }

    /**
     * 是否可以长按触发拖拽
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * 是否可以侧滑
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }
}
```

#### 2.3 Adapter实现接口方法
```java
//接口
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPos,int toPos);
    void onitemSwipe(int pos);
}
```

```java
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements ItemTouchHelperAdapter {

    ...

    /**
     * 上下拖动替换item位置
     *
     * @param fromPos
     * @param toPos
     */
    @Override
    public void onItemMove(int fromPos, int toPos) {
        Collections.swap(mDataList, fromPos, toPos);
        notifyItemMoved(fromPos, toPos);
    }

    /**
     * 左右滑动删除Item
     *
     * @param pos
     */
    @Override
    public void onitemSwipe(int pos) {
        mDataList.remove(pos);
        notifyItemRemoved(pos);
    }
}
```

这样我们就已经实现了基本的拖拽交换Item位置以及滑动删除Item；

### 3.点击图片进入拖拽状态

#### 3.1 进入拖拽状态
可以看到在我们的示例中，我们点击一个指定的图标就直接进入了拖拽状态，那么是如何实现呢？
```java
adapter.setOnStartDragListener(new OnStartDragListener() {
    @Override
    public void onStartDragListener(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
});
```
#### 3.2 实现图片点击监听
奏是这个方法，直接开始拖拽；这里可以看出，我们是给adapter设置了一个接口监听，当点击到图片的时候就触发这个方法，好吧，去实现：
```java
//监听接口
public interface OnStartDragListener {
    void onStartDragListener(RecyclerView.ViewHolder viewHolder);
}
```java
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements ItemTouchHelperAdapter {
    ...
    private OnStartDragListener onStartDragListener;

    public void setOnStartDragListener(OnStartDragListener onStartDragListener) {
        this.onStartDragListener = onStartDragListener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvItem.setText(mDataList.get(position));
        holder.ivItemMove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event)==MotionEvent.ACTION_DOWN
                        &&onStartDragListener!=null){
                    onStartDragListener.onStartDragListener(holder);
                }
                return false;
            }
        });
    }
}
```
很简单，当我们设置了监听之后，我们给这个图片设置触摸监听，只要```ACTION_DOWN```我们就触发拖拽效果；

#### 3.3 取消长按进入拖拽
当然既然我们有了拖拽的按钮就不需要长按进入拖拽了，在Callback中如此设置就可以：
```java
/**
 * 是否可以长按触发拖拽
 * @return
 */
@Override
public boolean isLongPressDragEnabled() {
    return false;
}
```

### 4 实现动画
在锤子便签中，图片拖拽时候的缩放真的是非常Nice的效果，所以我们也要实现：
重写```Callback```中的```onDrawChild```方法可以实现：
```java
/**
 * 在不同的状态进行不同状态的绘制
 * @param c 画板
 * @param recyclerView rv
 * @param viewHolder viewHolder
 * @param dX x方向拖动
 * @param dY y方向拖动
 * @param actionState 基本拖动类型：
 *                    ACTION_STATE_DRAG-->拖拽
 *                    ACTION_STATE_SWIPE-->滑动
 *                    ACTION_STATE_IDLE-->闲置
 * @param isCurrentlyActive 当前状态
 */
@Override
public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    View item = viewHolder.itemView;
    ViewGroup.LayoutParams layoutParams = item.getLayoutParams();
    //如果当前是拖拽状态
    if (actionState==ItemTouchHelper.ACTION_STATE_DRAG){
        if (isCurrentlyActive){//正在拖动
            if (layoutParams.height> DensityUtil.dip2px(context,100)){
                layoutParams.height-=50;
            }
        }else {
            if (layoutParams.height<DensityUtil.dip2px(context,200)){
                layoutParams.height+=50;
            }
        }
        item.setLayoutParams(layoutParams);
    }
}

```
注释已经说得比较清楚了，就不多做解释了;

### 5.源码传送门
  [我是一只咸鱼，不想承认，也不能否认，不要同情我笨，又夸我天真，还梦想着翻身...](https://github.com/mhgd3250905/SKRecyclerViewItemDemo)
