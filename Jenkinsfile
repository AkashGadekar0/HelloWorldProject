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
                echo 'Building project with Maven'
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image'
                bat "docker build -t %DOCKER_IMAGE% ."
            }
        }

        // stage('Push Docker Image') {
        //     steps {
        //         echo 'Pushing Docker image'
        //         bat "docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%"
        //         bat "docker push %DOCKER_IMAGE%"
        //     }
        // }
        stage('Push Docker Image') {
                steps {
                    echo 'Pushing Docker image'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        bat "echo %DOCKERHUB_PASSWORD% | docker login -u %DOCKERHUB_USERNAME% --password-stdin"
                        bat "docker push %DOCKER_IMAGE%"
                    }
                }
            }


        stage('Deploy with Helm') {
            steps {
                echo 'Deploying with Helm'
                bat "helm upgrade --install servlet-app %CHART_PATH%"
            }
        }
    }
}
