package win.huansha.moneipe.inversiones.dominio.puerto.salida;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.dominio.modelo.Portafolio;

import java.util.UUID;

public interface PortafolioRepositorio {
    Mono<Portafolio> guardar(Portafolio portafolio);
    Mono<Portafolio> porId(UUID id);
    Flux<Portafolio> porCreador(UUID creadorId);
    Mono<Void> eliminar(UUID id);
}
