# Autonomous

Java 8 utility aggregator for freelance and enterprise projects: null-safety helpers, date/time math,
Brazilian document validation (CPF, CNPJ, State Registration), zip-code lookup, crypto/hash utilities,
e-mail sending, JSON parsing, Hibernate persistence, Guice dependency injection, JSF/servlet web helpers,
and JasperReports PDF rendering.

## Prerequisites

- **JDK 8** (1.8.x)
- **Maven 3.6+** (tested with 3.9.x)

## Build

The aggregator POM lives in `build/`. From the repository root:

```bash
mvn -f build/pom.xml clean install
```

To run only the tests:

```bash
mvn -f build/pom.xml test
```

## Modules

| Module | Artifact | Responsibility |
|---|---|---|
| `functions` | `autonomous.functions` | Utilities: null-safety, dates/times, currency, BR document validation (CPF/CNPJ/IE), CEP lookup, crypto/hash, e-mail, JSON, i18n |
| `ioc` | `autonomous.ioc` | Guice bootstrap: servlet listener + injector adapter |
| `tenaz` | `autonomous.tenaz` | Hibernate persistence: session lifecycle, generic CRUD, named/SQL search, multi-vendor JDBC descriptors |
| `printer` | `autonomous.printer` | JasperReports rendering to PDF over the servlet/JSF response |
| `web` | `autonomous.web` | JSF/servlet helpers: `FacesContext` facade, converters, file upload, request/response wrappers |
| `build` | `autonomous.build` | Aggregator parent POM (dependency + plugin management) |

## Test Stack

- JUnit 5.8.2 (Jupiter) with JUnit Vintage engine for legacy JUnit 4 tests
- Mockito 4.11.0
- AssertJ 3.24.2
- Maven Surefire 3.0.0-M9

## License

Proprietary. All rights reserved.
