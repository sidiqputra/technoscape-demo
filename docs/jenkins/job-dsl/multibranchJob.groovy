import groovy.json.JsonSlurper

def inputFile = readFileFromWorkspace("docs/jenkins/job-dsl/pipelineJob.json")
def InputJSON = new JsonSlurper().parseText(inputFile)


for (i = 0; i < InputJSON.project.size(); i++) {
def project_name = InputJSON.project[i].name

    multibranchPipelineJob("PR-${project_name}") {
        branchSources {
            github {
                id("${project_name}")
                scanCredentialsId('github-token')
                repoOwner('msidiqputra')
                repository("${project_name}")
                buildOriginBranch(false)
                buildOriginBranchWithPR(true)
                buildForkPRMerge(false)
                buildOriginPRMerge(true)
            }
        }

        configure {
            def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits

            traits << 'org.jenkinsci.plugins.github__branch__source.OriginPullRequestDiscoveryTrait' {
                strategyId('1')
            }
            traits << 'org.jenkinsci.plugins.github__branch__source.BranchDiscoveryTrait' {
                strategyId('1')
            }

            traits << 'jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait' {
                includes('PR-*')
                excludes('')
            }
            def factory = it / factory(class: 'com.cloudbees.workflow.multibranch.CustomBranchProjectFactory')
            factory << definition(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition') {
                scriptPath('Jenkinsfile')
            }

        }
    }
}