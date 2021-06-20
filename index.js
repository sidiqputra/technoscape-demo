var PORT = process.env.PORT || 5000;
var express = require("express");
var Products = require("./product.json");
var app = express();

app.get("/product/listall", (req, res, next) => {
    res.json(Products);
});

app.get("/product/:id", (req, res, next) => {
    console
    res.json(Products.find((product) => {
        return +req.params.id == product.id
    }))
});

app.listen(PORT, () => {
    console.log("Server running on port " + PORT);
});