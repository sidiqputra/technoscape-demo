pipeline {
    agent any
    stages {
        stage('Create a job') {
            steps {
              jobDsl targets: [
                '*.groovy',
              ].join('\n'),
              removedJobAction: 'DELETE',
              removedViewAction: 'DELETE',
              lookupStrategy: 'SEED_JOB'
            }
        }
    }
}