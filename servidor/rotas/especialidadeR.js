var express = require('express');
var espe_Rota = express.Router();

var Especialidade = require('../modelos/especialidade');


function getNextSequenceValue(sequenceName){

   var sequenceDocument = Especilidade.findAndModify({
      query:{_id: sequenceName },
      update: {$inc:{sequence_value:1}},
      new:true
   });

   return sequenceDocument.sequence_value;
}

espe_Rota.get('/especialidades',function(req,res){
	var array_espe = Especialidade.find({});


	array_espe.exec(function(err,data){
		if(err){
			res.sendStatus(500).json('Erro ao trazer as especilidades');
		}else{
			res.json(data);
		}
	});

});

espe_Rota.post('/add',function(req,res){
	var especilidade = new Especialidade(req.body);

	especilidade.save(function(err,data){
		if( err ){
			res.sendStatus(400).json('Especialidade ja existe no sistema');
		}else{
			res.status(201).json(data);
		}
	})
});



espe_Rota.post('/add2',function(req,res){

  var especialidade;

  Especialidade.findOne({nomeEspecialidade: req.body.nomeEspecialidade }, function(err,data){
    console.log("_______________ENCONTRATO REGISTRO ABAIXO_________________");
    console.log(data);
    console.log("_______________ENCONTRATO REGISTRO ACIMA_________________");
    if(data == undefined || err){
      console.log("_______________ESPECIALIDADE NAO CADASTRADA NO SISTEMA_________________");
      especialidade = new Especialidade({ nomeEspecialidade: req.body.nomeEspecialidade });
      especialidade.save(function(err,data) {
        if (err) {
            res.status(400).json(err);
        } else {
            res.status(201).json(data);
        }
      });
    }
    else{
      console.log("_______________ESPECIALIDADE JA CADASTRADA NO SISTEMA_________________");
      res.status(400).json(err);
    }
  });
});


module.exports = espe_Rota;
