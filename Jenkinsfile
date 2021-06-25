pipeline {
    agent any
    stages {
        stage ('Build') {
            steps {
                nodejs(nodeJSInstallationName: 'node14') {
                    sh 'npm install'
                }
            }
        }
        stage('Test') {
            steps {
                nodejs(nodeJSInstallationName: 'node14') {
                    sh 'npm run test'
                }
            }
        }
    }
}