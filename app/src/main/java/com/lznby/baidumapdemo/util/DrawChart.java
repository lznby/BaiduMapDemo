package com.lznby.baidumapdemo.util;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.lznby.baidumapdemo.entity.Pressure;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线图绘制类
 */
public class DrawChart {
    public static void drawChart(LineChart mLineChart, Pressure[] pressureArray){

        //水压最高限制
        float maxPressure = pressureArray[0].getPressure_maxlevel();

        //水压最低限制
        float minPressure = pressureArray[0].getPressure_minlevel();

        List<String> mList = new ArrayList<>();
        for (int i = 0; i < pressureArray.length; i++) {
            mList.add(pressureArray[i].getTime());
        }

        //显示边界顶部数据
        mLineChart.setDrawBorders(true);

        //设置数据描点
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < pressureArray.length; i++) {
            entries.add(new Entry(i, pressureArray[i].getPressure()));
        }

        //添加曲线说明
        LineDataSet lineDataSet = new LineDataSet(entries, "水压");//设置标注信息
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);

        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        //设置显示值的字体大小
        lineDataSet.setValueTextSize(9f);

        //设置y轴不可缩放
        mLineChart.setScaleYEnabled(false);

/*        //x轴描述
        Description description = new Description();
        description.setText("更新时间");
        description.setTextColor(Color.RED);
        mLineChart.setDescription(description);*/

        //隐藏描述
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);

        //得到x轴
        XAxis xAxis = mLineChart.getXAxis();

        //得到y轴
        YAxis leftYAxis = mLineChart.getAxisLeft();
        YAxis rightYAxis = mLineChart.getAxisRight();


        //设置x轴数据及样式
        setXAxisData(xAxis, mList);

        //设置X轴的刻度数量
        xAxis.setLabelCount(3, true);

        //设置y轴数据及样式
        setYAxisData(leftYAxis, rightYAxis, 0f, 1f);

        //在左侧y轴上绘制限制线
        drawLimitLine(leftYAxis,maxPressure,minPressure, "水压过高", "水压过低");

    }


    /**
     * 设置x轴样式及数据
     * @param xAxis
     */
    private  static void setXAxisData(XAxis xAxis, final List<String> mList) {
        /**
         * 这里设置x轴的样式及数据
         */

        //设置X轴的位置, 值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //设置X轴间最小距离
        xAxis.setGranularity(1f);

        //为x轴添加数据
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mList.get((int) value); //x轴数据集合
            }
        });

    }

    /**
     * 设置Y轴数据及样式
     * @param leftYAxis 左侧y轴
     * @param rightYAxis 右侧y轴
     * @param minimum y轴最小值
     * @param maximum y轴最大值
     */
    private static void setYAxisData(YAxis leftYAxis, YAxis rightYAxis, float minimum, float maximum) {
        //设置y轴值
        leftYAxis.setAxisMinimum(minimum);
        leftYAxis.setAxisMaximum(maximum);

        //设置y轴坐标刻度数
        leftYAxis.setLabelCount(10, true);

        //设置右边y轴不显示
        rightYAxis.setEnabled(false);

    }

    /**
     * 限制线LimitLine设置。y轴上绘制限制线。
     * @param yAxis 绘制LimitLine线的坐标
     * @param max   最高限制线值
     * @param limit 最低限制线值
     * @param maxDescribe 最高限制线值说明
     * @param limitDescribe 最低限制线值说明
     */
    private static void drawLimitLine(YAxis yAxis,float max, float limit, String maxDescribe, String limitDescribe){

        /**
         * 设置水压最高线
         */
        //得到限制线
        LimitLine maxLine = new LimitLine(max,maxDescribe);

        //限制线宽度
        maxLine.setLineWidth(1f);

        //限制线字体大小
        maxLine.setTextSize(10f);

        //限制线字体颜色
        maxLine.setTextColor(Color.RED);

        //限制线颜色
        maxLine.setLineColor(Color.BLUE);

        //限制线添加位置
        yAxis.addLimitLine(maxLine); //Y轴添加限制线

        /**
         * 设置水压最低线
         */
        //得到限制线
        LimitLine limitLine = new LimitLine(limit,limitDescribe);

        //限制线宽度
        limitLine.setLineWidth(1f);

        //限制线字体大小
        limitLine.setTextSize(10f);

        //限制线字体颜色
        limitLine.setTextColor(Color.RED);

        //限制线颜色
        limitLine.setLineColor(Color.BLUE);

        //限制线添加位置
        yAxis.addLimitLine(limitLine);
    }
}
