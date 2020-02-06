#!groovy

pipeline {
    agent {
        node {
            label 'java'
        }
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
            script {
                sh "docker build . -t jfrog-docker-reg2.bintray.io/jfrog/auto-mat:1.1.${BUILD_NUMBER} -t jfrog-docker-reg2.bintray.io/jfrog/auto-mat:latest"
            }
        }
        stage('test') {
            script {
                sh "gradle build test"
            }
        }
    }

}