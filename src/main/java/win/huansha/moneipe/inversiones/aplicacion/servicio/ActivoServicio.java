package win.huansha.moneipe.inversiones.aplicacion.servicio;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.dominio.excepcion.RecursoNoEncontradoExcepcion;
import win.huansha.moneipe.inversiones.dominio.modelo.Activo;
import win.huansha.moneipe.inversiones.dominio.puerto.entrada.ActivoCasoUso;
import win.huansha.moneipe.inversiones.dominio.puerto.salida.ActivoRepositorio;
import win.huansha.moneipe.inversiones.dominio.puerto.salida.PortafolioRepositorio;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivoServicio implements ActivoCasoUso {

    private final ActivoRepositorio repo;
    private final PortafolioRepositorio portafolioRepo;

    @Override
    public Mono<Activo> crear(UUID portafolioId, String tipo, String simbodo, String nombre,
                              double cantidad, double precioCompra, LocalDate fechaCompra) {
        return portafolioRepo.porId(portafolioId)
            .switchIfEmpty(Mono.error(new RecursoNoEncontradoExcepcion("Portafolio no encontrado: " + portafolioId)))
            .then(Mono.fromCallable(() -> Activo.builder()
                .id(UUID.randomUUID())
                .portafolioId(portafolioId)
                .tipo(tipo)
                .simbodo(simbodo)
                .nombre(nombre)
                .cantidad(cantidad)
                .precioCompra(precioCompra)
                .fechaCompra(fechaCompra)
                .build()))
            .flatMap(repo::guardar);
    }

    @Override
    public Mono<Activo> porId(UUID id) {
        return repo.porId(id)
            .switchIfEmpty(Mono.error(new RecursoNoEncontradoExcepcion("Activo no encontrado: " + id)));
    }

    @Override
    public Flux<Activo> listarPorPortafolio(UUID portafolioId) {
        return repo.porPortafolio(portafolioId);
    }

    @Override
    public Mono<Activo> actualizar(UUID id, String nombre, Double cantidad, Double precioCompra) {
        return repo.porId(id)
            .switchIfEmpty(Mono.error(new RecursoNoEncontradoExcepcion("Activo no encontrado: " + id)))
            .map(existing -> Activo.builder()
                .id(existing.getId())
                .portafolioId(existing.getPortafolioId())
                .tipo(existing.getTipo())
                .simbodo(existing.getSimbodo())
                .nombre(nombre != null ? nombre : existing.getNombre())
                .cantidad(cantidad != null ? cantidad : existing.getCantidad())
                .precioCompra(precioCompra != null ? precioCompra : existing.getPrecioCompra())
                .fechaCompra(existing.getFechaCompra())
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
            .switchIfEmpty(Mono.error(new RecursoNoEncontradoExcepcion("Activo no encontrado: " + id)))
            .then(repo.eliminar(id));
    }
}
