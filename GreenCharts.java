package edu.greencharts;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.EventDispatcher;

@DesignerComponent(
        version = 1,
        description = "GreenCharts - Extensión para crear gráficas simples con categorías y valores.",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,
        iconName = "https://i.imgur.com/Z7RzV2P.png"
)
@SimpleObject(external = true)
public class GreenCharts extends AndroidNonvisibleComponent {

    private final ComponentContainer container;

    public GreenCharts(ComponentContainer container) {
        super(container.$form());
        this.container = container;
    }

    // --- Evento de prueba ---
    @SimpleEvent(description = "Se dispara cuando la extensión está lista.")
    public void ExtensionReady() {
        EventDispatcher.dispatchEvent(this, "ExtensionReady");
    }

    // --- Método simple para probar ---
    @SimpleFunction(description = "Devuelve un texto para confirmar funcionamiento.")
    public String Test() {
        return "GreenCharts está funcionando correctamente.";
    }
}
