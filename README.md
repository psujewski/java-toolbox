# Toolbox Core

Minimal Java utility library providing testable abstractions for system time and UUID generation.

## ✨ Features

- `TimeProvider` — abstraction over `Clock` for deterministic time control
- `UuidProvider` — abstraction over `UUID.randomUUID()` for easier testing
- Pure Java, no external dependencies
- Easily integrates with Spring Boot

## 📦 Maven

```xml
<dependency>
  <groupId>info.psuj</groupId>
  <artifactId>toolbox-core</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
