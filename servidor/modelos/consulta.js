var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var consultaSchema = new Schema({
	idMedico:{type: String,required:true},
	idPaciente:{type:String},
	data:{type:Date, required:true},
	hora:{type:String,required:true}
})

var consulta = mongoose.model('Consulta',consultaSchema);
module.exports = consulta;