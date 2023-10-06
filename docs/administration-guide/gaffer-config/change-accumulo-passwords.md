# Changing the Accumulo Passwords

When deploying Accumulo - either as part of a Gaffer stack or as a standalone,
the passwords for all the users and the instance.secret are set to default
values and should be changed. The instance.secret cannot be changed once
deployed as it is used in initalisation.

The passwords can be configured in a standard deployment via the
[`accumulo.properties`](https://accumulo.apache.org/docs/2.x/configuration/files#accumuloproperties)
file.

The following table outlines the values and defaults if using the container
images:

| Name                 | value                           | default value |
| -------------------- | ------------------------------- | ------------- |
| Instance Secret      | `instance.secret`               | "DEFAULT"     |
| Tracer user          | `trace.user`                    | "root"        |
| Tracer user password | `trace.token.property.password` | "secret"      |
