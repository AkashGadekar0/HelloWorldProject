pipeline {
    agent any

    environment {
        IMAGE_NAME = "akashgadekar/java-servlet-app"
        IMAGE_TAG = "${BUILD_NUMBER}"
        DOCKER_IMAGE = "${IMAGE_NAME}:${IMAGE_TAG}"
        CHART_PATH = "java-servlet-app"
        KUBECONFIG = 'C:/Users/akgadekar/.kube/config'
    }

    tools {
        maven 'maven-project'
    }

    stages {
        stage('Build Artifact') {
            when {
                branch 'main'
                branch 'b2'
            }
            steps {
                echo 'Building project with Maven'
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            when {
                branch 'main'
                branch 'b2'
            }
            steps {
                echo 'Building Docker image: ${DOCKER_IMAGE}'
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
                when {
                    branch 'main'
                }
                steps {
                    echo 'Pushing Docker image: ${DOCKER_IMAGE}'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        bat "echo %DOCKERHUB_PASSWORD% | docker login -u %DOCKERHUB_USERNAME% --password-stdin"
                        bat "docker push %DOCKER_IMAGE%"
                    }
                }
            }


        stage('Deploy with Helm') {
            when {
                branch 'main'
            }
            steps {
                echo 'Deploying with Helm'
                bat "helm upgrade --install servlet-app ${CHART_PATH} --set image.repository=${IMAGE_NAME} --set image.tag=${IMAGE_TAG}"
            }
        }
    }
}
