# Atom JAXB

Atom JAXB is Java library focused on Atom 1.0 feed marshalling/unmarshalling.

![Build Status](https://github.com/vidal-community/atom-jaxb/actions/workflows/ci.yml/badge.svg)
[![Coverage Status](https://coveralls.io/repos/vidal-community/atom-jaxb/badge.svg?branch=master)](https://coveralls.io/r/vidal-community/atom-jaxb?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/fr.vidal.oss/atom-jaxb.svg)](https://search.maven.org/artifact/fr.vidal.oss/atom-jaxb/)

## What it does

Atom JAXB provides the standard Atom 1.0 JAXB beans you need as
well as a simple extension mechanism, leveraging namespaces.

Indeed, the recommended approach is to extend standard Atom
element with your own elements in your own namespaces (or
other standard ones).

This can be done as follows:

```java
	Feed feed = new Feed();
	// [...]
	Entry entry = new Entry();
	entry.addAdditionalElement(new SimpleElement(
		new Namespace("http://my.own.namespace", "my"),
		"custom",
		"42",
		Collections.emptyList()
	));
	feed.addEntry(entry);
```

which roughly corresponds to:

```xml
	<?xml version="1.0" encoding="UTF-8"?>
	<feed xmlns="http://www.w3.org/2005/Atom">
		<entry>
			<!-- [...] -->
			<my:custom xmlns:my="http://my.own.namespace">42</my:custom>
		</entry>
	</feed>
```

## What it does not (and will never do)

Atom JAXB is not designed with complex XML tree in mind.
Instead, it emphasizes:

   - flat structures (favour links between entries over nested entry elements)
   - simple textual `<content>` (plain text or HTML)

## Benchmark

    $> mvn package
    $> java -jar benchmark/target/benchmarks.jar Marshaller
