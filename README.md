# Racks – CI/CD and Kubernetes Deployment Project

# 1.Project Overview:

This project demonstrates a complete DevOps workflow for a Spring Boot backend application.
It covers containerization, automation, and orchestration using industry‑standard tools.

## Project Components:

- A Spring Boot REST backend 
- A PostgreSQL database 
- Docker and Docker Compose for containerization 
- A CI/CD pipeline implemented with GitHub Actions 
- Deployment to a local Kubernetes cluster with persistent storage and health checks

# 2.Technology Stack:
- Backend: Spring Boot (Java 17)
- Database: PostgreSQL 15 
- Build Tool: Maven 
- Containerization: Docker 
- Orchestration: Docker Compose, Kubernetes 
- CI/CD: GitHub Actions 
- Container Registry: GitHub Container Registry (GHCR)
- Kubernetes Cluster: Docker Desktop Kubernetes

# 3.Application Architecture:
```
Client
 |
 |--> Kubernetes Service (NodePort)
       |
       |--> Spring Boot Application (Deployment)
               |
               |--> PostgreSQL Database (StatefulSet + PVC)
```
# 4.Docker and Docker Compose:

## 4.1 Dockerfile:

The application is containerized using a multi‑stage Dockerfile:

- Build stage: Uses Maven to compile the Spring Boot application

- Runtime stage: Uses a lightweight Java runtime image to run the generated JAR

## 4.2 Docker Compose Stack:

The Docker Compose setup includes:
- A Spring Boot service
- A PostgreSQL service 
- Persistent volumes for database storage 
- Environment variables for configuration

Run the application with Docker Compose using:
```shell
docker compose up --build
```
Stop the containers using:
```shell
docker compose down
```
# 5.CI/CD Pipeline:

The CI/CD pipeline is designed to:
- Automatically test the application on every change
- Prevent broken or unstable code from being released
- Build reproducible Docker images
- Publish Docker images only from stable code
  
## When is the pipeline triggered?

### 1. Pull Requests (PR)

The pipeline is triggered whenever a pull request is opened or updated.

- Tests are executed using a PostgreSQL service container
- A Docker image is built
- The image is NOT pushed to the registry

Pull requests are used to validate changes before merging them into the main branch. Publishing images at this stage would be unsafe.

### 2. Push to main

The pipeline is also triggered when code is pushed to the main branch.

- Tests are executed
- A Docker image is built
- The Docker image is pushed to GitHub Container Registry (GHCR)

The main branch is treated as the stable branch, so only tested and approved code is released.

## 5.1 Pipeline Stages
The pipeline is composed of three main stages:

### 1. Test Stage
- Runs on pull requests and pushes
- Starts a temporary PostgreSQL service container
- Uses health checks to wait until the database is ready
- Executes Maven tests
- Stops the pipeline immediately if tests fail

### 2. Build Stage
- Runs only if the test stage succeeds
- Builds the Docker image using Docker Buildx
- Tags the image with:
  - the commit SHA (for traceability)
  - latest (for convenience)
- Does not publish the image

### 3. Push Stage
- Runs only on pushes to the main branch
- Authenticates securely to GitHub Container Registry
- Publishes the Docker image

## 5.2 Design Decisions and Justifications

- Pull requests do not publish Docker images to avoid releasing unstable code
- The main branch represents the stable version of the application
- PostgreSQL is run as a service container to test real database integration
- Health checks are used to avoid pipeline failures
- Docker images are tagged with the commit SHA for full traceability
- Sensitive values such as database passwords are stored using GitHub Secrets
- Docker Buildx and caching are used to improve build performance


# 6.Kubernetes Deployment:

The application is deployed to a local Kubernetes cluster using Docker Desktop Kubernetes.

## 6.1 Kubernetes Resources:

- Application
- Deployment: Runs the Spring Boot application
- Service (NodePort): Exposes the application externally 
- ConfigMap: Stores non‑sensitive configuration values 
- Secret: Stores database credentials securely 
- Readiness Probe: Determines when the application is ready to receive traffic 
- Liveness Probe: Restarts the application if it becomes unhealthy

## Database Design :
we chose to do the database insinde kubernetes (Option A).
Running the database inside the Kubernetes cluster allows us to treat it as a first-class service, just like the backend.
We used a StatefulSet instead of a Deployment because databases are stateful by nature.
StatefulSets provide:
- A stable network identity (postgres-0)
- A stable persistent volume that survives pod restarts
This ensures that even if the database pod crashes, the data is not lost.

With PersistentVolumeClaims, our database data is stored outside the container lifecycle.
  So when Kubernetes recreates the PostgreSQL pod:

- The pod is new

- The data remains the same

# 7.Kubernetes Manifests Structure
```
k8s/
├── namespace.yaml
├── configmap.yaml
├── secret.yaml
├── postgres-hl-service.yaml
├── postgres-statefulset.yaml
├── app-deployment.yaml
├── app-service.yaml
```
# 8.Deploying to Kubernetes Instructions:

## 8.1 Create the namespace: 
```
kubectl apply -f k8s/namespace.yaml
```
## 8.2 Apply all Kubernetes resources: 
```
kubectl apply -f k8s/ -n racks
```
# 8.3 Verification:
```
kubectl get pods -n racks

kubectl get services -n racks

kubectl get pvc -n racks
```
# 9.Accessing the Application

The application can be accessed through the NodePort service: 

http://localhost:30080/app. This confirms successful deployment and external access via Kubernetes.

# 10.Configuration and Secrets:

- Application configuration is managed using Kubernetes ConfigMaps

- Sensitive values such as database credentials are stored in Kubernetes Secrets

- No credentials are committed to the repository

- The CI/CD pipeline uses GitHub’s built‑in secret mechanism

# 11.Team Members:

Michelle Metni

Rami Moussalli

Jamil Nakhle
