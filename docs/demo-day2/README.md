# DAY 2
---
## Create Jenkins Job to deploy application

- #### Create NodeJS global tools
`Open Jenkins -> Manage Jenkins -> Global Tools Configuration -> Nodejs -> NodeJS installations`

![Image of Nodejs tools](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/nodejs-tools.png?raw=true)

- #### Create credential
`Manage Jenkins -> Manage Credential -> (global) -> Add credentials`

![Image of Jenkins Credential](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/jenkins-credential.png?raw=true)

- #### Create New Job
`Dashboard -> New Item`

![Image of New Job](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/new-job.png?raw=true)


- #### Add Pipeline
```
pipeline {
    agent any 
    stages {
        stage('Prepare Code') {
            steps {
                checkout([$class: "GitSCM", branches: [[name: "main"]], userRemoteConfigs: [[url: "https://github.com/sidiqputra/technoscape-demo.git"]]])
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
                    sh 'git push https://:${TOKEN}@git.heroku.com/technoscape.git HEAD:main'
                }
            }
        }
    }
}
```
- #### Deploy the code

`open the job -> build now`

- #### Automate deploy with poll scm

## Create unit test
- #### Install mocha, chai, chai-http
```
$ npm install --save mocha chai chai-http
```

- #### Edit file package.json 
```
"test": "mocha",
```

- #### Create folder `test`

- #### Create file server.js inside `test` folder
```
let chai = require("chai");
let chaiHttp = require("chai-http");
let server = require("../index.js");

//Assertion Style
chai.should();

chai.use(chaiHttp);

describe('Test GET Product', () => {

    describe("GET /product/listall", () => {
        it("GET all product", (done) => {
            chai.request(server)
                .get("/product/listall")
                .end((err, response) => {
                    response.should.have.status(200);
                    response.body.should.be.a('array');
                done();
                });
        });

        it("GET one product", (done) => {
            chai.request(server)
                .get("/product/1")
                .end((err, response) => {
                    response.should.have.status(200);
                done();
                });
        });

    });

});
```

- #### Adjust index.js to export module
```
const server = app.listen(PORT, () => {
 console.log("Server running on port " + PORT);
});

module.exports = server;
```
- #### Run unit test
```
$ npm run test
```
## Create Jenkins Multibranch
- #### Create credential github-token
`Manage Jenkins -> Manage Credential -> (global) -> Add credentials`

![Image of Credential Github](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/credential-github.png?raw=true)

- #### Create New Job
`New Item -> Multibranch Pipeline`

- #### Setup the multibranch job 

![Image of New Multibranch](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/multibranch-settings.png?raw=true)


## Integrate Github PR with Jenkins
- #### Open github repository
`Settings -> Branches -> Add rule`

![Image of github protection rules](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/github-protection-rules.png?raw=true)

- #### create smee channel
`Open` [smee.io](https://smee.io "smee.io") -> `Start a new channel`

- #### Create github webhook
`Open github.com -> Settings -> Webhooks -> Add webhook`


![Image of github webhook](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/github-webhook.png?raw=true)

- #### Install smee-client
```
$ npm install --global smee-client
```

- #### Listen smee client
```
$ smee --url https://smee.io/<your channel> --target http://localhost:8080/github-webhook/
```
- #### Open text editor and create new branch
```
$ git checkout -b feature/automation-test
```
- #### Create Jenkinsfile
```
pipeline {
    agent any
    stages {
        stage ('Checkout') {
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
```
- #### Commit changes to branch
```
$ git add -A
$ git commit -m 'add Jenkinsfile'
$ git push origin feature/automation-test
```
- #### Create New PR in github
`in this stage new PR will run unit test before we can merging the code`