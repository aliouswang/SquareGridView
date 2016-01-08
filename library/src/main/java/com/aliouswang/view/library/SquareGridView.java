package com.aliouswang.view.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 *
 * This is not a sub grid view, It just for display multi images.
 * It can use in recycler view and list view.
 * We have do super optimize for you. Please be free to use. :)
 *
 * Note: You should never add this view into horizontal
 * linearLayout, the height can't show correctly.
 *
 * Created by aliouswang on 16/1/8.
 */
public class SquareGridView extends ViewGroup{

    public static final int DEFAULT_MAX_SIZE = 9;
    public static final int DEFAULT_COLUMN_NUM = 3;
    public static final int DEFAULT_RATIO = 1;
    public static final int DEFAULT_HORIZONTAL_SPACE = 10;
    public static final int DEFAULT_VERTICAL_SPACE = 10;

    private int numColumns = DEFAULT_COLUMN_NUM;
    private int maxSize = DEFAULT_MAX_SIZE;
    private int horizontalSpacing;
    private int verticalSpacing;
    private float ratio;
    private int childrenWidth;
    private int childrenHeight;

    private SquareViewAdapter squareViewAdapter;

    public SquareGridView(Context context) {
        super(context);
        initAttr(context, null);
    }

    public SquareGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public SquareGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes
                    (attrs, R.styleable.SquareGridView);
            numColumns = typedArray.getInteger(R.styleable.SquareGridView_numColumns, DEFAULT_COLUMN_NUM);
            maxSize = typedArray.getInteger(R.styleable.SquareGridView_maxSize, DEFAULT_MAX_SIZE);
            horizontalSpacing = typedArray.
                    getDimensionPixelSize(R.styleable.SquareGridView_horizontalSpacing, DEFAULT_HORIZONTAL_SPACE);
            verticalSpacing = typedArray.
                    getDimensionPixelSize(R.styleable.SquareGridView_verticalSpacing, DEFAULT_VERTICAL_SPACE);
            ratio = typedArray.getFloat(R.styleable.SquareGridView_ratio, DEFAULT_RATIO);
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width, height;

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        width = widthSpecSize;
        height = heightSpecSize;

        int count = getRealCount();
        float rowCount = (count + 0f) / numColumns;
        int realRow = (int) Math.ceil(rowCount);


        childrenWidth = (width - getPaddingLeft() - getPaddingRight()
                - (numColumns - 1) * horizontalSpacing) / numColumns;
        childrenHeight = (int) (childrenWidth * ratio);

        height = getPaddingTop() + getPaddingBottom() + realRow * childrenHeight
                + (realRow - 1) * verticalSpacing;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getRealCount();
        for (int i = 0; i < count; i ++) {
            int row = i / numColumns;
            int column = i % numColumns;
            int left = getPaddingLeft() + column * horizontalSpacing + column * childrenWidth;
            int top = getPaddingTop() + row * verticalSpacing + row * childrenHeight;
            View childView = getChildAt(i);
            childView.layout(left, top, left + childrenWidth, top + childrenHeight);
        }
    }

    public void setAdapter(final SquareViewAdapter adapter) {
        this.squareViewAdapter = adapter;
        int count = getRealCount();
        int childCount = getChildCount();
        int shortCount = count - childCount;
        if (shortCount > 0) {
            //we need add new subview.
            for (int i = 0;i < shortCount; i++) {
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());

                GenericDraweeHierarchyBuilder builder =
                        new GenericDraweeHierarchyBuilder(getResources());
                builder.setPlaceholderImage(ContextCompat.getDrawable(getContext(),
                        R.drawable.default_load_failed_image),
                        ScalingUtils.ScaleType.FIT_XY);
                simpleDraweeView.setHierarchy(builder.build());

                simpleDraweeView.setTag(i + childCount);
                ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
                        childrenWidth, childrenHeight
                );
                this.addView(simpleDraweeView, vlp);
            }
        }else if(shortCount < 0){
            for (int i = 0;i < Math.abs(shortCount); i ++) {
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) getChildAt(i + count);
                simpleDraweeView.setVisibility(View.GONE);
            }
        }
        for (int i = 0;i < count; i++) {
            final int index = i;
            final SimpleDraweeView simpleDraweeView = (SimpleDraweeView) getChildAt(i);
            simpleDraweeView.setVisibility(View.VISIBLE);
            simpleDraweeView.setImageURI(Uri.parse(squareViewAdapter.getImageUrl(i)));
            simpleDraweeView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (adapter != null) {
                        adapter.onItemClick(simpleDraweeView, index, adapter.getItem(index));
                    }
                }
            });
        }
    }

    /**
     * Real item count.
     *
     * @return
     */
    public int getRealCount() {
        int count = getItemCount();
        count = Math.min(count, maxSize);
        return count;
    }

    public int getItemCount() {
        if (this.squareViewAdapter != null) {
            return this.squareViewAdapter.getCount();
        }else return 0;
    }
}
