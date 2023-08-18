# Adding your own libraries and functions

By default with the Gaffer deployment you get access to the:

- Sketches library
- Time library
- Bitmap Library
- JCS cache library

If you want more libraries than this (either one of ours of one of your own) you will need to customise the docker images and use them in place of the defaults.

You will need a [basic Gaffer instance deployed on Kubernetes](deploy-empty-graph.md).

## Add Extra Libraries to Gaffer REST

At the moment, Gaffer uses a runnable jar file located at `/gaffer/jars`. When it runs it includes the `/gaffer/jars/lib` on the classpath. There is nothing in there by default because all the dependencies are bundled in to the JAR. However, if you wanted to add your own jars, you can do it like this:

```Dockerfile
FROM gchq/gaffer-rest:latest
COPY ./my-custom-lib:1.0-SNAPSHOT.jar /gaffer/jars/lib/
```

Build the image using:

```bash
docker build -t custom-rest:latest .
```

## Add the extra libraries to the Accumulo image

Gaffer's Accumulo image includes support for the following Gaffer libraries:

- The Bitmap Library
- The Sketches Library
- The Time Library

In order to push down any extra value objects and filters to Accumulo that are not in those libraries, we have to add the jars to the accumulo `/lib/ext directory`. Here is an example `Dockerfile`:

```Dockerfile
FROM gchq/gaffer:latest
COPY ./my-library-1.0-SNAPSHOT.jar /opt/accumulo/lib/ext
```

Then build the image

```bash
docker build -t custom-gaffer-accumulo:latest .
```

# Switch the images in the deployment

You will need a way of making the custom images visible to the kubernetes cluster. Once visible you can switch them out. Create a `custom-images.yaml` file with the following contents:

```yaml
api:
  image:
    repository: custom-rest
    tag: latest

accumulo:
  image:
    repository: custom-gaffer-accumulo
    tag: latest
```

To switch them run:

```bash
helm upgrade my-graph gaffer-docker/gaffer -f custom-images.yaml --reuse-values
```