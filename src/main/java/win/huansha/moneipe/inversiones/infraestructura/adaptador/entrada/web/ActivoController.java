package win.huansha.moneipe.inversiones.infraestructura.adaptador.entrada.web;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

import win.huansha.moneipe.inversiones.aplicacion.servicio.ActivoServicio;
import win.huansha.moneipe.inversiones.dominio.modelo.Activo;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inversiones/activos")
@RequiredArgsConstructor
public class ActivoController {

    private final ActivoServicio servicio;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Activo> crear(@RequestBody Map<String, String> body) {
        return Mono.fromCallable(() -> {
            UUID portafolioId = UUID.fromString(body.get("portafolioId"));
            String tipo = body.get("tipo");
            String simbodo = body.get("simbodo");
            String nombre = body.get("nombre");
            double cantidad = Double.parseDouble(body.getOrDefault("cantidad", "0"));
            double precioCompra = Double.parseDouble(body.getOrDefault("precioCompra", "0"));
            LocalDate fechaCompra = body.get("fechaCompra") != null
                ? LocalDate.parse(body.get("fechaCompra")) : null;
            return new Object[]{portafolioId, tipo, simbodo, nombre, cantidad, precioCompra, fechaCompra};
        }).flatMap(arr ->
            servicio.crear((UUID) arr[0], (String) arr[1], (String) arr[2], (String) arr[3],
                (double) arr[4], (double) arr[5], (LocalDate) arr[6]));
    }

    @GetMapping("/{id}")
    public Mono<Activo> porId(@PathVariable UUID id) {
        return servicio.porId(id);
    }

    @GetMapping("/portafolio/{portafolioId}")
    public Flux<Activo> listarPorPortafolio(@PathVariable UUID portafolioId) {
        return servicio.listarPorPortafolio(portafolioId);
    }

    @PutMapping("/{id}")
    public Mono<Activo> actualizar(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        String nombre = body.get("nombre");
        Double cantidad = body.get("cantidad") != null ? Double.parseDouble(body.get("cantidad")) : null;
        Double precioCompra = body.get("precioCompra") != null ? Double.parseDouble(body.get("precioCompra")) : null;
        return servicio.actualizar(id, nombre, cantidad, precioCompra);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable UUID id) {
        return servicio.eliminar(id);
    }
}
