# DAY 2
## Create Jenkins Job to deploy application
### Create NodeJS global tools
Manage Jenkins -> Global Tools Configuration -> Nodejs -> NodeJS installations
![Image of Nodejs tools](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/nodejs-tools.png?raw=true)
### Create credential
Manage Jenkins -> Manage Credential -> (global) -> Add credentials
![Image of Jenkins Credential](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/jenkins-credential.png?raw=true)
### Create New Job
Dashboard -> New Item
![Image of New Job](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/new-job.png?raw=true)
### Add Pipeline
```
pipeline {
    agent any 
    stages {
        stage('Prepare Code') {
            steps {
                checkout([$class: "GitSCM", branches: [[name: "main"]], userRemoteConfigs: [[url: "https://github.com/serdaucok/technoscape.git"]]])
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
## Create unit test
### Install mocha, chai, chai-http
```
$ npm install --save mocha chai chai-http
```
### Edit file package.json 
```
"test": "mocha",
```
### Create folder `test`
### Create file server.js inside `test` folder
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
### Adjust index.js to export module
```
const server = app.listen(PORT, () => {
 console.log("Server running on port " + PORT);
});

module.exports = server;
```
### Run unit test
```
$ npm run test
```
## Create Jenkins webhook smee channel
### Open https://smee.io/ -> Start a new channel
### Install smee-client
```
$ npm install --global smee-client
```
### Listen smee client
```
$ smee --url https://smee.io/<your channel> --target http://localhost:8080/github-webhook/
```
## Create Jenkins Multibranch
### Create New Job
New Item -> Multibranch Pipeline
### Setup the job multibranch job 
![Image of New Multibranch](https://github.com/sidiqputra/technoscape-demo/blob/main/docs/images/mutibranch-settings.png?raw=true)
## Integrate Github PR with Jenkins

# BONUS
## Automate Job Creation (Job Seeder)

- https://github.com/iqraabdulrauf/api-testing-with-mocha-chai/blob/master/index.js