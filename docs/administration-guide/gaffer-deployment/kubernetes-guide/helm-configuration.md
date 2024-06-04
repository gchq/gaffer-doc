# Configuring Gaffer with Helm

!!! warning
    Gaffer Kubernetes is not currently up to date. You will need to use a
    version of Kubernetes <= 1.25 for deployments.
    The information here is subject to change in future releases.

The general overview of what you can configure in a Gaffer graph is outlined
under the [configuring Gaffer pages](../../gaffer-config/config.md). However,
under a Helm based Kubernetes deployment the configuration needs to be applied
slightly differently, this page captures how you can currently configure a
Gaffer deployment using Helm.

!!! tip
    Use the `--reuse-values` argument on a Helm upgrade to re-use passwords
    from the initial construction.

## Graph Metadata

Create a file called `graph-meta.yaml`. We will use this file to add our
description and graph ID. Changing the description is as easy as changing the
`graph.config.description` value.

```yaml
graph:
  config:
    description: "My graph description"
```

Upgrade your deployment using Helm to load the new file:

```bash
helm upgrade my-graph gaffer-docker/gaffer -f graph-metadata.yaml --reuse-values
```

### Graph ID

Updating the ID may be simple or complicated depending on your store type. If
you are using a Map or Federated store, you can just set the
`graph.config.graphId` value like with the graph description. Though if you are
using a Map Store, the graph will be emptied as a result.

To safely update the Graph ID of an Accumulo instance you must change the gaffer
users permissions to read and write to that table. To do that update the
`graph-meta.yaml` file with the following contents:

```yaml
graph:
    config:
    graphId: "MyGraph"
    description: "My Graph description"

accumulo:
    config:
    userManagement:
        users:
        gaffer:
            permissions:
            table:
                MyGraph:
                - READ
                - WRITE
                - BULK_IMPORT
                - ALTER_TABLE
```

## Loading new Graph Schema

The easiest way to deploy a schema file is to use helms `--set-file` option
which lets you set a value from the contents of a file. For a Helm deployment to
pick up changes to a Schema, you need to run a helm upgrade:

```bash
helm upgrade my-graph gaffer-docker/gaffer --set-file graph.schema."schema\.json"=./schema.json --reuse-values
```

## Change Accumulo Passwords

When deploying the Accumulo Helm chart, the following values are set. If you are
using the Gaffer Helm chart with the Accumulo integration, the values will be
prefixed with "accumulo":

| Name                 | value                                         | default value |
| -------------------- | --------------------------------------------- | ------------- |
| Instance Secret      | `config.accumuloSite."instance.secret"`       | "DEFAULT"     |
| Root password        | `config.userManagement.rootPassword`          | "root"        |
| Tracer user password | `config.userManagement.users.tracer.password` | "tracer"      |

When you deploy the Gaffer Helm chart with Accumulo, a "gaffer" user with a
password of "gaffer" is used by default following the same pattern as the tracer
user.

So to install a new Gaffer with Accumulo store, create an
`accumulo-passwords.yaml` with the following contents:

```yaml
accumulo:
  enabled: true
  config:
    accumuloSite:
      instance.secret: "changeme"
    userManagement:
      rootPassword: "changeme"
      users:
        tracer:
          password: "changme"
        gaffer:
          password: "changeme"
```

You can install the graph with:

```bash
helm install my-graph gaffer-docker/gaffer -f accumulo-passwords.yaml
```
