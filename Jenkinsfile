pipeline {
    agent any

    environment {
        IMAGE_NAME      = 'swastika1702/calculator'
        IMAGE_TAG       = "${env.BUILD_NUMBER}"
        APP_HOST        = 'host.docker.internal'
        // Exposes DOCKERHUB_CREDS_USR and DOCKERHUB_CREDS_PSW
        DOCKERHUB_CREDS = credentials('dockerhub-creds')
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn -B clean verify'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG -t $IMAGE_NAME:latest .'
            }
        }

        stage('Docker Push') {
            steps {
                sh '''
                    echo "$DOCKERHUB_CREDS_PSW" | \
                      docker login -u "$DOCKERHUB_CREDS_USR" --password-stdin
                    docker push $IMAGE_NAME:$IMAGE_TAG
                    docker push $IMAGE_NAME:latest
                '''
            }
        }

        stage('Deploy with Ansible') {
            steps {
                sh '''
                    ansible-playbook -i ansible/inventory.ini ansible/deploy.yml \
                      -e image_name=$IMAGE_NAME \
                      -e image_tag=$IMAGE_TAG
                '''
            }
        }

        stage('Smoke Test') {
            steps {
                sh '''
                    sleep 3
                    curl -sf "http://$APP_HOST:8090/api/add?a=2&b=3" | grep -q '"result":5'
                '''
            }
        }
    }

    post {
        always {
            sh 'docker logout || true'
        }
        success {
            echo "Deployed ${IMAGE_NAME}:${IMAGE_TAG} successfully"
        }
        failure {
            echo 'Pipeline failed - read the failing stage log above'
        }
    }
}