package win.huansha.moneipe.inversiones.infraestructura.adaptador.entrada.web;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import win.huansha.moneipe.inversiones.aplicacion.servicio.PortafolioServicio;
import win.huansha.moneipe.inversiones.dominio.modelo.Portafolio;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inversiones/portafolios")
@RequiredArgsConstructor
public class PortafolioController {

    private final PortafolioServicio servicio;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Portafolio> crear(@RequestHeader("X-User-Id") String userId, @RequestBody Map<String, Object> body) {
        String grupoIdRaw = body.get("grupoId") != null ? body.get("grupoId").toString() : null;
        UUID grupoId = (grupoIdRaw != null && !grupoIdRaw.isBlank()) ? UUID.fromString(grupoIdRaw) : null;
        return usuarioActual(userId).flatMap(uid ->
            servicio.crear(uid, (String) body.get("nombre"), (String) body.get("descripcion"), (String) body.get("moneda"), grupoId));
    }

    @GetMapping("/{id}")
    public Mono<Portafolio> porId(@PathVariable UUID id) {
        return servicio.porId(id);
    }

    @GetMapping
    public Flux<Portafolio> listar(@RequestHeader("X-User-Id") String userId) {
        return usuarioActual(userId).flatMapMany(servicio::listarMios);
    }

    @PutMapping("/{id}")
    public Mono<Portafolio> actualizar(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        return servicio.actualizar(id, body.get("nombre"), body.get("descripcion"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return servicio.eliminar(id);
    }

    private Mono<UUID> usuarioActual(String userId) {
        return Mono.just(UUID.fromString(userId));
    }
}
