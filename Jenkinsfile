pipeline {
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Build JAR File") {
            steps {
                sh "mvn clean install"
            }
        }
        stage("Test") {
            steps {
                sh "mvn test"
            }
        }
        stage("Build Docker Image") {
            steps {
                sh "docker build -t franciscamarquez/proyecto-docker ."
            }
        }
        stage("Push Docker Image") {
            steps {
            withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dckpass')]) {
                    sh "docker login -u franciscamarquez -p ${dckpass}"
                }
                sh "docker push franciscamarquez/proyecto-docker"
            }
        }
    }
    post {
        always {
            sh "docker logout"
        }
    }
}