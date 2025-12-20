# Racks – CI/CD and Kubernetes Deployment Project

# 1.Project Overview:

This project demonstrates a complete DevOps workflow for a Spring Boot backend application.
It covers containerization, automation, and orchestration using industry‑standard tools.

# 1.1 Project Components:

-A Spring Boot REST backend

-A PostgreSQL database

-Docker and Docker Compose for containerization

-A CI/CD pipeline implemented with GitHub Actions

-Deployment to a local Kubernetes cluster with persistent storage and health checks

# 2.Technology Stack:

-Backend: Spring Boot (Java 17)

-Database: PostgreSQL 15

-Build Tool: Maven

-Containerization: Docker

-Orchestration: Docker Compose, Kubernetes

-CI/CD: GitHub Actions

-Container Registry: GitHub Container Registry (GHCR)

-Kubernetes Cluster: Docker Desktop Kubernetes

# 3.Application Architecture:

Client
 |
 |--> Kubernetes Service (NodePort)
       |
       |--> Spring Boot Application (Deployment)
               |
               |--> PostgreSQL Database (StatefulSet + PVC)

# 4.Docker and Docker Compose:

# 4.1 Dockerfile:

The application is containerized using a multi‑stage Dockerfile:

-Build stage: Uses Maven to compile the Spring Boot application

-Runtime stage: Uses a lightweight Java runtime image to run the generated JAR

# 4.2 Docker Compose Stack:

The Docker Compose setup includes:

-A Spring Boot service

-A PostgreSQL service

-Persistent volumes for database storage

-Environment variables for configuration

Run the application with Docker Compose using:

docker compose up --build

Stop the containers using:

docker compose down

# 5.CI/CD Pipeline:

A CI/CD pipeline was implemented using GitHub Actions. The pipeline is triggered on pushes to the main branch and on pull requests.

# 5.1 Pipeline Stages:

# 5.1.1 Test Stage:

-Runs Maven tests

-Uses a PostgreSQL service container inside GitHub Actions

-Validates that the application can start and interact with a database

# 5.1.2 Build Stage:

-Builds the Docker image using the project Dockerfile

-Ensures the application can be successfully containerized

# 5.1.3 Registry Push Stage:

-Pushes the Docker image to GitHub Container Registry (GHCR)

-Uses GitHub’s built‑in GITHUB_TOKEN for secure authentication

-Tags images as latest and with the commit SHA

# 6.Kubernetes Deployment:

The application is deployed to a local Kubernetes cluster using Docker Desktop Kubernetes.

# 6.1 Kubernetes Resources:

-Application

-Deployment: Runs the Spring Boot application

-Service (NodePort): Exposes the application externally

-ConfigMap: Stores non‑sensitive configuration values

-Secret: Stores database credentials securely

-Readiness Probe: Determines when the application is ready to receive traffic

-Liveness Probe: Restarts the application if it becomes unhealthy

# Database (Option A – Inside Kubernetes):

-StatefulSet: Manages the PostgreSQL database

-PersistentVolumeClaim: Ensures database data persistence

-Headless Service: Provides a stable network identity for PostgreSQL

# 7.Kubernetes Manifests Structure

k8s/
├── namespace.yaml
├── configmap.yaml
├── secret.yaml
├── postgres-hl-service.yaml
├── postgres-statefulset.yaml
├── app-deployment.yaml
├── app-service.yaml

# 8.Deploying to Kubernetes:

# 8.1 Create the namespace: 

kubectl apply -f k8s/namespace.yaml

# 8.2 Apply all Kubernetes resources: 

kubectl apply -f k8s/ -n racks

# 8.3 Verification:

kubectl get pods -n racks

kubectl get services -n racks

kubectl get pvc -n racks

# 9.Accessing the Application

The application can be accessed through the NodePort service: 

http://localhost:30080. This confirms successful deployment and external access via Kubernetes.

# 10.Configuration and Secrets:

-Application configuration is managed using Kubernetes ConfigMaps

-Sensitive values such as database credentials are stored in Kubernetes Secrets

-No credentials are committed to the repository

-The CI/CD pipeline uses GitHub’s built‑in secret mechanism

# 11.Team Members:

-Michelle Metni

-Rami Moussalli

-Jamil Nakhle
