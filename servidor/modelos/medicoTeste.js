const EspecialidadeTeste = require('../modelos/EspecialidadeTeste.js');
const mongoose = require('mongoose'), Schema = mongoose.Schema;

const medicoTesteSchema = mongoose.Schema({
    name: String,
	  especialidades : [{ type: Schema.Types.ObjectId, ref: 'EspecialidadeTeste' }]
});

module.exports = mongoose.model('MedicoTeste', medicoTesteSchema);
