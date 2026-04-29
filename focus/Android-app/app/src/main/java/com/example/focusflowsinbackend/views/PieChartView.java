package com.example.focusflowsinbackend.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom View para el gráfico de tarta en ResumenActivity.
 * Dibuja sectores proporcionales con un hueco central (donut).
 *
 * Uso:
 *   pieChart.setData(
 *       new float[]{ energiaPct, stresPct, cansancioPct },
 *       new int[]  { greenColor, purpleColor, orangeColor },
 *       sessionNumber
 *   );
 */
public class PieChartView extends View {

    private float[] values;        // Porcentajes (suman ~100)
    private int[]   colors;        // Color ARGB de cada segmento
    private int     sessionNumber; // Mostrado en el centro del hueco

    private Paint   segmentPaint;
    private Paint   holePaint;     // Relleno del hueco central
    private Paint   textPaint;
    private Paint   labelPaint;

    private RectF   ovalRect = new RectF();

    // ── Constructores ────────────────────────────────────────────────
    public PieChartView(Context context) {
        super(context); init();
    }
    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs); init();
    }
    public PieChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle); init();
    }

    private void init() {
        segmentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        segmentPaint.setStyle(Paint.Style.FILL);

        holePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        holePaint.setStyle(Paint.Style.FILL);
        holePaint.setColor(0xFF1A1724); // bg_dark — debe coincidir con el fondo

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xFFF2F0FA); // text_primary
        textPaint.setTextSize(42f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(0xFF8A83A8); // text_muted
        labelPaint.setTextSize(26f);
        labelPaint.setTextAlign(Paint.Align.CENTER);
    }

    // ── API pública ──────────────────────────────────────────────────
    public void setData(float[] values, int[] colors, int sessionNumber) {
        this.values        = values;
        this.colors        = colors;
        this.sessionNumber = sessionNumber;
        invalidate();
    }

    // ── Dibujo ───────────────────────────────────────────────────────
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (values == null || colors == null) return;

        float w = getWidth();
        float h = getHeight();
        float cx = w / 2f;
        float cy = h / 2f;
        float outerRadius = Math.min(cx, cy) - 4f;
        float innerRadius = outerRadius * 0.42f; // radio del hueco (donut)

        ovalRect.set(cx - outerRadius, cy - outerRadius,
                     cx + outerRadius, cy + outerRadius);

        // ── Dibujar segmentos ────────────────────────────────────────
        float startAngle = -90f; // empezar desde arriba
        float totalValue = 0;
        for (float v : values) totalValue += v;

        for (int i = 0; i < values.length; i++) {
            float sweep = (values[i] / totalValue) * 360f;
            segmentPaint.setColor(colors[i]);
            segmentPaint.setAlpha(217); // ~85% opacity
            canvas.drawArc(ovalRect, startAngle, sweep, true, segmentPaint);
            startAngle += sweep;
        }

        // ── Hueco central ────────────────────────────────────────────
        canvas.drawCircle(cx, cy, innerRadius, holePaint);

        // ── Texto central: etiqueta + número de sesión ───────────────
        float textOffset = (textPaint.descent() - textPaint.ascent()) / 4f;
        canvas.drawText("Sesión", cx, cy - textOffset, labelPaint);
        canvas.drawText("#" + sessionNumber, cx, cy + textOffset + textPaint.descent() + 4f, textPaint);
    }
}
