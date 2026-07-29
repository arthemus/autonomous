# AGENTS.md

Shared agent instructions for AI coding assistants (Droid, Claude, Gemini, Devin, etc.) working on the
**Autonomous** repository. Pointers for tool-specific configs: `CLAUDE.md`, `GEMINI.md`, and
`build/AGENTS.md` all redirect here.

## Repository Layout

```
autonomous/
  build/      Aggregator parent POM (dependency + plugin management)
  functions/  Utility library (null-safety, dates, currency, BR docs, CEP, crypto, e-mail, JSON, i18n)
  ioc/        Guice dependency-injection bootstrap
  tenaz/      Hibernate persistence (session lifecycle, CRUD, search, JDBC descriptors)
  printer/    JasperReports PDF rendering
  web/        JSF/servlet helpers (FacesContext facade, converters, upload, request/response)
```

## Build and Test Commands

```bash
# Full reactor build + install to local cache
mvn -f build/pom.xml clean install

# Run all tests
mvn -f build/pom.xml test

# Build a single module (with dependencies)
mvn -f build/pom.xml -pl ../functions clean install

# Resume from a failed module
mvn -f build/pom.xml clean install -rf :autonomous.functions
```

## Hard Constraints

1. **Java 8 only.** No Java 9+ APIs (var, records, text blocks, Stream.toList(), etc.).
2. **UTF-8 everywhere.** Source files, resource bundles, and POM encoding are UTF-8.
3. **English-only identifiers, comments, JavaDoc, string literals, log messages, and exception messages.**
   Brazilian legal/domain terms (CPF, CNPJ, UF, CEP) may remain as-is with an English JavaDoc explanation.
4. **No `printStackTrace()`.** Use SLF4J or rethrow.
5. **No empty catch blocks.** Log or rethrow; never swallow.
6. **No static mutable state.** Inject collaborators via constructor.
7. **No network, filesystem, or clock dependence in unit tests.** Inject seams (Mockito mocks or fakes).
8. **No credentials in source files or resources.** Externalize to environment or config.

## Code Conventions

- Package root: `org.autonomous` (`org.autonomous.functions`, `org.autonomous.tenaz`, etc.).
- Test stack: JUnit 5 (Jupiter) + Mockito 4 + AssertJ 3. Legacy JUnit 4 tests run via the vintage engine
  but must be migrated when touched.
- Every test method uses the AAA pattern with literal `// Arrange`, `// Act`, `// Assert` comments.
- One behavior per test method. `@DisplayName` in English.
- Assertions use AssertJ `assertThat(...)`, never JUnit `assertEquals`.
- Mocks via `@ExtendWith(MockitoExtension.class)` + `@Mock` / `@InjectMocks`.
- Dependencies are declared in the parent POM where shared; module POMs declare module-specific deps.

## Mandatory Skills

When writing or modifying tests, apply the **unit-testing-aaa** skill (`.factory/skills/unit-testing-aaa/SKILL.md`).

When translating Portuguese content to English, apply the **pt-en-translation** skill
(`.factory/skills/pt-en-translation/SKILL.md`).

## Never Do

- Do not commit `*.iml`, `target/`, `.idea/`, or any IDE-specific files.
- Do not add new dependencies without checking they resolve from Maven Central over HTTPS.
- Do not introduce `javax.faces.bean.*` managed beans (deprecated since JSF 2.3); use CDI where new JSF
  code is unavoidable.
- Do not upgrade Hibernate beyond 3.6.10.Final without a full `HibernatePersist` refactor plan.
- Do not rename packages without updating all import statements and moving files to matching directories.
