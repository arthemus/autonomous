# Skill: pt-en-translation

Translate Portuguese (Brazilian) content to English across Java source files, resource bundles, and POMs.

## When to Apply

- Translating JavaDoc, inline comments, string literals, log messages, or exception messages.
- Renaming Portuguese class names, method names, field names, or package names to English.
- Translating POM `<name>` and `<description>` elements.
- Translating resource bundle property files.

## Rules

### Scope of Translation

Translate ALL of the following to English:
1. JavaDoc (`/** ... */`)
2. Inline comments (`//` and `/* ... */`)
3. String literals (exception messages, log messages, UI labels)
4. Class names, method names, field names, parameter names, local variable names
5. Package names (move the file to the matching directory)
6. Resource bundle keys and values
7. POM `<name>` and `<description>` elements

### Terms to Keep as-is (Brazilian legal/domain terms)

These terms remain in Portuguese with an English explanation in JavaDoc:
- **CPF** - Individual Taxpayer Registry number (Brazilian individual tax ID)
- **CNPJ** - National Registry of Legal Entities number (Brazilian company tax ID)
- **UF** - Federative Unit (Brazilian state abbreviation)
- **CEP** - Postal code (Brazilian zip code format: XXXXX-XXX)

### Domain Glossary

| Portuguese | English |
|---|---|
| CEP | ZipCode (in identifiers) / postal code (in prose) |
| Rodizio | DrivingRestriction |
| Rodizio Municipal | MunicipalDrivingRestriction |
| Inscricao Estadual | StateRegistration |
| Boleto | BankSlip |
| Moeda | Currency |
| Feriado | Holiday |
| Semana | Week |
| Horario | Time |
| Calculo | Calculation |
| Calcula | Calculate |
| Formata | Formatter |
| Busca | Lookup |
| Conexao | Connection |
| Servidores | Servers |
| Mensageria | Messaging |
| Mensagem | Message |
| Funcoes | Functions |
| Geleia | NullSafe |
| Valida | Validates / Validator |
| Validador | Validator |
| Unidade Federativa | FederativeUnit |
| Calendario | CalendarUtils |
| Persistencia | Persistence |
| Relatorios | Reports |
| Injecao de Dependencia | DependencyInjection |
| Implementacoes Web | WebImplementations |
| Componentes Auxiliares | AuxiliaryComponents |
| Banco | Database |
| Tabela | Table |
| Consulta | Query |
| Resultado | Result |
| Excecao | Exception |
| Seguranca | Security |
| Criptografia | Crypto |
| Chave | Key |
| Senha | Password |
| Usuario | User |
| Cliente | Client |
| Endereco | Address |
| Telefone | Phone |
| Data | Date |
| Hora | Hour |
| Minuto | Minute |
| Segundo | Second |
| Dia | Day |
| Mes | Month |
| Ano | Year |
| Valor | Value |
| Numero | Number |
| Texto | Text |
| Arquivo | File |
| Pasta | Directory |
| Impressao | Printing / Print |
| Ambiente | Environment |
| Propriedade | Property |
| Parametro | Parameter |
| Retorno | Return |
| Classe | Class |
| Metodo | Method |
| Interface | Interface |
| Objeto | Object |
| Teste | Test |
| Testes Unitarios | UnitTests |

### Identifier Renaming Convention

- PascalCase for classes and interfaces: `Funcoes` -> `Functions`, `BuscaCEP` -> `ZipCodeLookup`.
- camelCase for methods and fields: `getValor` -> `getValue`, `isExiste` -> `isExists`.
- lowercase for packages: `mensageria` -> `messaging`, `servidores` -> `servers`, `seguranca` -> `security`.
- When renaming a package, move the file to the matching directory and update all imports across the repo.

### Encoding Verification

After editing any file, verify UTF-8 integrity:

```bash
iconv -f UTF-8 -t UTF-8 <file> > /dev/null
```

If this fails, the file has encoding corruption and must be fixed before committing.

### Resource Bundles

- Rename `mensagens.properties` -> `messages.properties`.
- Rename `mensagens_pt_BR.properties` -> `messages_pt_BR.properties`.
- Translate property values to English in the default `messages.properties`.
- Keep Portuguese values in `messages_pt_BR.properties` (the pt_BR locale variant).
- Update all `ResourceBundle.getBundle("mensagens", ...)` calls to use `"messages"`.

## Verification Checklist

- [ ] All JavaDoc, comments, strings, log messages, and exception messages are in English.
- [ ] All class, method, field, parameter, and local variable names are in English.
- [ ] All package names are in English; files moved to matching directories.
- [ ] All imports updated across the repo for renamed classes and packages.
- [ ] Brazilian legal terms (CPF, CNPJ, UF, CEP) kept with English JavaDoc explanation.
- [ ] Resource bundles renamed and translated.
- [ ] POM `<name>` and `<description>` elements translated.
- [ ] `iconv -f UTF-8 -t UTF-8` passes on every edited file.
- [ ] `mvn -f build/pom.xml clean install` passes after all renames.
