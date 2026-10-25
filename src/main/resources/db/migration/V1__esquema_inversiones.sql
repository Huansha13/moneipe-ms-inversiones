CREATE TABLE portafolios (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre      VARCHAR(100) NOT NULL,
    descripcion TEXT,
    moneda      VARCHAR(10)  NOT NULL DEFAULT 'PEN',
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    created_by  UUID,
    updated_by  UUID
);

CREATE TABLE activos (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    portafolio_id UUID NOT NULL REFERENCES portafolios (id),
    tipo          VARCHAR(20) NOT NULL,   -- ACCION, BONO, CRYPTO, FONDO, ETF
    simbodo       VARCHAR(20) NOT NULL,   -- ticker/símbodo
    nombre        VARCHAR(150) NOT NULL,
    cantidad      DECIMAL(18,8) NOT NULL DEFAULT 0,
    precio_compra DECIMAL(18,8) NOT NULL DEFAULT 0,
    fecha_compra  DATE,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    created_by    UUID,
    updated_by    UUID
);

CREATE TABLE transacciones (
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    activo_id UUID NOT NULL REFERENCES activos (id),
    tipo      VARCHAR(20) NOT NULL,   -- COMPRA, VENTA, DIVIDENDO
    cantidad  DECIMAL(18,8) NOT NULL,
    precio    DECIMAL(18,8) NOT NULL,
    fecha     DATE NOT NULL,
    created_at TIMESTAMP,
    created_by UUID
);

CREATE INDEX idx_activos_portafolio ON activos (portafolio_id);
CREATE INDEX idx_transacciones_activo ON transacciones (activo_id);
CREATE INDEX idx_transacciones_fecha ON transacciones (fecha);
