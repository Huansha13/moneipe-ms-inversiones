package win.huansha.moneipe.inversiones.dominio.puerto.entrada;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.dominio.modelo.Activo;

import java.util.UUID;

public interface ActivoCasoUso {
    Mono<Activo> crear(UUID portafolioId, String tipo, String simbodo, String nombre,
                       double cantidad, double precioCompra, java.time.LocalDate fechaCompra);
    Mono<Activo> porId(UUID id);
    Flux<Activo> listarPorPortafolio(UUID portafolioId);
    Mono<Activo> actualizar(UUID id, String nombre, Double cantidad, Double precioCompra);
    Mono<Void> eliminar(UUID id);
}
