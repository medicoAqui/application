var express = require('express');
var consultaR = express.Router();

var Consulta = require('../modelos/consulta');

consultaR.get('/consultas',function(req,res){
	var consultas = Consulta.find({});

	consultas.exec(function(err,data){
		if ( err ){
			res.sendStatus(500);
		}else{
			res.json(data);
		}
	});

});

consultaR.get('medico/id')
module.exports = consultaR; 