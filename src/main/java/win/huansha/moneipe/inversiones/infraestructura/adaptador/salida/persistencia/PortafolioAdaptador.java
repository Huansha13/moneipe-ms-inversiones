package win.huansha.moneipe.inversiones.infraestructura.adaptador.salida.persistencia;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.dominio.modelo.Portafolio;
import win.huansha.moneipe.inversiones.dominio.puerto.salida.PortafolioRepositorio;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PortafolioAdaptador implements PortafolioRepositorio {

    private final PortafolioR2dbcRepository repo;

    @Override
    public Mono<Portafolio> guardar(Portafolio portafolio) {
        PortafolioEntity e = new PortafolioEntity();
        e.setId(portafolio.getId());
        e.setNombre(portafolio.getNombre());
        e.setDescripcion(portafolio.getDescripcion());
        e.setMoneda(portafolio.getMoneda());
        e.setCreatedBy(portafolio.getCreatedBy());
        e.setUpdatedBy(portafolio.getUpdatedBy());
        return repo.save(e).map(this::toDomain);
    }

    @Override
    public Mono<Portafolio> porId(UUID id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Portafolio> porCreador(UUID creadorId) {
        return repo.findAll()
            .filter(e -> creadorId.equals(e.getCreatedBy()))
            .map(this::toDomain);
    }

    @Override
    public Mono<Void> eliminar(UUID id) {
        return repo.deleteById(id);
    }

    private Portafolio toDomain(PortafolioEntity e) {
        return Portafolio.builder()
            .id(e.getId())
            .nombre(e.getNombre())
            .descripcion(e.getDescripcion())
            .moneda(e.getMoneda())
            .createdAt(e.getCreatedAt())
            .updatedAt(e.getUpdatedAt())
            .createdBy(e.getCreatedBy())
            .updatedBy(e.getUpdatedBy())
            .build();
    }
}
