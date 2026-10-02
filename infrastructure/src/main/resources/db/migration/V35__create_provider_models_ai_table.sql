-- PostgreSQL: esquema según el diagrama MindConnect.

CREATE TABLE provider_models_ai (
    id UUID PRIMARY KEY,
    name_provider_ai VARCHAR(100) NOT NULL,
    razon_social VARCHAR NOT NULL,
    sitio_web TEXT NOT NULL,
    "isActive" BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- VARCHAR sin longitud: el diagrama no especifica un límite para razon_social.
-- Se conserva la grafía isActive mediante un identificador entre comillas.
