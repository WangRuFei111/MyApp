package com.ymylibrary.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;

import androidx.viewpager.widget.ViewPager;

import java.util.Calendar;
import java.util.List;


public class MonthCalendarView extends ViewPager implements OnMonthClickListener {

    private MonthAdapter mMonthAdapter;
    private OnCalendarClickListener mOnCalendarClickListener;

    public MonthCalendarView(Context context) {
        this(context, null);
    }

    public MonthCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        addOnPageChangeListener(mOnPageChangeListener);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        initMonthAdapter(context, context.obtainStyledAttributes(attrs, R.styleable.MonthCalendarView));
    }

    private void initMonthAdapter(Context context, TypedArray array) {
        mMonthAdapter = new MonthAdapter(context, array, this);
        setAdapter(mMonthAdapter);
        setCurrentItem(mMonthAdapter.getMonthCount() / 2, false);
    }

    @Override
    public void onClickThisMonth(int year, int month, int day) {
        if (mOnCalendarClickListener != null) {
            mOnCalendarClickListener.onClickDate(year, month, day);
        }
    }

    @Override
    public void onClickLastMonth(int year, int month, int day) {
        MonthView monthDateView = mMonthAdapter.getViews().get(getCurrentItem() - 1);
        if (monthDateView != null) {
            monthDateView.setSelectYearMonth(year, month, day);
        }
        setCurrentItem(getCurrentItem() - 1, true);
    }

    @Override
    public void onClickNextMonth(int year, int month, int day) {
        MonthView monthDateView = mMonthAdapter.getViews().get(getCurrentItem() + 1);
        if (monthDateView != null) {
            monthDateView.setSelectYearMonth(year, month, day);
            monthDateView.invalidate();
        }
        onClickThisMonth(year, month, day);
        setCurrentItem(getCurrentItem() + 1, true);
    }

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int position) {
            MonthView monthView = mMonthAdapter.getViews().get(getCurrentItem());
            if (monthView != null) {
                monthView.clickThisMonth(monthView.getSelectYear(), monthView.getSelectMonth(), monthView.getSelectDay());
                if (mOnCalendarClickListener != null) {
                    mOnCalendarClickListener.onPageChange(monthView.getSelectYear(), monthView.getSelectMonth(), monthView.getSelectDay());
                }
            } else {
                MonthCalendarView.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onPageSelected(position);
                    }
                }, 50);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * ???????????????
     */
    public void setTodayToView() {
        setCurrentItem(mMonthAdapter.getMonthCount() / 2, true);
        MonthView monthView = mMonthAdapter.getViews().get(mMonthAdapter.getMonthCount() / 2);
        if (monthView != null) {
            Calendar calendar = Calendar.getInstance();
            monthView.clickThisMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        }
    }

    /**
     * ????????????????????????
     */
    public void setUpMonth(int year, int month) {
        setCurrentItem(mMonthAdapter.getMonthCount() / 2, true);
        MonthView monthView = mMonthAdapter.getViews().get(mMonthAdapter.getMonthCount() / 2);
        if (monthView != null) {


            if (month == 0) {
                year -= 1;
                month = 11;
            } else {
                month -= 1;
            }

            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH)==month) {
                monthView.clickThisMonth(year, month, calendar.get(Calendar.DATE));
            } else {
                monthView.clickThisMonth(year, month, 1);
            }


        }
    }


    /**
     * ????????????????????????
     */
    public void setDoMonth(int year, int month) {
        setCurrentItem(mMonthAdapter.getMonthCount() / 2, true);
        MonthView monthView = mMonthAdapter.getViews().get(mMonthAdapter.getMonthCount() / 2);
        if (monthView != null) {


            if (month == 11) {
                year += 1;
                month = 0;
            } else {
                month += 1;
            }

            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH)==month) {
                monthView.clickThisMonth(year, month, calendar.get(Calendar.DATE));
            } else {
                monthView.clickThisMonth(year, month, 1);
            }
        }
    }

    /**
     * ??????????????????
     */
    public void addDot(CalendarBean calendarBean) {
        setCurrentItem(mMonthAdapter.getMonthCount() / 2, true);
        MonthView monthView = mMonthAdapter.getViews().get(mMonthAdapter.getMonthCount() / 2);
        if (monthView != null) {
            monthView.addDot(calendarBean);
        }
    }

    /**
     * ?????????????????????
     */
    public void addDots(List<CalendarBean> listCalendarBean) {
        setCurrentItem(mMonthAdapter.getMonthCount() / 2, true);
        MonthView monthView = mMonthAdapter.getViews().get(mMonthAdapter.getMonthCount() / 2);
        if (monthView != null) {
            monthView.addDots(listCalendarBean);
        }
    }

    /**
     * ??????????????????
     */
    public void removeDot(CalendarBean calendarBean) {
        setCurrentItem(mMonthAdapter.getMonthCount() / 2, true);
        MonthView monthView = mMonthAdapter.getViews().get(mMonthAdapter.getMonthCount() / 2);
        if (monthView != null) {
            monthView.removeDot(calendarBean);
        }
    }


    /**
     * ??????????????????
     */
    public void removeDots(List<CalendarBean> listCalendarBean) {
        setCurrentItem(mMonthAdapter.getMonthCount() / 2, true);
        MonthView monthView = mMonthAdapter.getViews().get(mMonthAdapter.getMonthCount() / 2);
        if (monthView != null) {
            monthView.removeDots(listCalendarBean);
        }
    }


    /**
     * ????????????????????????
     *
     * @param onCalendarClickListener
     */
    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener) {
        mOnCalendarClickListener = onCalendarClickListener;
    }

    public SparseArray<MonthView> getMonthViews() {
        return mMonthAdapter.getViews();
    }

    public MonthView getCurrentMonthView() {
        return getMonthViews().get(getCurrentItem());
    }

}
