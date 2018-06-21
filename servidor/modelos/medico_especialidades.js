var mongoose = require('mongoose');
var Schema =  mongoose.Schema;

var medico_especSchema = new Schema({

	crmMedico:{
		type: String,
		required: true
	},

	idEspecialidade:{
		type:String,
		required: true
	}

});

var modelo = mongoose.model('Medico_Especialidades', medico_especSchema);

module.exports = modelo;