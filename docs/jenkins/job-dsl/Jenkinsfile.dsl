pipeline {
    agent any
    stages {
        stage('Create a job') {
            steps {
              jobDsl targets: [
                'docs/jenkins/job-dsl/*.groovy',
              ].join('\n'),
              removedJobAction: 'DELETE',
              removedViewAction: 'DELETE',
              lookupStrategy: 'SEED_JOB'
            }
        }
    }
}