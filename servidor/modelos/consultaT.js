var mongoose = require('mongoose');
var Schema = mongoose.Schema;
const AutoIncrement = require('mongoose-sequence')(mongoose);

var consultaTSchema = new Schema({

	observacao:{type:String},
	dataConsulta:{type:Date, required:true},
	hora:{type:String,required:true},
	status:{type:String, required:true },
  consultorio : { type: Schema.Types.ObjectId, ref: 'Consultorio' },
  medico : { type: Schema.Types.ObjectId, ref: 'Medico' },
  cliente : { type: Schema.Types.ObjectId, ref: 'Usuario' }

})

consultaTSchema.plugin(AutoIncrement, {inc_field: 'idConsulta'});

var consultaT = mongoose.model('ConsultaT',consultaTSchema);

module.exports = consultaT;
