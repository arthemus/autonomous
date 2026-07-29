# Skill: unit-testing-aaa

Write and maintain unit tests using the Arrange-Act-Assert pattern with JUnit 5, Mockito, and AssertJ.

## When to Apply

- Writing any new unit test.
- Modifying an existing test.
- Migrating a JUnit 4 test to JUnit 5.
- Adding test coverage for a class that has no tests.

## Rules

### Framework

- **JUnit 5 (Jupiter)** only. Use `org.junit.jupiter.api.Test`, not `org.junit.Test`.
- **Mockito 4** for test doubles. Use `@ExtendWith(MockitoExtension.class)` + `@Mock` + `@InjectMocks`.
- **AssertJ 3** for assertions. Use `assertThat(...)`, never `assertEquals` or `assertTrue` from JUnit.
- Java 8 compatible. No `var`, no records, no text blocks.

### Structure (AAA)

Every test method must have exactly three sections, each preceded by a literal comment:

```java
@Test
@DisplayName("should return default zero when input string is null")
void shouldReturnDefaultZeroWhenInputStringIsNull() {
    // Arrange
    String input = null;

    // Act
    Integer result = NullSafe.intNotNull(input);

    // Assert
    assertThat(result).isEqualTo(0);
}
```

- One behavior per test method. Do not combine multiple assertions for different behaviors.
- Test method name: `should<ExpectedBehavior>When<Condition>` (camelCase, English).
- `@DisplayName` is mandatory and written in English.

### Isolation

- No network calls (HTTP, sockets, DNS).
- No filesystem access (`new File(...)`, `Files.readAllLines`, etc.).
- No real clock dependence (`System.currentTimeMillis()`, `LocalDate.now()`). Inject a clock or pass
  the value as a parameter.
- No database connections. Mock `SessionFactory`, `Session`, `Connection`, etc.
- No static initializer side effects. If the class under test has static state, refactor it first
  (inject a seam) rather than testing around it.

### Mocking

- Mock interfaces or concrete collaborator classes, never the class under test.
- Use `when(mock.collaborator(any())).thenReturn(value)` for stubbing.
- Use `verify(mock, times(1)).collaborator(arg)` for interaction assertions.
- Prefer `assertThat` over `verify` when the return value is observable.
- Use `@Captor` for argument capture when needed.

### Coverage Targets

- **Tier 1 (pure logic):** No mocks needed. Test all branches, edge cases, null inputs, boundary values.
- **Tier 2 (collaborator mocks):** Mock interfaces/services. Verify delegation and error propagation.
- **Tier 3 (post-refactor seams):** Test classes that required refactoring for injectability.
- **Tier 4 (legacy migration):** Rewrite JUnit 4 tests to JUnit 5 + AAA. Remove `printStackTrace`,
  empty catches, and live network calls.

### Before/After Example (from this repo)

**Before (JUnit 4, no AAA, ambiguous null, no AssertJ):**

```java
@Test
public void testaIntegerNull() {
    Assert.assertNotNull(Geleia.intNotNull(null));
}
```

**After (JUnit 5, AAA, AssertJ, explicit cast):**

```java
@Test
@DisplayName("should return default zero when Integer input is null")
void shouldReturnDefaultZeroWhenIntegerIsNull() {
    // Arrange
    Integer input = null;

    // Act
    Integer result = NullSafe.intNotNull(input);

    // Assert
    assertThat(result).isEqualTo(0);
}
```

## Verification Checklist

- [ ] Every test method has `// Arrange`, `// Act`, `// Assert` comments.
- [ ] One behavior per test.
- [ ] `@DisplayName` in English on every test method.
- [ ] Assertions use AssertJ `assertThat`.
- [ ] No network, filesystem, or clock access.
- [ ] No `printStackTrace` or empty catch blocks.
- [ ] `mvn -f build/pom.xml test` passes for the module.
