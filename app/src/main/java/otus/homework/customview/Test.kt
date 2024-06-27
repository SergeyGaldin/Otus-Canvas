package otus.homework.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random
import kotlin.math.min

class LineChartVietEST(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val dataSet = mutableListOf<DataPoint>()
    private var xMin = 0
    private var xMax = 0
    private var yMin = 0
    private var yMax = 0

    private val dataPointPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 7f
        style = Paint.Style.STROKE
    }

    private val dataPointFillPaint = Paint().apply {
        color = Color.WHITE
    }

    private val dataPointLinePaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 7f
        isAntiAlias = true
    }

    private val axisLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        dataSet.forEachIndexed { index, currentDataPoint ->
            val realX = currentDataPoint.xVal.toRealX()
            val realY = currentDataPoint.yVal.toRealY()

            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val startX = currentDataPoint.xVal.toRealX()
                val startY = currentDataPoint.yVal.toRealY()
                val endX = nextDataPoint.xVal.toRealX()
                val endY = nextDataPoint.yVal.toRealY()
                canvas.drawLine(startX, startY, endX, endY, dataPointLinePaint)
            }

            canvas.drawCircle(realX, realY, 7f, dataPointFillPaint)
            canvas.drawCircle(realX, realY, 7f, dataPointPaint)
        }

        canvas.drawLine(0f, 0f, 0f, height.toFloat(), axisLinePaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), axisLinePaint)
    }

    fun setData() {
        val newDataSet: List<DataPoint> = generateRandomDataPoints()
        xMin = newDataSet.minBy { it.xVal }.xVal
        xMax = newDataSet.maxBy { it.xVal }.xVal
        yMin = newDataSet.minBy { it.yVal }.yVal
        yMax = newDataSet.maxBy { it.yVal }.yVal
        dataSet.clear()
        dataSet.addAll(newDataSet)
        invalidate()
    }
    private fun generateRandomDataPoints(): List<DataPoint> {
        val random = Random()
        return (0..20).map {
            DataPoint(it, random.nextInt(50) + 1)
        }
    }
    private fun Int.toRealX() = toFloat() / xMax * width
    private fun Int.toRealY() = toFloat() / yMax * height
}

data class DataPoint(
    val xVal: Int,
    val yVal: Int
)