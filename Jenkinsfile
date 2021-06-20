pipeline {
    agent any 
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