package win.huansha.moneipe.inversiones.dominio.modelo;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Portafolio {
    UUID id;
    String nombre;
    String descripcion;
    String moneda;
    UUID grupoId;
    Instant createdAt;
    Instant updatedAt;
    UUID createdBy;
    UUID updatedBy;
}
