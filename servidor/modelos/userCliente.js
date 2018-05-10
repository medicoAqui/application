var mongoose = require('mongoose');
var Schema = mongoose.Schema;


var usuarioSchema = new Schema({
	name:{
		type:String,
		required:true
	},
	email:{
		type:String,
		required:true
	},
	sexo:{
		type:String,
		required: true
	},
	cpf: {
        type: Number,
        required:true,
        unique: true
    },
	password:{
		type:String,
		required:true
	},
    telefone:{
	    type:String,
        required:true
    }

});

var usuario = mongoose.model('Usuario',usuarioSchema);

module.exports = usuario;