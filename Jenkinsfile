pipeline {
    agent any

    environment {
        IMAGE_NAME = "akashgadekar/java-servlet-app"
        IMAGE_TAG = "${BUILD_NUMBER}"  // or use GIT_COMMIT if needed
        DOCKER_IMAGE = "${IMAGE_NAME}:${IMAGE_TAG}"
        CHART_PATH = "java-servlet-app"
        REGISTRY_CREDENTIALS = "dockerhub-creds"
    }

    tools {
        maven 'maven-project'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Artifact') {
            steps {
                echo "Building project with Maven..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image: ${DOCKER_IMAGE}"
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: "${REGISTRY_CREDENTIALS}",
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push ${DOCKER_IMAGE}
                        docker logout
                    '''
                }
            }
        }

        stage('Deploy with Helm') {
            steps {
                echo "Deploying Helm chart using image: ${DOCKER_IMAGE}"
                sh """
                    helm upgrade --install servlet-app ${CHART_PATH} \
                      --namespace default \
                      --set image.repository=${IMAGE_NAME} \
                      --set image.tag=${IMAGE_TAG} \
                      --set image.pullPolicy=Always
                """
            }
        }
    }
}
