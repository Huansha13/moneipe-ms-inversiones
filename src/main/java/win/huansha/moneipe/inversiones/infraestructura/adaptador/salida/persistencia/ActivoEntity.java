package win.huansha.moneipe.inversiones.infraestructura.adaptador.salida.persistencia;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("activos")
public class ActivoEntity implements Persistable<UUID> {

    @Id
    private UUID id;
    private UUID portafolioId;
    private String tipo;
    private String simbodo;
    private String nombre;
    private double cantidad;
    private double precioCompra;
    private LocalDate fechaCompra;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    @CreatedBy
    private UUID createdBy;
    @LastModifiedBy
    private UUID updatedBy;

    @Transient
    private boolean nuevo = true;

    public ActivoEntity() {}

    @Override
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    @Override
    public boolean isNew() { return createdAt == null; }

    public UUID getPortafolioId() { return portafolioId; }
    public void setPortafolioId(UUID portafolioId) { this.portafolioId = portafolioId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getSimbodo() { return simbodo; }
    public void setSimbodo(String simbodo) { this.simbodo = simbodo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }
    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }
    public UUID getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(UUID updatedBy) { this.updatedBy = updatedBy; }
}
