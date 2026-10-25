package win.huansha.moneipe.inversiones.infraestructura.adaptador.salida.persistencia;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.dominio.modelo.Activo;
import win.huansha.moneipe.inversiones.dominio.puerto.salida.ActivoRepositorio;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivoAdaptador implements ActivoRepositorio {

    private final ActivoR2dbcRepository repo;

    @Override
    public Mono<Activo> guardar(Activo activo) {
        ActivoEntity e = new ActivoEntity();
        e.setId(activo.getId());
        e.setPortafolioId(activo.getPortafolioId());
        e.setTipo(activo.getTipo());
        e.setSimbodo(activo.getSimbodo());
        e.setNombre(activo.getNombre());
        e.setCantidad(activo.getCantidad());
        e.setPrecioCompra(activo.getPrecioCompra());
        e.setFechaCompra(activo.getFechaCompra());
        e.setCreatedBy(activo.getCreatedBy());
        e.setUpdatedBy(activo.getUpdatedBy());
        return repo.save(e).map(this::toDomain);
    }

    @Override
    public Mono<Activo> porId(UUID id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Activo> porPortafolio(UUID portafolioId) {
        return repo.findAll()
            .filter(e -> portafolioId.equals(e.getPortafolioId()))
            .map(this::toDomain);
    }

    @Override
    public Mono<Void> eliminar(UUID id) {
        return repo.deleteById(id);
    }

    private Activo toDomain(ActivoEntity e) {
        return Activo.builder()
            .id(e.getId())
            .portafolioId(e.getPortafolioId())
            .tipo(e.getTipo())
            .simbodo(e.getSimbodo())
            .nombre(e.getNombre())
            .cantidad(e.getCantidad())
            .precioCompra(e.getPrecioCompra())
            .fechaCompra(e.getFechaCompra())
            .createdAt(e.getCreatedAt())
            .updatedAt(e.getUpdatedAt())
            .createdBy(e.getCreatedBy())
            .updatedBy(e.getUpdatedBy())
            .build();
    }
}
