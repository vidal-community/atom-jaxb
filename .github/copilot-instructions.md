# Copilot Instructions for atom-jaxb

## Repository Overview

**atom-jaxb** is a Java library for Atom 1.0 feed marshalling/unmarshalling using JAXB. It provides standard Atom 1.0 JAXB beans with a simple extension mechanism leveraging XML namespaces. The library emphasizes flat structures and simple textual content over complex nested XML trees.

**Project Type**: Maven multi-module Java library  
**Language**: Java 11  
**Build Tool**: Maven 3.0.4+ (with wrapper)  
**Size**: Small (~23 main Java classes, 9 test classes)  
**Group ID**: fr.vidal.oss  
**Artifact ID**: atom-jaxb-parent (parent), atom-jaxb (main module), atom-jaxb-benchmark (benchmark module)

## Build Environment

- **Java Version**: Java 11 - CRITICAL: specified in `pom.xml` (java.version=11) and `.java-version` (11.0)
- **Maven Version**: 3.0.4 minimum (use Maven wrapper `./mvnw`)
- **Encoding**: UTF-8 (project.build.sourceEncoding)
- **EditorConfig**: Configured for 4-space indentation, LF line endings, YAML uses 2-space indentation

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
- Compiler warnings about deprecated AssertJ methods are EXPECTED and can be ignored (43 warnings in generated code + 11 in test code)

**Full verification** (compile + test + package + javadoc):
```bash
./mvnw --threads 1C verify --no-transfer-progress
```
- This is the EXACT command used in CI (.github/workflows/ci.yml)
- Generates: JAR, sources JAR, test JAR, javadoc JAR for all modules
- Always use `--threads 1C` flag for consistent parallel builds
- This builds both atom-jaxb and benchmark modules

**Install to local Maven repository**:
```bash
./mvnw install -DskipTests --no-transfer-progress
```
- Use `-DskipTests` to skip test execution when you only need to install
- Installs all modules (parent, atom-jaxb, benchmark) to local Maven repository

**Build benchmark only** (JMH performance benchmarks):
```bash
./mvnw package -pl benchmark --no-transfer-progress
```
- Builds only the benchmark module
- Requires atom-jaxb to be installed first (or built in same reactor)
- Creates benchmarks.jar (uber-JAR) in benchmark/target/

**Run benchmarks**:
```bash
java -jar benchmark/target/benchmarks.jar Marshaller
```
- Runs JMH benchmarks for marshalling performance

## Project Structure

```
atom-jaxb/                            # Root directory
├── .github/
│   ├── workflows/
│   │   └── ci.yml                    # GitHub Actions CI pipeline
│   ├── dependabot.yml                # Dependency updates configuration
│   └── copilot-instructions.md       # This file
├── atom-jaxb/                        # Main library module
│   ├── pom.xml                       # Module POM
│   ├── src/main/java/fr/vidal/oss/jaxb/atom/core/
│   │   ├── Feed.java                 # Root Atom feed element
│   │   ├── Entry.java                # Atom entry element
│   │   ├── Link.java                 # Atom link element
│   │   ├── AtomJaxb.java             # JAXBContext factory
│   │   ├── SimpleElement.java        # Extension element
│   │   ├── ExtensionElement.java     # Extension interface
│   │   ├── package-info.java         # JAXB namespace config
│   │   └── [20 other core classes]
│   ├── src/test/java/fr/vidal/oss/jaxb/atom/
│   │   ├── MarshallingTest.java      # Main marshalling tests
│   │   ├── UnmarshallingTest.java    # Main unmarshalling tests
│   │   ├── Date*.java                # Date handling tests
│   │   └── core/                     # Core component tests
│   └── target/                       # Build output (git-ignored)
│       ├── generated-test-sources/assertj-assertions/
│       ├── classes/                  # Compiled main classes
│       ├── test-classes/             # Compiled test classes
│       ├── atom-jaxb-*.jar           # Main JAR
│       └── atom-jaxb-*-tests.jar     # Test JAR with assertions
├── benchmark/                        # JMH benchmarking module
│   ├── pom.xml                       # Benchmark module POM
│   ├── src/main/java/                # Benchmark sources
│   └── target/
│       └── benchmarks.jar            # Uber-JAR for benchmarks
├── pom.xml                           # Parent POM (aggregator)
├── .java-version                     # Java version spec (11.0)
├── mvnw, mvnw.cmd                    # Maven wrapper scripts
├── .mvn/                             # Maven wrapper config
├── .editorconfig                     # Editor configuration
├── deploy-settings.xml               # Maven deployment settings
├── README.md                         # Project documentation
└── CONTRIBUTING.md                   # Contribution guidelines
```

