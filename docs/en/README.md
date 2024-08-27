# Instaflix

[![Android CI/CD Pipeline](https://github.com/rovargas15/Instaflix/actions/workflows/android.yml/badge.svg?branch=master)](https://github.com/rovargas15/Instaflix/actions/workflows/android.yml)

Instaflix is a demo project for Instaleap that showcases a list of movies and series, divided into
three categories each.

Below are the technical specifications of the project.

## Prerequisites

Before running the project, make sure to set up your environment correctly. You have two options for
doing this:

### Option 1: Using the `SecretCreate.sh` Script

1. Clone the repository.
2. After cloning the repository, locate the **SecretCreate.sh** file at the root of the project.
3. Open it and follow the instructions to add your `API_KEY`
   from [The Movie Database (TMDB)](https://developers.themoviedb.org/3/getting-started/introduction).
4. Running the script will generate a `secrets.properties` file containing the `API_KEY` required by
   the `data` module in Gradle.

### Option 2: Manual Creation of `secrets.properties`

1. Clone the repository.
2. Create a `secrets.properties` file at the root of the project.
3. Add the following line to the file, replacing `YOUR_API_KEY` with your API key obtained
   from [The Movie Database (TMDB)](https://developers.themoviedb.org/3/getting-started/introduction):

   ```properties 
   API_KEY=TU_API_KEY

Once the `secrets.properties`s file is configured with the `API_KEY`, sync the project and run the
build.

## Technologies

his project was developed entirely with Jetpack Compose :) — a new declarative framework for
creating UI that is expected to become the default choice for this purpose.

## Architecture

The project follows the Clean Architecture approach, ensuring high cohesion and low coupling through
layer separation:

- **Domain Layer**: Contains business specifications.
- **Data Layer**: Groups components associated with different data sources, such as mappers,
  response models, entities, and repositories.
- **Presentation Layer**: Contains functionality modules (`movie`, `tv`, `favorite`) and
  the `NavigatorController`, which manages the application's flow.
- **Feature:**(`movie`, `tv`, `favorite`) Contain the app's functionalities.

## Dependency Injection

The project uses Dependency Injection (DI) to facilitate layer separation, following *the principle
of dependency inversion*. This prevents the domain layer from having direct references to the data
layer, making the code easier to test and maintain. The DI framework used is *Hilt*, which is based
on Dagger to efficiently build the dependency tree.

## Unit test

Unit tests have been implemented for viewModels, use cases, and repositories, as these components
contain most of the project's logic.

## API

- **URL base**: `https://api.themoviedb.org/3`
- **Available Services**:
    - Movies list: `GET /movie/{popular}`
    - TV shows list: `GET /tv/{popular}`
    - Images: `GET /{id}/{path}/images?language=es`

## Continuous Integration (CI) with GitHub Actions

A CI pipeline has been integrated using GitHub Actions to automatically validate the project. This
pipeline performs the following tasks:

- **Runs Unit Tests**: Verifies that all tests pass correctly.
- **Validates Code with Lint**: Ensures that the code adheres to defined style rules.
- **Runs Detekt**: Performs static code analysis to identify potential issues and maintain quality.
- **Checks Code Coverage with JaCoCo**: Generates a test coverage report and ensures that defined
  thresholds are met.
- **Generates a Debug APK**: At the end of the pipeline, a debug APK is generated and uploaded as a
  GitHub artifact, allowing for direct download and testing from the platform.

## LLibraries Used

### Jetpack Compose

- [Material Design](https://material.io/blog/jetpack-compose)
- [UI Tooling](https://developer.android.com/jetpack/compose/layouts/material?hl=es-419)
- [Activity Compose](https://developer.android.com/jetpack/compose/layouts/material?hl=es-419)
- [ViewModel Compose](https://developer.android.com/jetpack/compose/layouts/material?hl=es-419)
- [Compose UI Test](https://developer.android.com/jetpack/compose/testing?hl=es-419)
- [Navigation](https://developer.android.com/jetpack/compose/navigation?hl=es-419)

### Test

- [Mockk](https://github.com/mirtizakh/Android-Mockk)
- [Compose Test](https://developer.android.com/jetpack/compose/testing?hl=es-419)

### Others

- [Coroutines Core](https://github.com/Kotlin/kotlinx.coroutines)
- [Room](https://developer.android.com/training/data-storage/room)
- [Lifecycle ViewModel](https://github.com/androidx/androidx)
- [ktor](https://ktor.io/docs/client-create-new-application.html#create-client)
- [Hilt](https://github.com/googlecodelabs/android-hilt)