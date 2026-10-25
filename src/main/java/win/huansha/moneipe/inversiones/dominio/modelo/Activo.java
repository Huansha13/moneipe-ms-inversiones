package win.huansha.moneipe.inversiones.dominio.modelo;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Activo {
    UUID id;
    UUID portafolioId;
    String tipo;        // ACCION, BONO, CRYPTO, FONDO, ETF
    String simbodo;     // ticker/símbodo
    String nombre;
    double cantidad;
    double precioCompra;
    LocalDate fechaCompra;
    Instant createdAt;
    Instant updatedAt;
    UUID createdBy;
    UUID updatedBy;
}
