var express = require('express');
var medicoRouter =  express.Router();

var Medico = require('../modelos/userMedico.js');
var Consulta = require('../modelos/consulta');
var Especialidade = require('../modelos/especialidade');
var Consultorio = require('../modelos/consultorio');


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

medicoRouter.post('/medicoByCrm',function(req,res){

  var medico = Medico.findOne({crm: req.body.crm });
  medico.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{
          res.send(data);
      }
  });
});

medicoRouter.post('/medicoByEmail',function(req,res){

  var medico = Medico.findOne({email: req.body.email });
  medico.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{
          res.send(data);
      }
  });
});

medicoRouter.post('/medicoBy_id',function(req,res){

  var medico = Medico.findOne({_id: req.body._id });
  medico.exec(function(err,data){
      console.log(data)
      if(err){
          res.status(500).send(err);
      }else{
          res.send(data);
      }
  });
});
// aqui adiciona medico , cria a especialidade e a tebela medico especialidade

medicoRouter.post('/add', function(req,res){

  var novoMedico = new Medico( req.body);

  novoMedico.save(function(err, data){
    if (err){
      res.sendStatus(400).send(err);
    }else{
      res.send(data);
    }
  })

    
});




medicoRouter.post('/medicosByEspecialidade',function(req,res){

  Medico.find({especialidade: req.body.especialidade}).exec(function(err,data){
    if( err){
      res.sendStatus(400).send(err);
    }else{
      res.send(data);
    }
  });
});

medicoRouter.post('/medicosByEspecialidadeAndEstadoCidade',function(req,res){
  var jobQueries = [];
  Especialidade.findOne({nomeEspecialidade: req.body.nomeEspecialidade}, function(err,espec){
    if(espec == undefined || err){
        res.status(400).json('Nao foi encontrado endereco registrado para este estado e cidade' );
    }
    else{
        var endereco  = Endereco.find({cidade: req.body.cidade, uf:req.body.uf });
        endereco.then(function(ends){
        console.log(ends);

          ends.forEach(function(end) {
            var consultorioFind = Consultorio.find({endereco :end._id});
            consultorioFind.then(function(consults){
              console.log(espec._id);

              consults.forEach(function(consult) {
                jobQueries.push(Medico.find({consultorio:consult._id, especialidades: espec._id}));
              });
              return Promise.all(jobQueries );
              }).then(function(listOfJobs) {
                  res.send(listOfJobs);
              }).catch(function(error) {
                  res.status(500).send('one of the queries failed', error);
              });
          });

      });
    }
  });
});

medicoRouter.post('/TmedicosByEspecialidadeAndEstadoCidade',function(req,res){

  Consultorio.find({cidade: req.body.cidade, uf: req.body.uf}).exec(function(err, consu){
    if( err){
      res.send(err);
    }else{
      Medico.find({especialidade: req.body.especialidade, consultorio: consu}).exec(function(err,med){
        if(err){
          res.send('erro no medico');
        }else{
          res.send(med);
        }
      });
    }
  });

});




medicoRouter.put('/:id', function(req,res){
    var corpo = req.body;
    console.log(corpo);

    Medico.findByIdAndUpdate(req.params.id,corpo,{new: true}, function(err,data){
        if(err){
            res.status(500).send(err);
        }else{
            res.send(data)
        }
    });

});


medicoRouter.delete('/:id', function(req,res){
	var idUsuario = { _id: req.params.id };

	Medico.remove(idUsuario, function(err, data) {
		if (err) {
			res.status(400).json(err);
		} else {
			res.json(data);
		}
	});

});




module.exports = medicoRouter;
