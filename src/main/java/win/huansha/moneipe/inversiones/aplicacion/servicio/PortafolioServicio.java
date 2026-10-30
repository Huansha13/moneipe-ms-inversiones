package win.huansha.moneipe.inversiones.aplicacion.servicio;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.dominio.excepcion.RecursoNoEncontradoExcepcion;
import win.huansha.moneipe.inversiones.dominio.modelo.Portafolio;
import win.huansha.moneipe.inversiones.dominio.puerto.entrada.PortafolioCasoUso;
import win.huansha.moneipe.inversiones.dominio.puerto.salida.PortafolioRepositorio;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortafolioServicio implements PortafolioCasoUso {

    private final PortafolioRepositorio repo;

    @Override
    public Mono<Portafolio> crear(UUID creadoPor, String nombre, String descripcion, String moneda, UUID grupoId) {
        return Mono.just(Portafolio.builder()
            .id(UUID.randomUUID())
            .nombre(nombre)
            .descripcion(descripcion)
            .moneda(moneda != null ? moneda : "PEN")
            .grupoId(grupoId)
            .createdBy(creadoPor)
            .updatedBy(creadoPor)
            .build())
            .flatMap(repo::guardar);
    }

    @Override
    public Mono<Portafolio> porId(UUID id) {
        return repo.porId(id)
            .switchIfEmpty(Mono.error(new RecursoNoEncontradoExcepcion("Portafolio no encontrado: " + id)));
    }

    @Override
    public Flux<Portafolio> listarMios(UUID usuarioId) {
        return repo.porCreador(usuarioId);
    }

    @Override
    public Mono<Portafolio> actualizar(UUID id, String nombre, String descripcion) {
        return repo.porId(id)
            .switchIfEmpty(Mono.error(new RecursoNoEncontradoExcepcion("Portafolio no encontrado: " + id)))
            .map(existing -> Portafolio.builder()
                .id(existing.getId())
                .nombre(nombre != null ? nombre : existing.getNombre())
                .descripcion(descripcion != null ? descripcion : existing.getDescripcion())
                .moneda(existing.getMoneda())
                .createdAt(existing.getCreatedAt())
                .updatedAt(java.time.Instant.now())
                .createdBy(existing.getCreatedBy())
                .updatedBy(existing.getUpdatedBy())
                .build())
            .flatMap(repo::guardar);
    }

    @Override
    public Mono<Void> eliminar(UUID id) {
        return repo.porId(id)
            .switchIfEmpty(Mono.error(new RecursoNoEncontradoExcepcion("Portafolio no encontrado: " + id)))
            .then(repo.eliminar(id));
    }
}
