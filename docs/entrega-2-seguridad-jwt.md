# Entrega académica 2: Implementación del módulo de seguridad JWT con Spring

Estado al **8 de octubre de 2026**: **parcial**. Existe integración de Spring
Security con HTTP Basic y CSRF. La autenticación mediante JWT no está
implementada; esta entrega no debe presentarse como terminada.

## Implementación disponible

| Componente | Evidencia |
| --- | --- |
| Dependencias | [POM de infraestructura](../infrastructure/pom.xml): `spring-boot-starter-security` y `spring-security-test`, con versiones administradas por Spring Boot |
| Cadena de filtros | [SecurityConfig](../infrastructure/src/main/java/com/migracion/rangel/infrastructure/security/config/SecurityConfig.java): autenticación obligatoria, HTTP Basic, política `STATELESS` y CSRF activo |
| Credenciales | [application.properties](../infrastructure/src/main/resources/application.properties): `SECURITY_USERNAME` y `SECURITY_PASSWORD`, cuenta técnica en memoria |
| Token CSRF | [SecurityController](../infrastructure/src/main/java/com/migracion/rangel/infrastructure/security/adapters/in/rest/SecurityController.java): `GET /api/security/csrf` autenticado |
| Verificación | [SecurityIntegrationTest](../infrastructure/src/test/java/com/migracion/rangel/infrastructure/security/SecurityIntegrationTest.java): seis pruebas de integración |

La seguridad se ubica en infraestructura para conservar dominio y aplicación
independientes de Spring Security. No hay un cuarto módulo Maven de seguridad.
La política `STATELESS` no implica autenticación JWT: actualmente cada petición
debe aportar credenciales Basic. El token CSRF protege escrituras y no sirve
como token de autenticación. La sesión conserva el token CSRF.

La [guía de seguridad HTTP](seguridad.md) contiene las instrucciones de
configuración y el flujo que funciona actualmente.

## Requisito JWT pendiente

| Elemento necesario para el flujo JWT | Estado |
| --- | --- |
| Inicio de sesión que autentique credenciales y devuelva un JWT | Pendiente |
| Emisión y firma del token | Pendiente |
| Configuración de claves, algoritmo y vencimiento | Pendiente |
| Validación de firma y vencimiento en cada solicitud protegida | Pendiente |
| Autenticación con `Authorization: Bearer <token>` | Pendiente |
| Pruebas de token válido, vencido, manipulado y ausente | Pendiente |

Tampoco hay usuarios persistidos ni autorización por roles de negocio.
Su necesidad debe contrastarse con la rúbrica: no son requisitos universales
de JWT. De igual forma, registro de usuarios, refresh tokens y revocación
requieren definir alcance antes de considerarlos obligatorios.

## Evidencias de pruebas actuales

Las seis pruebas verifican:

1. Rechazo de lecturas anónimas con 401.
2. Rechazo de contraseñas incorrectas con 401.
3. Lecturas autenticadas que llegan a la base de datos.
4. Rechazo de escrituras sin token CSRF con 403.
5. Validación de solicitudes autenticadas inválidas con 400.
6. Obtención de token CSRF y creación autenticada de un país con 201.

El 8 de octubre de 2026 estas pruebas pasaron dentro de una suite de
**325 pruebas sin fallos ni errores**, usando H2. Ninguna prueba actual demuestra
emisión o validación JWT.

Para ejecutar toda la suite desde la raíz en PowerShell:

```powershell
.\mvnw.cmd test
```

## Diferencia respecto a la entrega 1

Las migraciones V1–V52, los modelos del negocio, los casos de uso CRUD y la
persistencia pertenecen a la [entrega 1](entrega-1-migraciones-ddd.md).
La seguridad controla el acceso a esa aplicación existente. Compartir
infraestructura y pruebas no convierte las migraciones en parte del requisito
JWT ni permite acreditar JWT con las pruebas de HTTP Basic.
