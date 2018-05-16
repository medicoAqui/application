var express = require('express');
var bodyParser= require('body-parser')
var cors = require('cors');
var morgan = require('morgan');
var mongoose = require('mongoose');
var config = require('./config');
var auth = require("./rotas/auth");


//let config = require('./config');
var app = express();


//var Usuario = require('../userCliente');
//var Medico = require('.modelos/userMedico');

var rotaCliente = require('./rotas/cliente');
var rotaMedico = require('./rotas/medico');
var rotaAcesso = require('./rotas/acesso');
//var outraRota =  require('./rotas/checarToken');

app.use(cors());
app.use(morgan('dev'));

mongoose.connect('mongodb://localhost:27017/medicoAqui',function(err,db){
  if(err) {
    console.log("erro: " + err);
  } else {
    console.log("Conectou no db");
  }
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));



app.use('/cliente',rotaCliente)
app.use('/medico',rotaMedico);
app.use('/auth', rotaAcesso);
//app.use('/to',outraRota);

//app.set('superSecret', config.secret);


app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
});

///////////////// rotas do cliente //////////////////////////////////////


///////////////////   rotas Medico //////////////////////////////////////////////////////////



module.exports = app;