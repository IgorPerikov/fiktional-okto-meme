#!/bin/bash
minikube start --memory=5000
kubectl create -f pg-deployment.yaml
kubectl create -f oktomeme-deployment.yaml
