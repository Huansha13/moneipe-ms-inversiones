package win.huansha.moneipe.inversiones.dominio.servicio;

import win.huansha.moneipe.inversiones.dominio.modelo.Activo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class RentabilidadCalculator {

    public static double rentabilidadActivo(double precioCompra, double precioActual) {
        if (precioCompra == 0) return 0;
        return ((precioActual - precioCompra) / precioCompra) * 100;
    }

    public static double valorTotal(List<Activo> activos, ToDoubleFunction<Activo> precioActual) {
        return activos.stream()
            .mapToDouble(a -> a.getCantidad() * precioActual.applyAsDouble(a))
            .sum();
    }

    public static double totalInvertido(List<Activo> activos) {
        return activos.stream()
            .mapToDouble(a -> a.getCantidad() * a.getPrecioCompra())
            .sum();
    }

    public static double rentabilidadPortafolio(List<Activo> activos, ToDoubleFunction<Activo> precioActual) {
        double invertido = totalInvertido(activos);
        if (invertido == 0) return 0;
        double actual = valorTotal(activos, precioActual);
        return ((actual - invertido) / invertido) * 100;
    }

    public static double redondear(double valor, int decimales) {
        return BigDecimal.valueOf(valor)
            .setScale(decimales, RoundingMode.HALF_UP)
            .doubleValue();
    }
}
