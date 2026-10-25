package win.huansha.moneipe.inversiones.dominio.modelo;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Transaccion {
    UUID id;
    UUID activoId;
    String tipo;        // COMPRA, VENTA, DIVIDENDO
    double cantidad;
    double precio;
    LocalDate fecha;
    Instant createdAt;
    UUID createdBy;
}
