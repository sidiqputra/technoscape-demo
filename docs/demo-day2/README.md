# DAY 2
## Create Jenkins pipeline to deploy application
### Create NodeJS global tools
Manage Jenkins -> Global Tools COnfiguration -> Nodejs -> NodeJS installations
![Image of Nodejs tools](https://github.com/sidiqputra/docs/images/nodejs-tools.png)

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
## Create Jenkins Multibranch
## Integrate Github PR with Jenkins

# BONUS
## Automate Job Creation (Job Seeder)

- https://github.com/iqraabdulrauf/api-testing-with-mocha-chai/blob/master/index.js