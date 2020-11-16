[![tommygun-eth Actions Status](https://github.com/abdelhamidbakhta/tommygun-eth/workflows/tommygun-eth-ci/badge.svg)](https://github.com/abdelhamidbakhta/tommygun-eth/actions)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/abdelhamidbakhta/tommygun-eth/blob/master/LICENSE)
[![sonar-quality-gate][sonar-quality-gate]][sonar-url] [![sonar-coverage][sonar-coverage]][sonar-url] [![sonar-bugs][sonar-bugs]][sonar-url] [![sonar-vulnerabilities][sonar-vulnerabilities]][sonar-url]
# Tommy Gun Eth

##Description

Build large state Ethereum testnet using standalone HTTP service connected to an Ethereum client.

## Installation

### Install Prerequisites

* Java 11

### Build Instructions

```shell script
./gradlew assemble
```

## Usage

```shell script
java -jar java -jar build/libs/tommygun-eth.jar
```

OR

```shell script
./gradlew bootRun
```

## Code Style

We use Google's Java coding conventions for the project. To reformat code, run: 

```shell script 
./gradlew spotlessApply
```

Code style is checked automatically during a build.

## API

### Open API v3

Go to http://localhost:8080 to see the API definition in a web browser.

API docs specification are available at http://localhost:8080/v3/api-docs

## Docker

### Build image

#### Using plain docker file
```shell script
docker build -t $IMAGE_NAME .
```

#### Using gradle task
```shell script
./gradlew bootBuildImage --imageName=$IMAGE_NAME
```

### Run container

```shell script
docker run -p 8080:8080 -t $IMAGE_NAME
```

### Use existing image

```shell script
docker run -p 8080:8080 abdelhamidbakhtaconsensys/tommygun-eth:latest
```


[sonar-url]: https://sonarcloud.io/dashboard?id=abdelhamidbakhta_tommygun-eth
[sonar-quality-gate]: https://sonarcloud.io/api/project_badges/measure?project=abdelhamidbakhta_tommygun-eth&metric=alert_status
[sonar-coverage]: https://sonarcloud.io/api/project_badges/measure?project=abdelhamidbakhta_tommygun-eth&metric=coverage
[sonar-bugs]: https://sonarcloud.io/api/project_badges/measure?project=abdelhamidbakhta_tommygun-eth&metric=bugs
[sonar-vulnerabilities]: https://sonarcloud.io/api/project_badges/measure?project=abdelhamidbakhta_tommygun-eth&metric=vulnerabilities