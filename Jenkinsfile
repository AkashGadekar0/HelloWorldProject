pipeline {
    agent any

    environment {
        IMAGE_NAME = "akashgadekar/java-servlet-app"
        IMAGE_TAG = "4"  
        DOCKER_IMAGE = "${IMAGE_NAME}:${IMAGE_TAG}"
        CHART_PATH = "java-servlet-app"
    }

    tools {
        maven 'maven-project'
    }

    stages {
        stage('Build Artifact') {
            steps {
                sh 'mvn clean package '
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Deploy with Helm') {
            steps {
                sh "helm upgrade --install servlet-app ${CHART_PATH}"
            }
        }
    }
}
