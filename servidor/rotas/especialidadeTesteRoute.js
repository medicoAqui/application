var express = require('express');
var espe_Rota = express.Router();

var EspecialidadeTeste = require('../modelos/EspecialidadeTeste');

espe_Rota.get('/especialidadesTeste',function(req,res){
	var array_espe = EspecialidadeTeste.find({});

	array_espe.exec(function(err,data){
		if(err){
			res.sendStatus(500).json('Erro ao trazer as especilidades');
		}else{
			res.json(data);
		}
	});

});
