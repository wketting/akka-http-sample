# Akka HTTP tutorial


This project demonstrates the [Akka HTTP](http://doc.akka.io/docs/akka-http/current/scala/http/) library and Scala to write a simple REST (micro)service. The project shows the following tasks that are typical for most Akka HTTP-based projects:

* starting standalone HTTP server,
* handling file-based configuration,
* routing,
* deconstructing requests,
* unmarshalling JSON entities to Scala's case classes,
* marshaling Scala's case classes to JSON responses,
* error handling,
* issuing requests to external services,
* testing.

## Usage

Start services with sbt:

```
$ sbt run
```

With the service up, you can start sending HTTP requests:

```
$ curl http://localhost:9000/users
[{"name":"test1"},{"name":"test2"},{"name":"test3"}]
```

```
$ curl -X POST -H 'Content-Type: application/json' http://localhost:9000/users/login -d '{"name": "test1", "password": "test1"}'
{"name":"test1"}
```

### Testing

Execute tests using `test` command:

```
$ sbt test
```
