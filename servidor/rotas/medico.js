﻿var express = require('express');
var medicoRouter =  express.Router();

var Medico = require('../modelos/userMedico.js');
var Consulta = require('../modelos/consulta');
var Especialidade = require('../modelos/especialidade');
var Med_Espe = require('../modelos/medico_especialidades');

medicoRouter.use(function(req, res, next) {

    // log each request to the console
    console.log(req.method, req.url);

    // continue doing what we were doing and go to the route
    next();
});

// define the home page route
medicoRouter.get('/medicos', function(req, res) {
  var medico = Medico.find({});

  medico.exec(function(err,data){
  	if(err){
  		res.sendStatus(500);
  	}else{
  		res.json(data);
  	}
  });
});

medicoRouter.get('/:id',function(res,req){
	var medico = Medico.find({_id: req.param.id});

	medico.exec(function(err,data){
		if(err) {
            res.sendStatus(400).json('Medico nao encontrado no sistema');
        }else{
			res.json(data);
		}
	});
});

/// aqui adiciona medico , cria a especialidade e a tebela medico especialidade

medicoRouter.post('/add', function(req,res){

    Especialidade.findOne({nomeEspecialidade: req.body.nomeEspecialidade }, function(err,data){
        console.log(data)
        if(err){
          var especialidade = new Especialidade({ nomeEspecialidade: req.body.nomeEspecialidade });

          especialidade.save(function(err,data) {

            if (err) {
                res.status(400).json(err);
            } else {
                res.status(201).json(data);
            }
          });
        }
    });


    var novoMedico = new Medico(req.body);

    novoMedico.especialidades.push(req.body.nomeEspecialidade);

    novoMedico.save(function(err, data) {

        console.log(data);

        if (err) {
            res.status(400).json(err);
        } else {
            res.status(201).json(data);
        }
    });


});
// Não funciona
/*
medicoRouter.delete('/delete', function(req,res){
	var idMedico = { crm: req.params.crm };

	Medico.remove(idMedico, function(err, data) {
		if (err) {
			res.status(400).json(err);
		} else {
			res.json(data);
		}
	});

});
*/
medicoRouter.post('/me',function(req,res){

    Medico.findOne({crm: req.body.rcm}, function(err,data){
        console.log(data)
        if(err){
            res.status(500).send('Medico nao cadastrado');
        }else{
            res.send(data);
        }
    });
});

medicoRouter.put('/:crm', function(req,res){
    var corpo = req.body;
    console.log(corpo);

    Medico.findByIdAndUpdate(req.params.crm,corpo,{new: true}, function(err,data){
        if(err){
            res.status(500).send(err);
        }else{
            res.send(data)
        }
    });

});

medicoRouter.put('/consulta/:crm', function(red,res){
    var corpo = req.body;
    console.log(corpo);

    Consulta.findByIdAndUpdate(req.params.crm,corpo,{new: true}, function(err,data){
        if(err){
            res.status(500).send(err);
        }else{
            res.send(data)
        }
    });

});

medicoRouter.delete('/:crm', function(req,res){
	var idUsuario = { crm: req.params.crm };

	Medico.remove(idUsuario, function(err, data) {
		if (err) {
			res.status(400).json(err);
		} else {
			res.json(data);
		}
	});

});

medicoRouter.post('/consulta',function(req,res){

	var consulta = new Consulta(req.body);

	consulta.save(function(err, data) {
		console.log(consulta);

		console.log(data);

		if (err) {
			res.status(400).json(err);
		} else {
				res.status(201).json(data);
			}

	});


});


module.exports = medicoRouter;
