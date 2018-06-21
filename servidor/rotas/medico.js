var express = require('express');
var medicoRouter =  express.Router();
var Medico = require('../modelos/userMedico.js');
var Consulta = require('../modelos/consulta');

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



medicoRouter.post('/add', function(req,res){
	var novoMedico = new Medico(req.body);

	novoMedico.save(function(err, data) {
		console.log(novoMedico);

		console.log(data);

		if (err) {
			res.status(400).json(err);
		} else {
			res.status(201).json(data);
		}
	});

});

medicoRouter.post('/me',function(req,res){
  
    Medico.findOne({rcm: req.body.rcm}, function(err,data){
        console.log(data)
        if(err){
            res.status(500).send('Medico nao cadastrado');
        }else{
            res.send(data);
        }
    });
});

medicoRouter.put('/:rcm', function(req,res){
    var corpo = req.body;
    console.log(corpo);

    Medico.findByIdAndUpdate(req.params.rcm,corpo,{new: true}, function(err,data){
        if(err){
            res.status(500).send(err);
        }else{
            res.send(data)
        }
    });

});

medicoRouter.put('/consulta/:rcm', function(red,res){
    var corpo = req.body;
    console.log(corpo);

    Consulta.findByIdAndUpdate(req.params.rcm,corpo,{new: true}, function(err,data){
        if(err){
            res.status(500).send(err);
        }else{
            res.send(data)
        }
    });

});

medicoRouter.delete('/:rcm', function(req,res){
	var idUsuario = { rcm: req.params.rcm };

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