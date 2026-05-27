# CiberLightCol

Sistema de escritorio JavaFX para gestionar produccion, lotes, tecnicos, materiales, stock minimo, auditoria en MongoDB, restauracion de registros inactivados y notificaciones por WhatsApp mediante Twilio.

## Estado Actual

- UI JavaFX con vistas FXML.
- Persistencia principal en SQLite por defecto, con soporte configurable para MySQL.
- Auditoria en MongoDB para respaldar datos antes de inactivar registros.
- Seccion `Restauracion` basada en `audit_logs` de MongoDB.
- Notificaciones WhatsApp por Twilio para:
  - Material que cae por debajo del stock minimo.
  - Inicio de lote.
  - Finalizacion de lote.

## Requisitos

- JDK 21.
- Maven disponible en PATH, o ejecucion desde IntelliJ con el `pom.xml`.
- MongoDB accesible desde la IP configurada.
- SQLite local, o MySQL si se cambia `db.type`.
- Cuenta Twilio con WhatsApp Sandbox o sender aprobado.

## Configuracion

La configuracion base esta en:

- `src/main/resources/application.properties`
- `.env` para secretos locales, ignorado por Git.
- `.env.example` como plantilla segura.

Variables principales:

```env
TWILIO_ENABLED=true
TWILIO_ACCOUNT_SID=ACxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
TWILIO_API_KEY_SID=SKxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
TWILIO_API_KEY_SECRET=secret
TWILIO_FROM_WHATSAPP=+14155238886
TWILIO_TO_WHATSAPP=+57xxxxxxxxxx
```

MongoDB:

```properties
mongo.uri=mongodb://172.30.16.238:27017/?serverSelectionTimeoutMS=3000
mongo.database=ciberlight_backup
```

## Ejecucion

Desde Maven:

```bash
mvn clean javafx:run
```

Desde IntelliJ:

1. Abrir el proyecto desde `pom.xml`.
2. Usar JDK 21.
3. Ejecutar `com.ciber.MainApp`.

## Estructura Principal

```text
src/main/java/com/ciber
  audit/          Conexion y repositorio MongoDB
  config/         Configuracion de base de datos y .env
  controller/     Controladores JavaFX
  dao/            Acceso a datos SQL
  model/          Modelos de dominio
  service/        Reglas de negocio, restauracion y notificaciones
  util/           Conexion SQL e inicializador SQLite

src/main/resources
  view/           FXML de pantallas
  style/          CSS JavaFX
  sql/            Esquemas SQL
```

## Documentacion Tecnica

El handoff completo esta en:

- `docs/TECHNICAL_HANDOFF.md`

Incluye arquitectura, modelo entidad relacion, modelo MongoDB, modelo de la API Twilio, transacciones, flujos y pendientes.
