# Copilot Instructions for atom-jaxb

## Repository Overview

**atom-jaxb** is a Java library for Atom 1.0 feed marshalling/unmarshalling using JAXB. It provides standard Atom 1.0 JAXB beans with a simple extension mechanism leveraging XML namespaces. The library emphasizes flat structures and simple textual content over complex nested XML trees.

**Project Type**: Java library (Maven)  
**Language**: Java 8  
**Build Tool**: Maven 3.0.4+ (with wrapper)  
**Size**: Small (~20 main Java classes, 9 test classes)  
**Group ID**: fr.vidal.oss  
**Artifact ID**: atom-jaxb

## Build Environment

- **Java Version**: Java 8 (1.8) - specified in `pom.xml`
- **Maven Version**: 3.0.4 minimum (use Maven wrapper `./mvnw`)
- **Encoding**: UTF-8 (project.build.sourceEncoding)
- **EditorConfig**: Configured for 4-space indentation, LF line endings

## Build Commands - TESTED AND VALIDATED

### Always use the Maven wrapper (`./mvnw`) and include `--no-transfer-progress` flag

**Clean the project** (removes target directory):
```bash
./mvnw clean --no-transfer-progress
```

**Compile only**:
```bash
./mvnw compile --no-transfer-progress
```

**Run tests** (generates assertj assertions automatically before test compilation):
```bash
./mvnw test --no-transfer-progress
```
- Expected time: ~4 seconds
- Compiler warnings about deprecated AssertJ methods are EXPECTED and can be ignored

**Full verification** (compile + test + package + javadoc):
```bash
./mvnw --threads 1C verify --no-transfer-progress
```
- This is the EXACT command used in CI (.github/workflows/ci.yml)
- Expected time: ~6 seconds
- Generates: JAR, sources JAR, test JAR, javadoc JAR
- Always use `--threads 1C` flag for consistent parallel builds

**Install to local Maven repository** (required before building benchmark):
```bash
./mvnw install -DskipTests --no-transfer-progress
```
- Use `-DskipTests` to skip test execution when you only need to install
- Required for benchmark module which depends on atom-jaxb artifact

**Build benchmark** (JMH performance benchmarks):
```bash
./mvnw install -DskipTests --no-transfer-progress
./mvnw package -f benchmark/aggregator-pom.xml --no-transfer-progress
```
- IMPORTANT: Must run `install` on main project FIRST before building benchmark
- Benchmark depends on atom-jaxb artifact being in local Maven repository
- Creates benchmarks.jar in benchmark/target/

**Run benchmarks**:
```bash
java -jar benchmark/target/benchmarks.jar Marshaller
```

## Project Structure

```
atom-jaxb/
├── .github/
│   ├── workflows/
│   │   └── ci.yml                    # GitHub Actions CI pipeline
│   └── dependabot.yml                # Dependency updates configuration
├── src/main/java/fr/vidal/oss/jaxb/atom/
│   └── core/                         # Main package with JAXB beans
│       ├── Feed.java                 # Root Atom feed element
│       ├── Entry.java                # Atom entry element
│       ├── Link.java                 # Atom link element
│       ├── AtomJaxb.java             # JAXBContext factory
│       ├── SimpleElement.java        # Extension element
│       ├── ExtensionElement.java     # Extension interface
│       └── [other core classes]
├── src/test/java/fr/vidal/oss/jaxb/atom/
│   ├── MarshallingTest.java          # Main marshalling tests
│   ├── UnmarshallingTest.java        # Main unmarshalling tests
│   └── [other test classes]
├── benchmark/                        # JMH benchmarking module
│   ├── pom.xml                       # Separate Maven module
│   ├── aggregator-pom.xml            # Multi-module build config
│   └── src/main/java/
├── target/                           # Build output (git-ignored)
│   ├── generated-test-sources/
│   │   └── assertj-assertions/       # Auto-generated AssertJ assertions
│   ├── classes/                      # Compiled main classes
│   ├── test-classes/                 # Compiled test classes
│   └── [JAR files]
├── pom.xml                           # Main Maven configuration
├── mvnw, mvnw.cmd                    # Maven wrapper scripts
├── .editorconfig                     # Editor configuration
└── deploy-settings.xml               # Maven deployment settings
```

## Key Dependencies

