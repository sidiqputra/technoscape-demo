pipeline {
    agent any
    checkout scm 
    stages {
        stage('Test') {
            steps {
                nodejs(nodeJSInstallationName: 'node14') {
                    sh 'npm install'
                    sh 'npm run test'
                }
            }
        }
    }
}