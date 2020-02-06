#!groovy

pipeline {
    agent {
        node {
            label 'java'
        }
    }
    tools {
        gradle 'gradle-6.0.1'
        jdk 'jdk-11'
    }
    stages {
        stage('Clean workspace') {
            steps {
                cleanWs()
            }
        }
        stage('Git checkout') {
            steps {
                checkout scm
            }
        }
        stage('docker build') {
            steps {
                script {
                    sh "docker build . -t jfrog-docker-reg2.bintray.io/jfrog/auto-mat:1.1.${BUILD_NUMBER} -t jfrog-docker-reg2.bintray.io/jfrog/auto-mat:latest"
                }
            }
        }
        stage('test') {
            steps {
                script {
                    sh "gradle build test"
                }
            }
        }
    }
    post {
        always {
            junit 'build/reports/**/*'
        }
    }

}