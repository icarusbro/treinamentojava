//var Livro = require('mongoose').model('Livro');

module.exports = {
    cadastrarLivro: function (req,res,next) {
        /*var livro = new Livro();
        livro.nome = req.query.nome;
        livro.autor = req.query.autor;
        livro.save(function (err) {
            if(err) return res.status(500).send();
            return res.send(livro);
        })*/
    },
    consultaLivrosAll: function (req,res,next) {
        /*Livro.find({}).exec(function (err,livros) {
            if(err) return res.status(500).send();
            return res.send(livros);
        })*/
    }
}