## Key Dependencies

- **JAXB API & Runtime**: jakarta.xml.bind-api (via Glassfish JAXB BOM 4.0.6)
- **Test**: JUnit 4.13.2, AssertJ Core 3.27.6
- **Code Coverage**: JaCoCo 0.8.14, Coveralls 4.3.0
- **Benchmark**: JMH 1.37 (in benchmark module only)
- **Note**: Project migrated from javax.xml.bind to jakarta.xml.bind (Jakarta EE)

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
2. **Setup Java 11** (Temurin distribution) with Maven cache
3. **Prepare**: `./mvnw --version --no-transfer-progress`
4. **Build and test**: `./mvnw --threads 1C verify --no-transfer-progress`
5. **Report coverage** (master branch only): `./mvnw test jacoco:report coveralls:report -DrepoToken=$COVERALLS_TOKEN -DpullRequest=$PR_NUMBER --no-transfer-progress`
6. **Deploy** (master branch only): `./mvnw clean deploy -DskipTests --settings ./deploy-settings.xml --no-transfer-progress`

**Important**: The deploy step uses environment variables `OSSRH_USER` and `OSSRH_PASS` from GitHub Secrets configured in `deploy-settings.xml`.

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
**Solution**: The benchmark module depends on atom-jaxb artifact. When building with `./mvnw verify`, the reactor builds all modules in order. If building benchmark separately with `-pl benchmark`, ensure the main module is built first or use `./mvnw install` to install all artifacts to local repository.

### Issue: Tests fail with JAXB errors
**Solution**: Ensure Java 11 is being used. The project requires Java 11 and uses Jakarta JAXB (jakarta.xml.bind) instead of the older javax.xml.bind. Check `.java-version` file and `pom.xml` for version requirements.

### Issue: Compiler warnings about deprecated AssertJ methods
**Solution**: These are EXPECTED. The auto-generated AssertJ assertions (in `target/generated-test-sources/assertj-assertions/`) use deprecated methods. This is normal and can be ignored (43 warnings in generated code + 11 in test code).

### Issue: Maven build hangs or is very slow
**Solution**: Always use `--no-transfer-progress` flag to reduce output and improve performance. This is the standard used in CI builds.

## Making Changes

When making code changes:

1. **Before modifying**: Run `./mvnw clean --no-transfer-progress` for a fresh start
2. **After modifying main code**: Run `./mvnw compile --no-transfer-progress` to verify compilation
3. **After modifying test code**: Run `./mvnw test --no-transfer-progress` to verify tests pass
4. **Before committing**: Run `./mvnw --threads 1C verify --no-transfer-progress` to ensure CI will pass
5. **Check generated assertions**: If you modified core classes, AssertJ assertions will be regenerated automatically

## File Locations Reference

- **Main source**: `atom-jaxb/src/main/java/fr/vidal/oss/jaxb/atom/core/`
- **Test source**: `atom-jaxb/src/test/java/fr/vidal/oss/jaxb/atom/`
- **Build output**: `atom-jaxb/target/` and `benchmark/target/`
- **CI configuration**: `.github/workflows/ci.yml`
- **Maven config**: `pom.xml` (parent), `atom-jaxb/pom.xml` (main module), `benchmark/pom.xml` (benchmark module)
- **Deploy settings**: `deploy-settings.xml` (uses OSSRH_USER/OSSRH_PASS env vars)
- **Java version**: `.java-version` (specifies 11.0)

## Trust These Instructions

These instructions have been validated by actually running the commands. Trust them and only search for additional information if:
- A command fails with an error not documented here
- You need to understand implementation details not covered here
- The instructions appear outdated based on file contents

Always prefer using the Maven wrapper (`./mvnw`) over system Maven, and always include the `--no-transfer-progress` flag for cleaner output and better performance.

