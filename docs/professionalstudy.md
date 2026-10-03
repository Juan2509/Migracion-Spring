# Estudios profesionales (V13)

`professionalstudy` representa un estudio realizado por un profesional en un país.
El dominio contiene el agregado, ID, eventos y puerto de repositorio; application
contiene comandos, respuesta y cinco casos de uso. Infrastructure proporciona
API REST, validación de solicitudes, mapper, entidad JPA y adaptador del puerto.
En Java Projects los archivos están dentro de los tres módulos en src/main/java.

| Campo JSON | Tipo | Regla del esquema |
| --- | --- | --- |
| studyId | UUID | Obligatorio; debe existir en studies |
| professionalId | UUID | Obligatorio; debe existir en professionals |
| title | String | Obligatorio, máximo 100 caracteres |
| university | String | Obligatorio, máximo 100 caracteres |
| isValid | Boolean | Obligatorio; admite false |
| resolutionNumber | String | Opcional, máximo 60 caracteres |
| countryId | UUID | Obligatorio; debe existir en countries |

El ID se genera al registrar. CreatedAt y updatedAt usan LocalDateTime porque
V13 declara TIMESTAMP; el ID y createdAt se conservan al actualizar. No hay
UNIQUE: se admite repetir la combinación de estudio, profesional y país.
Las referencias utilizan IDs tipados y UUID en JPA, sin asociaciones @ManyToOne.
Las FK del SQL siguen proporcionando integridad frente a cambios concurrentes.

| Operación | Ruta | Resultado |
| --- | --- | --- |
| POST | /api/professional-studies | 201 con registro creado |
| GET | /api/professional-studies | 200 con lista |
| GET | /api/professional-studies/{id} | 200 con registro |
| PUT | /api/professional-studies/{id} | 200 con registro actualizado |
| DELETE | /api/professional-studies/{id} | 204 |

Un registro o referencia inexistente devuelve 404. La validación de la solicitud
devuelve 400; una violación de integridad de persistencia devuelve 409.
PUT reemplaza todos los campos editables, por lo que debe incluir los obligatorios.
La respuesta añade id, createdAt y updatedAt a los campos de entrada.

Para probar con Postman, crear primero un país, un estudio y un profesional
(este último requiere documento, tipo profesional y ciudad existentes). Usar sus
IDs reales en POST y PUT. Ejemplo de cuerpo, reemplazando los tres UUID:

```json
{
  "studyId": "11111111-1111-1111-1111-111111111111",
  "professionalId": "22222222-2222-2222-2222-222222222222",
  "title": "Psicología",
  "university": "Universidad de ejemplo",
  "isValid": false,
  "resolutionNumber": null,
  "countryId": "33333333-3333-3333-3333-333333333333"
}
```

Las seis pruebas nuevas cubren eventos y auditoría, validación sin mutación
parcial, CRUD, asociaciones repetidas, referencias inexistentes al registrar
y actualizar, y conversión completa JPA con resolución nula o presente.
Ejecutar desde la raíz:

```powershell
.\mvnw.cmd -o '-Dtest=*Test,!RangelApplicationTests' '-Dsurefire.failIfNoSpecifiedTests=false' test
```

Estas pruebas usan puertos en memoria y no necesitan PostgreSQL. La integración
HTTP y la ejecución real de Flyway siguen pendientes de la conexión. V13 no se
modifica para esta entrega. Los eventos permanecen en memoria, sin publicación.
