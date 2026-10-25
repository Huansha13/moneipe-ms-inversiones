package win.huansha.moneipe.inversiones.dominio.puerto.entrada;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.dominio.modelo.Portafolio;

import java.util.UUID;

public interface PortafolioCasoUso {
    Mono<Portafolio> crear(UUID creadoPor, String nombre, String descripcion, String moneda);
    Mono<Portafolio> porId(UUID id);
    Flux<Portafolio> listarMios(UUID usuarioId);
    Mono<Portafolio> actualizar(UUID id, String nombre, String descripcion);
    Mono<Void> eliminar(UUID id);
}
