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
var rotaConsulta =  require('./rotas/consultaR');
var rotaEspecialidade = require('./rotas/especialidadeR');

app.use(cors());
app.use(morgan('dev'));

mongoose.connect('mongodb://medicoaqui:medicoaqui123@ds155674.mlab.com:55674/medicoaqui',function(err,db){
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
app.use('/consulta',rotaConsulta);
app.use('/especilidade',rotaEspecialidade);

//app.set('superSecret', config.secret);


app.listen(process.env.PORT || 8080, function(){
  console.log("Express server listening on port %d in %s mode", this.address().port, app.settings.env);
});

///////////////// rotas do cliente //////////////////////////////////////


///////////////////   rotas Medico //////////////////////////////////////////////////////////



module.exports = app;