var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var medicoSchema = new Schema({
	name:{
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
	email:{
		type:String,
		required:true
	},
	crm:{
		type:String,
		required:true,
		unique:true
	},
	especialidade:{
		type:String,
		required: true
	},
	
	consultorio : { type: Schema.Types.ObjectId, ref: 'Consultorio'},
	
});

var medico = mongoose.model('Medico',medicoSchema);

module.exports = medico;
