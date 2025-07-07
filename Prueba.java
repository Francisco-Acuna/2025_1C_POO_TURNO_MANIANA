package ar.org.centro8.curso.java.repositories;

public class Prueba {
    /*
     *  Campo SQL DATE en la base

        Opciones Java:
        - java.sql.Date (del tipo sql)
        - java.util.Date (viejo, no recomendable en la actualidad)
        - java.time.LocalDate (recomendado desde Java 8)

        LocalDate representa una fecha sin hora y sin zona horaria, es decir: solo día, mes y año.
        Es inmutable: Una vez que se crea un objeto LocalDate, no se puede modificar. Si se realizan operaciones 
        (como añadir días), se devuelve un nuevo objeto LocalDate con el resultado. Esto lo hace seguro para hilos 
        y previene efectos secundarios inesperados.
        Tiene en cuenta la diferencia entre fecha calendario y fecha-hora: A diferencia de java.util.Date que tenía 
        ambos conceptos mezclados y era propensa a errores.

        Ejemplo de uso:

        import java.time.LocalDate;

        LocalDate hoy = LocalDate.now(); // Ejemplo: 2025-06-25 (formato ISO-8601)
        System.out.println("Hoy: " + hoy);

        Fecha específica (año, mes, día):
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15); // Año, mes (int), día
        System.out.println("Fecha de Nacimiento: " + fechaNacimiento);

        // O usando el enum Month para mayor claridad
        import java.time.Month;
        LocalDate fechaEvento = LocalDate.of(2024, Month.DECEMBER, 25);
        System.out.println("Fecha de Evento: " + fechaEvento);

        Parseo
        Desde un String. Por defecto, parse() espera el formato ISO-8601 (AAAA-MM-DD).
        LocalDate fechaDesdeString = LocalDate.parse("2023-01-31");
        System.out.println("Fecha desde String: " + fechaDesdeString);

        // Para otros formatos, hace falta un DateTimeFormatter
        import java.time.format.DateTimeFormatter;
        LocalDate otraFecha = LocalDate.parse("15/08/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("Otra Fecha: " + otraFecha);

        Lectura con JDBC:
        Opción clásica
        JDBC devuelve un objeto java.sql.Date (subtipo de java.util.Date) que representa la fecha tal cual viene de la base
        java.sql.Date sqlDate = resultSet.getDate("fecha_nacimiento"); // sqlDate es de tipo JDBC java.sql.Date
        Para usar la API moderna de Java 8+, hay que convertir a java.time.LocalDate:
        LocalDate ld1 = sqlDate.toLocalDate();

        // Opción moderna (JDBC 4.2+)
        LocalDate ld2 = resultSet.getObject("fecha_nacimiento", LocalDate.class);

	Escritura con JDBC:
	// Opción clásica
	LocalDate fecha = alumno.getFechaNacimiento();
	preparedStatement.setDate(1, java.sql.Date.valueOf(fecha));
	siempre que nos aseguremos de que la fecha no sea nula, si no:
	if (fecha != null) {
    		preparedStatement.setDate(1, java.sql.Date.valueOf(fecha));
	} else {
    		preparedStatement.setNull(1, Types.DATE);
	}

	// Opción moderna (JDBC 4.2+)
	setObject maneja null automáticamente si el valor es null
	preparedStatement.setObject(1, fecha); o preparedStatement.setObject(1, alumno.getFechaNacimiento());
	El driver intenta inferir el tipo SQL automáticamente.
	No es obligatorio pasar el Types
	preparedStatement.setObject(1, fecha, Types.DATE);


        Campo SQL TIME

        Opciones Java:
        - java.sql.Time
	- java.util.Date (no utilizar!)
        - java.time.LocalTime (recomendado desde Java 8)

	LocalTime representa una hora del día sin ninguna información de fecha o zona horaria. Es decir, 
	solo almacena la hora, minuto, segundo y, opcionalmente, nanosegundos.
	Inmutabilidad: Al igual que otras clases de la API java.time (como LocalDate y LocalDateTime), LocalTime es inmutable. 
	Esto significa que una vez que se crea una instancia de LocalTime, no se pueden cambiar sus valores. Cualquier operación 
	que modifique una LocalTime (como sumarle horas) en realidad devuelve una nueva instancia de LocalTime con los valores 
	actualizados, dejando la instancia original intacta. 
	Creación:
	Tiempo actual: Se puede obtener el tiempo actual del sistema utilizando LocalTime.now().
	Tiempo específico: Se puede crear una instancia de LocalTime especificando la hora, minuto, segundo 
	y nanosegundos utilizando los métodos estáticos of():
	LocalTime horaActual = LocalTime.now();
	LocalTime horaEspecifica = LocalTime.of(15, 30); // 15:30
	LocalTime horaConSegundos = LocalTime.of(9, 45, 10); // 09:45:10
	LocalTime horaConNanos = LocalTime.of(22, 15, 0, 123456789); // 22:15:00.123456789

	Parseo
	parsear una cadena a LocalTime si la cadena sigue un formato válido (por defecto, ISO 8601, como "10:15:30"):
	LocalTime horaParseada = LocalTime.parse("14:30:00");
	Para otros formatos, se puede usar DateTimeFormatter.
	Acceso a componentes: Se puedens obtener los componentes individuales de la hora (hora, minuto, segundo, nanosegundo) usando métodos get:
	int hora = horaEspecifica.getHour(); // 15
	int minuto = horaEspecifica.getMinute(); // 30

        Lectura con JDBC:
        // Clásico
        java.sql.Time sqlTime = resultSet.getTime("hora_evento");
        LocalTime lt1 = sqlTime.toLocalTime();

        // Moderno
        LocalTime lt2 = resultSet.getObject("hora_evento", LocalTime.class);

        Escritura con JDBC:
        LocalTime lt = evento.getHoraEvento();
        preparedStatement.setTime(1, java.sql.Time.valueOf(lt));

        // Moderno
        preparedStatement.setObject(1, lt);

	Campos de fecha y hora
	En SQL:
	DATETIME: Guarda la fecha y la hora exactamente como se le pase. 
	No importa la zona horaria. Si se pone 10:00 AM, siempre será 10:00 AM.
	Conviene utilizarla para eventos locales que suceden a una hora fija en un lugar 
	(ej., una cita a las 3 PM, sin importar dónde esté el servidor o el usuario). O
	para almacenar fechas muy, muy antiguas o muy futuras.
	TIMESTAMP: Guarda la fecha y la hora convirtiéndola a UTC (Tiempo Universal Coordinado) 
	para el almacenamiento. Cuando se lee, la base de datos la convierte de nuevo a la zona horaria local.
	Conviene utilizarla para registrar un momento exacto en el tiempo que debe ser el mismo en todo el mundo 
	(ej., cuándo se hizo una compra online, cuándo se creó un registro).
	Es ideal para aplicaciones globales donde usuarios de diferentes países deben ver la hora correcta según su ubicación.
	Limitación: Solo funciona para fechas entre 1970 y 2038.

	En Java:
	java.time.LocalDateTime: Para DATETIME en SQL.
	java.time.Instant o java.time.ZonedDateTime: Para TIMESTAMP en SQL (cuando la zona horaria es crucial).

	//Escritura con JDBC
	Para columnas DATETIME en MySQL (sin zona horaria) -> En Java usar java.time.LocalDateTime.
	preparedStatement.setDatetime(index, Datetime.valueOf(localDateTime))
	preparedStatement.setObject(index, localDateTime)

	//Lectura con JDBC
	getTimestamp().toLocalDateTime() o getObject(columnName, LocalDateTime.class)

	Para columnas TIMESTAMP en MySQL. Lo ideal es usar java.time.Instant o java.time.ZonedDateTime
	Si envías un java.time.Instant (PreparedStatement.setObject(index, instant) o setTimestamp(index, Timestamp.from(instant))), 
	el Instant representa un momento en UTC. El driver JDBC (en conjunto con MySQL) tomará este Instant (UTC) y lo convertirá a 
	la zona horaria de la conexión/sesión de MySQL antes de que MySQL lo convierta internamente a UTC para el almacenamiento. 
	Es un poco redundante pero así funciona.
	Si envías un java.time.LocalDateTime (PreparedStatement.setObject(index, localDateTime) o 
	setTimestamp(index, Timestamp.valueOf(localDateTime))), el LocalDateTime no tiene zona horaria. 
	El driver JDBC y MySQL asumirán la zona horaria de la conexión/sesión (o del sistema JVM si no se especifica) 
	para interpretar ese LocalDateTime y convertirlo a UTC para el almacenamiento en la columna TIMESTAMP.
	Recepción de DB:
	Si lees un TIMESTAMP de MySQL como java.time.Instant (ResultSet.getObject(columnName, Instant.class) o getTimestamp().toInstant()), 
	el driver lo leerá, aplicará la conversión de la zona horaria de la conexión/sesión (o del sistema) para llevarlo a UTC, y 
	te devolverá el Instant (que siempre es UTC).
	Si lees un TIMESTAMP de MySQL como java.time.LocalDateTime (ResultSet.getObject(columnName, LocalDateTime.class) 
	o getTimestamp().toLocalDateTime()), el driver lo leerá, aplicará la conversión de la zona horaria de la 
	conexión/sesión (o del sistema) y te devolverá un LocalDateTime que representa la fecha y hora en esa zona horaria.
	Cuando se trabaja con TIMESTAMP de MySQL, el driver JDBC SIEMPRE interactúa con la zona horaria de la conexión/sesión de MySQL.
	Si no configuras la zona horaria de la conexión JDBC explícitamente, MySQL Connector/J usará por defecto la zona horaria del 
	sistema de la JVM donde se ejecuta tu aplicación Java.
	Esto puede generar problemas si tu aplicación Java y tu servidor MySQL no están en la misma zona horaria, o si tu aplicación 
	está en una zona horaria diferente a la que el usuario final espera ver.

	Campo SQL ENUM y Enumerados en Java con Atributos

Supongamos una columna SQL:

enum('ALTO','MEDIO','BAJO') AS nivel

Java:

public enum Nivel {
    BAJO(1), MEDIO(2), ALTO(3);
    private final int prioridad;
    Nivel(int prioridad) { this.prioridad = prioridad; }
    public int getPrioridad() { return prioridad; }
    // mapeo inverso
    private static final Map<String, Nivel> BY_NAME = Map.of(
        "BAJO", BAJO, "MEDIO", MEDIO, "ALTO", ALTO
    );
    public static Nivel from(String name) {
        return BY_NAME.get(name);
    }
}

Lectura con JDBC:

String name = resultSet.getString("nivel");
Nivel nivel = name != null ? Nivel.from(name) : null;

Escritura con JDBC:
// usando setString
preparedStatement.setString(1, nivel.name());

// o setObject
preparedStatement.setObject(1, nivel.name());

Valores Nulos

Escenarios: columnas que admiten NULL (fecha, número, texto, enum, etc.)

Opciones de escritura:

Integer edad = null;
if (edad != null) {
    ps.setInt(2, edad);
} else {
    ps.setNull(2, Types.INTEGER);
}
// O con setObject
ps.setObject(2, edad, Types.INTEGER);

Lectura de nulos:

int x = rs.getInt("columna");
if (rs.wasNull()) {
    x = null; // usar envoltorio Integer
}
// O con getObject
Integer y = rs.getObject("columna", Integer.class);

Recomendación:

Para escritura: setObject(paramIndex, valor, Types.X) simplifica la lógica.

Para lectura: getObject(colName, Tipo.class) devuelve null directamente.

Evitar rs.getXxx() seguido de wasNull(), salvo que sea estrictamente necesario.
	

     */
}
