var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var UserCliente = require('../modelos/userCliente.js');
var jwt = require('jsonwebtoken');
var app = express();
var config = require('../config');


router.post('/authenticate', function(req, res) {

    UserCliente.findOne({cpf: req.body.cpf}, function (err, user) {
        console.log(req.body.cpf );
        console.log(user);

        if (err) {
            throw err;
        }

        if (!user) {
            res.json({success: false, message: 'Usuario não encontrado'});
        } else if (user) {


            if (user.password != req.body.password) {
                res.json({success: false, message: 'Senha Invalida'});
            } else {

                // cria o token
                var token = jwt.sign(user, 'schotpilgrim', {
                    expiresIn: 86400 // expires in 24 hours
                });

                res.json({
                    usuario: user,
                    success: true,
                    message: 'Aqui o token!',
                    token: token
                });
            }
        }
    });
});

router.get('/logout', function(req, res) {
    res.status(200).send({  token: null });
});


module.exports = router;


