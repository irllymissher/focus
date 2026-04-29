package com.example.focusflowsinbackend.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Custom View para el círculo animado de respiración 4-7-8 en RespiracionActivity.
 *
 * Dibuja:
 *  - Círculo de fondo (relleno semitransparente)
 *  - Círculo de borde con color que cambia según la fase
 *
 * La animación de escala (expand/contrae) se controla desde la Activity
 * mediante animateTo(targetScale, durationMs).
 *
 * Uso en XML: <com.focusflow.app.views.BreathingCircleView ... />
 */
public class BreathingCircleView extends View {

    private Paint fillPaint;
    private Paint strokePaint;

    private float currentScale = 1.0f;
    private int   strokeColor  = 0xFF7B5EEA; // purple por defecto

    // ── Constructores ────────────────────────────────────────────────
    public BreathingCircleView(Context context) {
        super(context); init();
    }
    public BreathingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs); init();
    }
    public BreathingCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle); init();
    }

    private void init() {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(0x267B5EEA); // purple 15% alpha

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(4f);
        strokePaint.setColor(strokeColor);
    }

    // ── Dibujo ───────────────────────────────────────────────────────
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cx = getWidth()  / 2f;
        float cy = getHeight() / 2f;
        float baseRadius = Math.min(cx, cy) - strokePaint.getStrokeWidth();
        float radius = baseRadius * currentScale;

        // Círculo relleno
        canvas.drawCircle(cx, cy, radius, fillPaint);
        // Borde
        canvas.drawCircle(cx, cy, radius, strokePaint);
    }

    // ── API pública ──────────────────────────────────────────────────

    /**
     * Anima el círculo hacia targetScale en durationMs milisegundos.
     * Llamar desde RespiracionActivity en cada cambio de fase.
     *
     * @param targetScale  escala destino (1.0 = normal, 1.6 = expandido)
     * @param durationMs   duración de la animación en milisegundos
     */
    public void animateTo(float targetScale, long durationMs) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(this, "circleScale", currentScale, targetScale);
        anim.setDuration(durationMs);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    /**
     * Setter para ObjectAnimator (la propiedad "circleScale").
     * No llamar directamente — usar animateTo().
     */
    public void setCircleScale(float scale) {
        this.currentScale = scale;
        invalidate();
    }

    public float getCircleScale() { return currentScale; }

    /**
     * Cambia el color de borde según la fase actual.
     * @param color color ARGB (e.g. getColor(R.color.green_primary))
     */
    public void setPhaseColor(int color) {
        strokeColor = color;
        strokePaint.setColor(color);
        // Actualizar relleno con 15% de alpha del mismo color
        int alpha = 0x26; // ~15%
        fillPaint.setColor((color & 0x00FFFFFF) | (alpha << 24));
        invalidate();
    }
}
