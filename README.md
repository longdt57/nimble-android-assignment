# Surveys (Coroutine)

- An assignment from Nimble

## Setup

- Clone the project
- Run the project with Android Studio
- Replace the `secrets.properties` keys with provided keys.
- Replace the `urls.properties` url with provided url.

## Linter and static code analysis

- Lint:

```
$ ./gradlew lint

Auto format code
$ ./gradlew ktlintFormat
```

Report is located at: `./app/build/reports/lint/`

- Detekt

```
$ ./gradlew detekt
```

Report is located at: `./build/reports/detekt`

## Testing

- Run unit testing:

```
$ ./gradlew app:testStagingDebugUnitTest
$ ./gradlew data:testDebugUnitTest
$ ./gradlew domain:test
```

- Run unit testing with coverage:

```
$ ./gradlew jacocoTestReport
```

Report is located at: `./app/build/reports/jacoco/`

## Build and deploy

For `release` builds, we need to provide release keystore and signing properties:

- Put the `release.keystore` file at root `config` folder.
- Put keystore signing properties in [signing.properties](https://github.com/nimblehq/android-templates/blob/develop/NimbleLeeAssignment/signing.properties).
