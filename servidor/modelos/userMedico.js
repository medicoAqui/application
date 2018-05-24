var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var medicoSchema = new Schema({
	nome:{
		type:String,
		required:true
	},
	DataNascimento:{
		type:String,
		required:true
	},
	cpf:{
		type:Number,
		required:true,
		unique:true
	},
	sexo:{
		type:String,
		required:true
	},
	especializacao:{
		type:String,
		required:true
	},
	registro:{
		type:String,
		required:true,
		unique:true
	}
});

var medico = mongoose.model('Medico',medicoSchema);

module.exports = medico;