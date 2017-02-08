/*var mongoose = require('mongoose')
, Schema = mongoose.Schema
, ObjectId = Schema.ObjectId;

var UsuarioSchema = new Schema({
    'livro': {type: ObjectId, ref: 'Livro'},
    'capitulo': { type: Number, min: 0, max: 9999 },
    'pagina': { type: Number, min: 0, max: 99999 },
    'assunto': String,
    'observacao': String
});

module.exports = mongoose.model('Referencia', UsuarioSchema);*/