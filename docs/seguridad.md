# Seguridad HTTP y pruebas H2

Esta guía describe la configuración técnica disponible actualmente. Forma
parte del avance de la [entrega académica 2](entrega-2-seguridad-jwt.md), cuyo
requisito JWT sigue pendiente. HTTP Basic y el token CSRF no son JWT.
H2 también permite verificar las migraciones de la
[entrega académica 1](entrega-1-migraciones-ddd.md).

La seguridad reside en `infrastructure/security`: `config` contiene la cadena
de filtros y `adapters/in/rest` expone el token CSRF. Los modulos `domain` y
`application` permanecen independientes de Spring Security.

Se requiere autenticacion HTTP Basic en todas las rutas. Definir
`SECURITY_USERNAME` y `SECURITY_PASSWORD` ademas de las variables de PostgreSQL
antes de arrancar. Usar HTTPS al desplegar. Esta integracion proporciona una
cuenta tecnica configurada por entorno; no implementa registro de usuarios,
roles de negocio ni JWT. Spring Boot administra la cuenta en memoria; la clave
configurada se recibe desde el entorno y debe tratarse como un secreto.

CSRF permanece activo porque los navegadores pueden reenviar credenciales
Basic automaticamente. Para POST, PUT y DELETE, obtener primero
`GET /api/security/csrf` autenticado, conservar la cookie de sesion y enviar
el valor `token` en el encabezado indicado por `headerName`. La sesion se usa
para CSRF; la autenticacion Basic se debe enviar en cada solicitud.

Ejemplo PowerShell (credenciales obtenidas del entorno):

```powershell
$credential = [pscredential]::new($env:SECURITY_USERNAME, (ConvertTo-SecureString $env:SECURITY_PASSWORD -AsPlainText -Force))
$csrf = Invoke-RestMethod http://localhost:8080/api/security/csrf -Authentication Basic -Credential $credential -AllowUnencryptedAuthentication -SessionVariable apiSession
$headers = @{}
$headers[$csrf.headerName] = $csrf.token
# Usar $headers, -WebSession $apiSession y las mismas credenciales en la escritura.
```

`-AllowUnencryptedAuthentication` se limita al ejemplo local. En despliegue,
usar una URL HTTPS.

H2 tiene alcance `test` y no se incluye en el JAR ejecutable. El perfil `test`
usa una base en memoria en modo PostgreSQL, ejecuta Flyway V1-V52 y mantiene
la validacion de Hibernate. Un dominio de compatibilidad adapta TIMESTAMPTZ
sin modificar las migraciones originales. La consola H2 permanece desactivada.
Estas pruebas verifican H2; no sustituyen las comprobaciones contra PostgreSQL,
especialmente para JSONB, restricciones y semantica de tipos.

Ejecutar desde la raiz: `.\mvnw.cmd test`. Las pruebas de seguridad verifican
rechazo de anonimos y claves incorrectas, lecturas autenticadas, proteccion
CSRF y Bean Validation con solicitudes autenticadas.

El POM raiz es un agregador Maven con tres modulos y packaging `pom`.
Las versiones de las nuevas dependencias se heredan de Spring Boot 4.1.1.
No hay modulos JPMS (`module-info.java`).
