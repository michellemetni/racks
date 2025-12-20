Racks – CI/CD and Kubernetes Deployment Project

Project Overview:

This project demonstrates a complete DevOps workflow for a Spring Boot backend application.
It covers containerization, automation, and orchestration using industry‑standard tools.

The project includes:

-A Spring Boot REST backend

-A PostgreSQL database

-Docker and Docker Compose for containerization

-A CI/CD pipeline implemented with GitHub Actions

-Deployment to a local Kubernetes cluster with persistent storage and health checks

Technology Stack:

-Backend: Spring Boot (Java 17)

-Database: PostgreSQL 15

-Build Tool: Maven

-Containerization: Docker

-Orchestration: Docker Compose, Kubernetes

-CI/CD: GitHub Actions

-Container Registry: GitHub Container Registry (GHCR)

-Kubernetes Cluster: Docker Desktop Kubernetes

Application Architecture:

Client
 |
 |--> Kubernetes Service (NodePort)
       |
       |--> Spring Boot Application (Deployment)
               |
               |--> PostgreSQL Database (StatefulSet + PVC)

Docker and Docker Compose:

Dockerfile:

The application is containerized using a multi‑stage Dockerfile:

-Build stage: Uses Maven to compile the Spring Boot application

-Runtime stage: Uses a lightweight Java runtime image to run the generated JAR

Docker Compose Stack:

The Docker Compose setup includes:

-A Spring Boot service

-A PostgreSQL service

-Persistent volumes for database storage

-Environment variables for configuration

Run the application with Docker Compose using:

docker compose up --build

Stop the containers using:

docker compose down


CI/CD Pipeline:

A CI/CD pipeline was implemented using GitHub Actions. The pipeline is triggered on pushes to the main branch and on pull requests.

Pipeline Stages:

Test Stage:

-Runs Maven tests

-Uses a PostgreSQL service container inside GitHub Actions

-Validates that the application can start and interact with a database

Build Stage:

-Builds the Docker image using the project Dockerfile

-Ensures the application can be successfully containerized

Registry Push Stage:

-Pushes the Docker image to GitHub Container Registry (GHCR)

-Uses GitHub’s built‑in GITHUB_TOKEN for secure authentication

-Tags images as latest and with the commit SHA

CI/CD Benefits:

-Automated and repeatable builds

-Secure handling of credentials

-No manual image creation or deployment steps

Kubernetes Deployment:

The application is deployed to a local Kubernetes cluster using Docker Desktop Kubernetes.

Kubernetes Resources:

-Application

-Deployment: Runs the Spring Boot application

-Service (NodePort): Exposes the application externally

-ConfigMap: Stores non‑sensitive configuration values

-Secret: Stores database credentials securely

-Readiness Probe: Determines when the application is ready to receive traffic

-Liveness Probe: Restarts the application if it becomes unhealthy

Database (Option A – Inside Kubernetes):

-StatefulSet: Manages the PostgreSQL database

-PersistentVolumeClaim: Ensures database data persistence

-Headless Service: Provides a stable network identity for PostgreSQL

Kubernetes Manifests Structure
k8s/
├── namespace.yaml
├── configmap.yaml
├── secret.yaml
├── postgres-hl-service.yaml
├── postgres-statefulset.yaml
├── app-deployment.yaml
├── app-service.yaml

Deploying to Kubernetes:
-Create the namespace: kubectl apply -f k8s/namespace.yaml

-Apply all Kubernetes resources: kubectl apply -f k8s/ -n racks

-Verification:

kubectl get pods -n racks

kubectl get services -n racks

kubectl get pvc -n racks

Expected results:

-Application and database pods in Running state

-PersistentVolumeClaim in Bound state

-NodePort service exposed

-Accessing the Application

The application can be accessed through the NodePort service: 

http://localhost:30080. This confirms successful deployment and external access via Kubernetes.

Configuration and Secrets:

-Application configuration is managed using Kubernetes ConfigMaps

-Sensitive values such as database credentials are stored in Kubernetes Secrets

-No credentials are committed to the repository

-The CI/CD pipeline uses GitHub’s built‑in secret mechanism

Team Members:

-Michelle Metni

-Rami Moussalli

-Jamil Nakhle
