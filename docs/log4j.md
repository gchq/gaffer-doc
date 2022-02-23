# Log4j in Gaffer

This page contains information on how logging is done in Gaffer and on previous use of Log4j in Gaffer.

## Log4j Version

Log4j version 1 (1.2.17), was used by Gaffer versions 1.21 and below. From Gaffer 1.22, Log4j was replaced with Reload4j. **The newer version of Log4j, Log4j2 - which is susceptible to the major Log4Shell attack, has never been used by Gaffer or its dependencies.** 

## How Logging is done

Gaffer uses SLF4J ([Simple Logging Facade for Java](https://www.slf4j.org/)) for all logging. This is a framework/abstraction layer which allows for different loggers to be used ([known as bindings](https://www.slf4j.org/manual.html#swapping)). The binding used by Gaffer is `org.slf4j:slf4j-reload4j:jar:1.7.36`.

## Dependencies of Gaffer using Log4j 1.2.17

Some major Gaffer dependencies (listed below) use Log4j internally (either directly or through SLF4J). From Gaffer version 1.22 these transitive dependencies are excluded and replaced with Reload4j, such that Log4j does not appear on the classpath at all.

- GCHQ Koryphe 1.14.0 - Uses SLF4J with Log4j.
- Apache HBase 1.3.0 - Multiple artefacts used from the group `org.apache.hbase`. All depend directly on Log4j.
- Apache Hadoop 2.6.5 - Multiple artefacts used from the group `org.apache.hadoop`. All depend directly on Log4j.
- Apache Accumulo 1.9.3 - Multiple artefacts used from the group `org.apache.accumulo`. All depend directly on Log4j.
- Apache Kafka 0.10.0.0 - Artefact depends indirectly on Log4j through a sub dependency (`com.101tec:zkclient`).
- Apache Spark 2.3.2 - Artefact depends directly on Log4j.

## Log4j Vulnerabilities

Current vulnerabilities in Log4j 1.12.17 relate to the JDBC, SMTP and JMS appenders, the JMS Sink and the Socket Server. Gaffer never used any of this. In its default configuration, we don't believe Gaffer is vulnerable to any of these problems. If the Log4j configuration is altered, changes could be made which may cause Gaffer to be vulnerable to one or more of the above vulnerabilities. Standard security processes to prevent unauthorised access and modification of configuration files should preclude this possibility.
