package win.huansha.moneipe.inversiones.dominio.puerto.salida;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.dominio.modelo.Activo;

import java.util.UUID;

public interface ActivoRepositorio {
    Mono<Activo> guardar(Activo activo);
    Mono<Activo> porId(UUID id);
    Flux<Activo> porPortafolio(UUID portafolioId);
    Mono<Void> eliminar(UUID id);
}
