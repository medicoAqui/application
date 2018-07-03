var express = require('express');
var medicoRouter =  express.Router();

var MedicoTeste = require('../modelos/medicoTeste.js');

medicoRouter.get('/medicosTeste', function(req, res) {
  var medico = MedicoTeste.find({});

  medico.exec(function(err,data){
  	if(err){
  		res.sendStatus(500);
  	}else{
  		res.json(data);
  	}
  });
});
