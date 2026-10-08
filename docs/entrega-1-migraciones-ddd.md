# Entrega académica 1: Actividad Spring, migraciones y DDD

Estado al **8 de octubre de 2026**: componentes principales implementados,
con las limitaciones indicadas en este documento. La evaluación frente a una
rúbrica específica requiere contrastar sus criterios; esta descripción recoge
la implementación comprobada en el repositorio.

## Alcance y ubicación

| Componente | Ubicación | Implementación |
| --- | --- | --- |
| Construcción Maven | `../pom.xml` y los POM de cada módulo | Padre agregador con `domain`, `application` e `infrastructure`; Java 25 y Spring Boot 4.1.1 |
| Dominio | `../domain/src/main/java/com/migracion/rangel/domain/` | 52 contextos funcionales, agregados, identificadores como objetos de valor, eventos y puertos de repositorio |
| Aplicación | `../application/src/main/java/com/migracion/rangel/application/` | Comandos, respuestas, excepciones y cinco casos de uso CRUD por contexto |
| Adaptadores | `../infrastructure/src/main/java/com/migracion/rangel/infrastructure/` | Controladores REST, DTO de entrada, entidades JPA, mapeadores, repositorios y configuración por contexto |
| Migraciones | `../infrastructure/src/main/resources/db/migration/` | 52 scripts SQL versionados, V1–V52 |
| Configuración | `../infrastructure/src/main/resources/application.properties` | PostgreSQL para ejecución, Flyway para crear el esquema y Hibernate en modo `validate` |
| Pruebas | `src/test/java` de los tres módulos | Dominio, casos de uso, mapeadores y arranque del contexto |

Los contextos agrupan funcionalidades como países, pacientes, profesionales,
historias clínicas y chat. La dependencia de aplicación apunta a dominio;
infraestructura depende de ambos. El código principal de dominio y aplicación
no depende de Spring ni de JPA.

La organización utiliza patrones tácticos de DDD y arquitectura hexagonal.
Los 52 paquetes funcionales no demuestran por sí solos 52 contextos delimitados
de DDD estratégico. Los eventos se registran en los agregados en memoria;
no existe publicación ni persistencia de eventos.

## Qué revisar

1. Abrir el [POM padre](../pom.xml) y comprobar los tres módulos.
2. Seguir `country` o `patient` desde dominio hasta aplicación y los adaptadores
   de infraestructura para ver el flujo completo.
3. Revisar [V1](../infrastructure/src/main/resources/db/migration/V1__create_countries_table.sql)
   y el inventario de los seis bloques de migraciones del [README](../README.md).
4. Revisar `@Valid` y las restricciones de los DTO de entrada, las validaciones
   de los agregados y las comprobaciones de referencias en los casos de uso.
5. Ejecutar desde la raíz, en PowerShell:

```powershell
.\mvnw.cmd test
```

La suite actual verifica ambas entregas conjuntamente. El 8 de octubre de 2026
pasaron **325 pruebas**: 122 de dominio, 139 de aplicación y 64 de infraestructura.
Las 64 incluyen seis pruebas de seguridad y una de arranque. Flyway aplicó
V1–V52 en H2 y Hibernate validó los mapeos. H2 tiene alcance `test` y utiliza
el perfil `test`; no requiere PostgreSQL externo para estas pruebas.

## Límites y pendientes

- PostgreSQL sigue siendo la base de ejecución. La revisión del 8 de octubre
  comprobó H2, no PostgreSQL real. El README conserva por separado el registro
  histórico de la comprobación con PostgreSQL temporal del 6 de octubre.
- `ai_models.provider_model_id` es `VARCHAR(50)` y el identificador del proveedor
  es `UUID`: la clave foránea sigue pendiente de resolver esa incompatibilidad.
- Los scripts crean estructura; no transfieren registros de otra base ni cargan
  datos iniciales.
- La cobertura HTTP no es exhaustiva para los 52 contextos. Pasar las pruebas
  no acredita todas las reglas funcionales posibles.
- Las validaciones actuales cubren nulidad, longitudes y referencias, entre
  otras comprobaciones. No debe asumirse validación de formato de correo o de
  campos vacíos donde no existen restricciones específicas.

## Relación con la entrega 2

El paquete `infrastructure/security` y sus pruebas corresponden al avance de
la [entrega 2](entrega-2-seguridad-jwt.md). La API actual requiere autenticación
incluso al demostrar funcionalidades de esta primera entrega. Para arrancar
con PostgreSQL se requieren las variables de base de datos y las credenciales
descritas en la [guía de seguridad](seguridad.md).
