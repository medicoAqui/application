var express = require('express');
var medicoRouter =  express.Router();

var Medico = require('../modelos/userMedico.js');
var Consulta = require('../modelos/consulta');
var Especialidade = require('../modelos/especialidade');
var Med_Espe = require('../modelos/medico_especialidades');
var Consultorio = require('../modelos/consultorio');
var Endereco = require('../modelos/endereco');

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

// aqui adiciona medico , cria a especialidade e a tebela medico especialidade

medicoRouter.post('/add', function(req,res){

    var especialidade;


    Especialidade.findOne({nomeEspecialidade: req.body.nomeEspecialidade }, function(err,data){
        console.log("_______________ENCONTRATO REGISTRO ABAIXO_________________");
        console.log(data);
        console.log("_______________ENCONTRATO REGISTRO ACIMA_________________");
        if(data == undefined || err){

          console.log("_______________FALHA AO CADASTRAR MEDICO (ESPCIALIDADE NAO ENCONTRADA)_________________");
          res.status(400).json("Especialidade nao encontrada");
        }
        else{
          especialidade = data;
          var consultorio;
          Consultorio.findOne({idConsultorio: req.body.idConsultorio }, function(err,data2){
              if(data2 == undefined || err){
                res.status(400).json("Consultorio nao encontrado");
              }else{
                consultorio = data2;
                var novoMedico = new Medico(req.body);

                novoMedico.especialidades.push(especialidade);
                novoMedico.consultorio = consultorio;

                novoMedico.save(function(err, data) {
                    console.log(data);

                    if (err) {
                        res.status(400).json(err);
                    } else {
                        res.status(201).json(data);
                    }
                });
              }
          });
          console.log("_______________MEDICO REGISTRADO (ESPECIALIDADE JA EXISTE)_________________");
        }
    });
});


medicoRouter.post('/addEspecialidadeAoMedico', function(req,res){


          Medico.findOne({crm: req.body.crm }, function(err,data){
            if (err) {
                res.status(400).json(err);
                console.log("_______________Falha na associacao de especialicadade ao medico, medico nao encontrado_________________");
            } else {
              var especialidade;
              Especialidade.findOne({nomeEspecialidade: req.body.nomeEspecialidade }, function(err,data2){
                  console.log("_______________ENCONTRATO REGISTRO ABAIXO_________________");
                  console.log(data2);
                  console.log("_______________ENCONTRATO REGISTRO ACIMA_________________");
                  if(data == undefined || err){
                    console.log("_______________FALHA AO CONSULTAR ESPECIALIDADE (ESPCIALIDADE NAO ENCONTRADA)_________________");
                    res.status(400).json(err);
                  }
                  else{
                    var especialidade = data2;
                    console.log(especialidade);
                    console.log("_______________REGISTRANDO ESPECIALIDADE AO MEDICO  _________________");
                    data.especialidades.push(especialidade);
                    data.save();
                    res.status(201).json(data);
                    console.log("_______________TUDO OK_________________");
                  }
             });
           }
        });
});

medicoRouter.post('/medicosByEspecialidade',function(req,res){

  var especialidade;

  Especialidade.findOne({nomeEspecialidade: req.body.nomeEspecialidade }, function(err,data){
    console.log(data);

    if(data == undefined || err){
        res.status(400).json(err);
    }
    else{
      var medico = Medico.find({especialidades :data._id});
      medico.exec(function(err,data2){
        if(err) {
                res.sendStatus(400).json('Medico nao encontrado no sistema');
        }else{
          res.send(data2);
        }
      });
    }
  });
});

medicoRouter.post('/EspecialidadesByCidadeAndUF',function(req,res){

  var endereco = Endereco.find({cidade: req.body.cidade, uf: req.body.uf });
  console.log(endereco.rua);

  endereco.exec(function(err,data){
      console.log(data)
      if(err){
          res.sendStatus(400).json('Endereco nao encontrado no sistema');
      }else{
        var consultorio = Consultorio.find({endereco :data});
        consultorio.exec(function(err,data2){
          console.log(data2)
          if(err) {
                  res.sendStatus(400).json('Consultorio nao encontrado no sistema');
          }else{
            var medico = Medico.find({consultorio :data2},{$all:{especialidades:1}});
            medico.exec(function(err,data3){
                var medic = data3;
              console.log(data3 + " aqui é o data 3");
              if(err) {
                      res.sendStatus(400).json('Medico nao encontrado no sistema');
              }else{
                var especialidades = Especialidade.find({_id:{$all:data3.especialidades}});
                especialidades.exec(function(err,data4){
                    console.log(data4);
                    if(err){
                        res.sendStatus(400).json('sem especialidade');
                    }else{
                        res.send(data4);
                    }

                });



                //console.log(medic.name+ " aqui é o medic");
                //res.send(data3.especialidades);
              }
            });
          }
        });
      }
  });
});

medicoRouter.post('/testeE', function(req,res){
    var eu;

    Endereco.find({cidade: req.body.cidade, uf: req.body.uf }).exec(function(err,endereco){
        console.log(endereco+ " parte1");
        if(err){
            res.senStatus(400).send("erro no endereço");
        }else{
            Consultorio.find({endereco :endereco}).exec(function(err,consultorio){
                console.log(consultorio+" parte 2");
                if(err){
                    res.sendStatus(400).send("consultorio nao encontrado");

                }else{
                    Medico.find({consultorio :consultorio}).populate('especialidades').exec(function(err,medico){
                        console.log(medico+ "parte3");
                        console.log(toString(medico.name));
                       // console.log(medico.name + " parte3.1");

                        if(err){
                            res.sandStatus(400).send("medico nao encontrado");

                        }else{
                            var outro = medico.especialidades;
                            var es = new Especialidade();
                            es = medico.especialidades;
                            var m = new Medico();
                            m = medico;

                            //console.log('The author is %s',medico.especialidades.nomeEspecialidade);
                           console.log(m+ " mmmmmmmm");
                                                      console.log((Medico) m.cpf+ " cccccccccccc");

                            console.log(toString(outro));

                                                        
                            res.send(medico);
                        }
                                                    console.log(medico.especialidades);
                                                    console.log(Medico.especialidades);



                    });
                }

            });
        }
    });
    console.log(eu);

});

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
