var express = require('express');
var endereco_Rota = express.Router();

var Endereco = require('../modelos/endereco');


function getNextSequenceValue(sequenceName){

   var sequenceDocument = Endereco.findAndModify({
      query:{_id: sequenceName },
      update: {$inc:{sequence_value:1}},
      new:true
   });

   return sequenceDocument.sequence_value;
}

endereco_Rota.get('/enderecos',function(req,res){
	var array_enderecos = Endereco.find({});

	array_enderecos.exec(function(err,data){
		if(err){
			res.sendStatus(500).json('Erro ao trazer os enderecos');
		}else{
			res.json(data);
		}
	});

});

endereco_Rota.post('/add',function(req,res){
	var endereco = new Endereco(req.body);

	endereco.save(function(err,data){
		if( err ){
			res.sendStatus(400).json('Endereco ja existe no sistema');
		}else{
			res.status(201).json(data);
		}
	})
});


module.exports = endereco_Rota;
