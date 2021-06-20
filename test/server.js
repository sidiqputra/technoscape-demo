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