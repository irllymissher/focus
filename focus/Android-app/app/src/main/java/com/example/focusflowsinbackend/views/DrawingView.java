package com.example.focusflowsinbackend.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Custom View para el dibujo emocional en EvaluacionActivity.
 * El usuario dibuja libremente con el dedo. Internamente guarda
 * un Path acumulado y lo redibuja en onDraw().
 *
 * Uso en XML: <com.focusflow.app.views.DrawingView ... />
 * Llama a clearCanvas() para borrar.
 */
public class DrawingView extends View {

    private Paint  strokePaint;
    private Path   currentPath;
    private float  lastX, lastY;

    // ── Constructor ──────────────────────────────────────────────────
    public DrawingView(Context context) {
        super(context); init();
    }
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs); init();
    }
    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr); init();
    }

    private void init() {
        currentPath = new Path();

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(6f);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
        // Color púrpura primario; en producción leer de R.color
        strokePaint.setColor(0xFF7B5EEA);
    }

    // ── Dibujo ───────────────────────────────────────────────────────
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(currentPath, strokePaint);
    }

    // ── Táctil ───────────────────────────────────────────────────────
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(x, y);
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // Curva de Bézier cuadrática para trazo suavizado
                float midX = (x + lastX) / 2f;
                float midY = (y + lastY) / 2f;
                currentPath.quadTo(lastX, lastY, midX, midY);
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                currentPath.lineTo(x, y);
                break;
        }

        invalidate();
        return true;
    }

    // ── API pública ──────────────────────────────────────────────────

    /** Borra todo el contenido del canvas. */
    public void clearCanvas() {
        currentPath.reset();
        invalidate();
    }

    /** Cambia el color del trazo en tiempo de ejecución. */
    public void setStrokeColor(int color) {
        strokePaint.setColor(color);
        invalidate();
    }

    /** Cambia el grosor del trazo. */
    public void setStrokeWidth(float widthDp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        strokePaint.setStrokeWidth(widthDp * density);
        invalidate();
    }
}