- **JAXB API & Runtime**: javax.xml.bind (via Glassfish JAXB BOM 2.3.1)
- **Test**: JUnit 4.13, AssertJ Core 3.22.0
- **Code Coverage**: JaCoCo 0.8.5, Coveralls 4.3.0
- **Benchmark**: JMH 1.37 (in benchmark module only)

## Generated Code

**AssertJ Assertions are AUTOMATICALLY generated** during the test compilation phase:
- Generated in: `target/generated-test-sources/assertj-assertions/`
- Plugin: assertj-assertions-generator-maven-plugin 2.2.0
- Generates assertions for package: `fr.vidal.oss.jaxb.atom.core`
- Entry point class: `fr.vidal.oss.jaxb.atom.Assertions`
- Do NOT manually edit these files
- Regenerated on every test compilation

## CI/CD Pipeline (.github/workflows/ci.yml)

The GitHub Actions CI workflow runs on every push:

1. **Checkout** code
2. **Setup Java 8** (Temurin distribution) with Maven cache
3. **Prepare**: `./mvnw --version --no-transfer-progress`
4. **Build and test**: `./mvnw --threads 1C verify --no-transfer-progress`
5. **Report coverage** (master branch only): JaCoCo + Coveralls
6. **Deploy** (master branch only): `./mvnw clean deploy -DskipTests --settings ./deploy-settings.xml`

## Commit Message Format (CONTRIBUTING.md)

Follow this STRICT format or commits will be rejected:

**Format**: `(Issue#REF )?Verb-imperative action-done`

**Rules**:
- Subject: max 50 characters
- Issue reference in UPPER CASE (e.g., `Issue#123`)
- First letter after issue reference: UPPER CASE
- Use imperative mood (e.g., "Add", NOT "Added")
- Body wrapped at 70 characters

**Valid examples**:
- `Issue#123 Fix compiler version`
- `Fix typo in ProductResource javadoc`
- `Issue#321 Add /imd/lppr/code/{code}`

**Invalid examples**:
- `issue#1323 hello world` (lowercase issue, lowercase subject)
- `Issue#1323 Added some stuff` (past tense instead of imperative)
- Subject longer than 50 characters

## Common Build Issues & Solutions

### Issue: Benchmark build fails with "Could not resolve dependencies"
**Solution**: Always run `./mvnw install -DskipTests --no-transfer-progress` FIRST to install atom-jaxb to local Maven repository before building the benchmark module.

### Issue: Tests fail with JAXB errors
**Solution**: Ensure Java 8 is being used. The project uses Glassfish JAXB runtime explicitly for Java 8+ compatibility.

### Issue: Compiler warnings about deprecated AssertJ methods
**Solution**: These are EXPECTED. The auto-generated AssertJ assertions use deprecated methods. This is normal and can be ignored (43 warnings in generated code + 11 in test code).

### Issue: Maven build hangs or is very slow
**Solution**: Always use `--no-transfer-progress` flag to reduce output and improve performance.

## Making Changes

When making code changes:

1. **Before modifying**: Run `./mvnw clean --no-transfer-progress` for a fresh start
2. **After modifying main code**: Run `./mvnw compile --no-transfer-progress` to verify compilation
3. **After modifying test code**: Run `./mvnw test --no-transfer-progress` to verify tests pass
4. **Before committing**: Run `./mvnw --threads 1C verify --no-transfer-progress` to ensure CI will pass
5. **Check generated assertions**: If you modified core classes, AssertJ assertions will be regenerated automatically

## File Locations Reference

- **Main source**: `src/main/java/fr/vidal/oss/jaxb/atom/core/`
- **Test source**: `src/test/java/fr/vidal/oss/jaxb/atom/`
- **Build output**: `target/`
- **CI configuration**: `.github/workflows/ci.yml`
- **Maven config**: `pom.xml` (main), `benchmark/pom.xml` (benchmark)
- **Deploy settings**: `deploy-settings.xml` (uses OSSRH_USER/OSSRH_PASS env vars)

## Trust These Instructions

These instructions have been validated by actually running the commands. Trust them and only search for additional information if:
- A command fails with an error not documented here
- You need to understand implementation details not covered here
- The instructions appear outdated based on file contents

Always prefer using the Maven wrapper (`./mvnw`) over system Maven, and always include the `--no-transfer-progress` flag for cleaner output and better performance.

