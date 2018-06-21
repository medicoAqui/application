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

consultaR.get('medico', function(req,res){
	var consulta = Consulta.find({cpfMedico: req.params.cpf});

	consulta.exec(function(err,data){
		if(err){
			res.sendStatus(400).json('Não ha consultas para este medico');
		}else{
			res.sendStatus(201).json(data);
		}

	});

});

consultaR.get('/hora',function(req,res){
	var consulta = Consulta.find({hora: req.params.hora});

	consulta.exec(function(err,data){
		if(err){
			res.sendStatus(400).json('Não ha consultas para este horario');
		}else{
			res.sendStatus(201).json(data);
		}

	});

});

consultaR.get('/data',function(req,res){
	var consulta = Consulta.find({data: req.params.data});

	consulta.exec(function(err,data){
		if(err){
			res.sendStatus(400).json('Não ha consultas para esta data');
		}else{
			res.sendStatus(201).json(data);
		}

	});
});



module.exports = consultaR; 