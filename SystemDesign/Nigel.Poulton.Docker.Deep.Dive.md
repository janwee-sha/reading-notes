# 2. Docker - The TLDR

Docker is software that runs on Linux and Windows. It creates, manages and orchestrates containers. 

# 4. The big picture

## 4.1 The Ops Perspective

When you install Docker, you get two major conponents:

- The Docker client
- The Docker daemon (sometimes called "server" or "engine")

The daemon implements the [Docker Engine API.](https://docs.docker.com/engine/api/)

In a default Linux installation, the client talks to the daemon via a local IPC/UNIX socket at */var/run/docker.sock*. On Windows this happens via a named pipe at *npipe:////./pipe/docker_engine*. 

# 5. The Docker Engine

## 5.1 Docker Engine - The TLDR

The major components that make up the Docker engine are: the *Docker client*, the *Docker daemon*, *containerd*, and *runc*.

<To be continued>

# 6. Images

## 6.2 Docker images - The deep dive

### 6.2.8 Filtering the output of `docker image ls`

Docker provides the `--filter` flag to filter the list of images returned by `docker image ls`.

The following example will only return dangling images:

```
$ docker images -a --filter dangling=true
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
mysql        <none>    a3a2968869cf   2 months ago   538MB
```

A dangling image is an image that is no longer tagged, and appears in listing as `<none>:<none>`. A common way they occur is when building a new image and tagging it with an existing tag. When this happens, Docker will build the new image, notice that an existing image has a matching tag, remove the tag from the existing image, give the tag to the new image.

Delete all dangling images on a system:

```
docker image prune
```

If you add the `-a` flag, Docker will also remove all unused images.

For all other filtering you can use `refernce`. Display only images tagged as "latest":

```
$ docker images --filter=reference="*:latest"
REPOSITORY   TAG       IMAGE ID       CREATED        SIZE
mysql        latest    57da161f45ac   32 hours ago   517MB
kubia        latest    2e7625696c2a   6 weeks ago    998MB
```

You can also use the `--format` flag to format output using GO tempaltes. For example:

```
$ docker images --format "{{.Repository}}: {{.Tag}}: {{.Size}}"
mysql: latest: 517MB
janwee/kubia: latest: 998MB
```

### 6.2.10 Images and layers

You can see the layers of an image is to inspect the image with `docker image inspect` command:

```
$ docker image inspect kubia --format "{{.RootFS.Layers}}"
[sha256:4efcd4003c841a56db1aa83ed4e7d7fb349a077eebc8c8f282a582d0bcc2a5b6 sha256:c284f546974cbf8f5370631f466f9d3b6e7f0d83e9960c85eaee230023568384 sha256:7354e83da007b4ad7c02c816904f86a31dc9663a2cbce10773b7a0bc0f5f09fa sha256:bb2453e129479a90ca0a31934569471f4e0fc9374eeb8b5ceaa6027dd5b72018 sha256:fa1175420e6f06a11266a95a2bf4822c3bd844f7c4de6fd5bfba294a6f70bbad sha256:3812bcc0cb2fb5689f2dc56a1af5c4194aaa1a2a1dea967aa5a07d0182e19799 sha256:4d0a418e8e285c9f434d2d7a020f7e8ff6380d486da3a16cfe75afd468f39bfb sha256:b92f538bab794e11cd9e7e315529095807de4e55639ad0065aaa4cc3f303699c sha256:469d9df1e9b9a5f8a425feae71c0397298fd37758804d9e789c33c328a45a51b sha256:5d343ddb671d213b7525673ebe4d64a50640c53cd5f961e7c7d0ddaf54103fde]
```

### 6.2.12 Pulling images by digest

Pulling images by tag has a problem -- tags are mutable. 

Docker 1.10 inintroduced a new content addressable storage model. As part of this new model, all images now get a cryptographic content hash. Because the digest is a hash of the contents of the image, it is not posiible to change the contents without the digest also changing.

You can view the digest when you pull an image. You can also do that by adding `--digest` flag to the `docker images` command:
```
$ docker images --digests mysql
REPOSITORY   TAG       DIGEST                                                                    IMAGE ID       CREATED        SIZE
mysql        latest    sha256:8653...0   57da161f45ac   33 hours ago   517MB
mysql        <none>    sha256:66ef...1   a3a2968869cf   2 months ago   538MB
```

### 6.2.14 Multi-architecture images

When new platforms and architectures, such as Windows, ARM, and s390x were added, smooth experience of Docker were broken. We have to think about whether the image we're pulling is built for the architecture we're running on.

Multi-architecture images is a solution for this problem.

Docker now supports multi-architecure images. This means a single image can have an image for Linux on x64, Linux on PowerPC, Windows x64, ARM etc.

To make this happen, the Registry API supports two important constructs:

- **manifest lists** (new): a list of architectures supported by a particular image tag. Each supported architecture then has its own **manifest** detailing the layers it's composed from.
- **manifests**

The fllowing figure uses the official *golang* image as an example:

![image](https://note.youdao.com/favicon.ico)

# 8. Containerizing an app

## 8.2 Containerizing an app - The deep dive
The following is an example Dockerfile:

```
FROM alpine
LABEL maintainer="janweesha@gmail.com"
RUN apk add --update nodejs nodejs-npm
COPY . /src
WORKDIR /src
RUN npm install
EXPOSE 8080
ENTRYPOINT ["node", "./app.js"]
```

All Dockerfiles start with the `FROM` instruction. This will be the base layer of the image, and the rest of the app will be added on top as additional layers.

As this point, the image looks like:

```
+------------+
|FROM alpine |
+------------+
```

The `RUN apk add --update nodejs nodejs-npm` uses the Alpine apk package manager to installs these packages as a new image layer.

The image now looks like:

```
+-------------------+
|RUN apk add npm... |
+-------------------+
|FROM alpine        |
+-------------------+
```

The `COPY . /src` instruction copies in the app files from the *build context*. The image now has three layers:

```
+-------------------+
|COPY . /src        |
+-------------------+
|RUN apk add npm... |
+-------------------+
|FROM alpine        |
+-------------------+
```

Next, the Dockerfile uses the `WORKDIR` instruction to set the working directory for the rest of the instructions in the file. This directory is relative to the image, and the info is added as metadata to the image config and not as a new layer.

Then the `RUN` npm install instruction uses `npm` to install application dependencies
listed in the `package.json` file in the build context. It runs within the context of the `WORKDIR` set in the previous instruction. The image now has four layers:

```
+-------------------+
|RUN npm install... |
+-------------------+
|COPY . /src        |
+-------------------+
|RUN apk add npm... |
+-------------------+
|FROM alpine        |
+-------------------+
```

The application exposes a web service on TCP port 8080, so the Dockerfile documents
this with the `EXPOSE 8080` instruction.

Finally, the `ENTRYPOINT` instruction is used to set the main application that the image (container) should run.

## 8.3 Containerizing an app - The commands

- `docker image build`: The `-t` flag tags the image, and the `-f` flag lets you specify the name and location of the Dockerfile.
- `FROM`
- `RUN`: Allows you to run commands inside the image which create new layer. Each `RUN` instruction creates a single new layer.
- `COPY`: It's common to use the `COPY` instruction to copy your application code into an image.
- `EXPOSE`
- `ENTRYPOINT`: Sets the default application to run when the image is started as a container.
- Others: `LABEL`, `ENV`, `ONBUILD`, `HEALTHCHECK`, `CMD` etc.

# 9. Deploying Apps with Docker Compose

Docker Compose deploys and manages multi-container applications on Docker nodes operating in **single-engine mode**.

## 9.2 Deploying Apps with Docker Compose - The deep dive

### 9.2.3 Compose files

Compose use YAML files to define multi-serice applications.

The default name for the Compose YAML file is *docker-compose.yml*. However, you
can use the `-f` flag to specify custom filenames.

Here's an example Docker Compose file:

```
version: "3.8"

services:

  db:
    image: mysql
    command: --default-authentication-plugin=mysql_native_password
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: janwee
      MYSQL_USER: janwee
      MYSQL_PASSWORD: janwee
    volumes:
      - /usr/local/mysqldata:/var/lib/mysql
    ports:
      - 3306:3306

  adminer:
    image: adminer
    restart: always
    ports:
      - 8080:8080
```

**Top-level keys**:

- `version`: It's mandatory, and it's always the first line at the root of the file. This defines the version of the Compose file format (basically the API).
- `services`: It's where we define the different application services. Compose will deploy each of these services as its own container.
- `networks`: It tells Docker to create new networks. By default, Compose will create bridge networks. These are single-host networks that can only connect containers on the same host. However, you can use the `driver` property to specify different network types.
- `volumes`: This key is where we tell Docker to create new volumes.
- Others: `secrects`, `configs` etc.

