pipeline {
    agent any 
    stages {
        stage('Prepare Code') {
            steps {
                checkout scm
            }
        }
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