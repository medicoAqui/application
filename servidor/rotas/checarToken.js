var express = require("express");
var bodyParser = require("body-parser");
var jwt = require("jwt-simple");
var auth = require("./auth.js");
var users = require("../modelos/userCliente");
var cfg = require("../config.js");
var app = express();
var router = express.Router();


router.post("/token", function(req, res) {
  if (req.body.cpf && req.body.password) {
    var cpf = req.body.cpf;
    var password = req.body.password;
    var user = users.find(function(u) {
      return u.cpf === cpf && u.password === password;
    });
    if (user) {
      var payload = {id: user.id};
      var token = jwt.encode(payload, cfg.jwtSecret);
      res.json({token: token});
    } else {
      res.sendStatus(401);
    }
  } else {
    res.sendStatus(401);
  }
});



module.exports = router;