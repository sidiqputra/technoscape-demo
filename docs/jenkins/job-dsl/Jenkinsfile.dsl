pipeline {
    agent any
    stages {
        stage('Create a job') {
            steps {
              jobDsl targets: [
                'pipelineJob.groovy',
              ].join('\n'),
              removedJobAction: 'DELETE',
              removedViewAction: 'DELETE',
              lookupStrategy: 'SEED_JOB'
            }
        }
    }
}