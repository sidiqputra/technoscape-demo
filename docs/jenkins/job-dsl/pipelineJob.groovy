import groovy.json.JsonSlurper

def inputFile = readFileFromWorkspace("docs/jenkins/job-dsl/pipelineJob.json")
def InputJSON = new JsonSlurper().parseText(inputFile)

for (i = 0; i < InputJSON.project.size(); i++) {
   def project_name = InputJSON.project[i].name
   def herokuapp = InputJSON.project[i].herokuapp

    pipelineJob("${project_name}") {
      logRotator {
          numToKeep(10)
      }

      definition {
        cps {
          sandbox()
          script('''
pipeline {
    agent any 
    stages {
        stage('Prepare Code') {
            steps {
                checkout([$class: "GitSCM", branches: [[name: "main"]], userRemoteConfigs: [[url: "https://github.com/sidiqputra/''' + project_name + '''.git"]]])
            }
        }
        stage('Build') {
            steps {
                nodejs(nodeJSInstallationName: 'node14') {
                    sh 'npm install'
                }
            }
        }
        stage('Deploy') {
            steps {
                withCredentials([string(credentialsId: 'heroku-token', variable: 'TOKEN')]) {
                    sh 'git push https://:${TOKEN}@git.heroku.com/''' + herokuapp + '''.git HEAD:main'
                }
            }
        }
    }
}
'''.stripIndent())
        }
      }
    }
  }