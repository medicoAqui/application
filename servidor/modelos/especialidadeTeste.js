const mongoose = require('mongoose'), Schema = mongoose.Schema;

const especialidadeTesteSchema = mongoose.Schema({
    nome: String
});

module.exports = mongoose.model('EspecialidadeTeste', especialidadeTesteSchema);
