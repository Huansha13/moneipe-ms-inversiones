package win.huansha.moneipe.inversiones.infraestructura.adaptador.salida.persistencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface PortafolioR2dbcRepository extends ReactiveCrudRepository<PortafolioEntity, UUID> {
}
