package edu.greencharts;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;

import java.util.ArrayList;

@DesignerComponent(
        version = 1,
        description = "Extensión GreenCharts — Gráficas sin internet",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "https://i.imgur.com/BC5W7tY.png"
)
@SimpleObject(external = true)
public class GreenCharts extends AndroidNonvisibleComponent {

    private final ComponentContainer container;

    public GreenCharts(ComponentContainer container) {
        super(container.$form());
        this.container = container;
    }

    // =======================================================
    // LISTAS INTERNAS PARA CATEGORÍAS Y VALORES
    // =======================================================

    private final ArrayList<String> categories = new ArrayList<>();
    private final ArrayList<Float> values = new ArrayList<>();

    @SimpleFunction(description = "Añade un dato a la gráfica.")
    public void AddData(String category, float value) {
        categories.add(category);
        values.add(value);
    }

    @SimpleFunction(description = "Limpia todos los datos.")
    public void ClearData() {
        categories.clear();
        values.clear();
    }

    // =======================================================
    // GENERAR PNG DE LA GRÁFICA
    // =======================================================

    @SimpleFunction(description = "Genera una gráfica de barras y regresa un PNG en forma de archivo.")
    public String GenerateBarChart() {

        int width = 1000;
        int height = 600;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.BLACK);

        int n = values.size();
        if (n == 0) {
            return "ERROR: No hay datos";
        }

        float max = 0;
        for (float v : values) if (v > max) max = v;

        int barWidth = width / (n * 2);

        for (int i = 0; i < n; i++) {
            float val = values.get(i);
            float barHeight = (val / max) * 400;

            paint.setColor(Color.rgb((i * 50) % 255, (i * 80) % 255, (i * 110) % 255));
            canvas.drawRect(
                    100 + i * (barWidth * 2),
                    500 - barHeight,
                    100 + i * (barWidth * 2) + barWidth,
                    500,
                    paint
            );

            paint.setColor(Color.BLACK);
            canvas.drawText(categories.get(i), 100 + i * (barWidth * 2), 550, paint);
        }

        String path = container.$form().getCacheDir() + "/greenchart.png";
        java.io.File file = new java.io.File(path);

        try {
            java.io.FileOutputStream out = new java.io.FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return path;
        } 
        catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
