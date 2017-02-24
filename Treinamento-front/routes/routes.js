var livro = require("../controllers/livro-controller");
var referencia = require("../controllers/referencia-controller");

module.exports = function(app, passport) {
    
    app.route('/')
    .get(function (req,res) {
        res.render('inicio.ejs');
    });
    
     app.route('/cadastroLivro')
    .get(function (req,res) {
        res.render('cadastroLivro.ejs');
    });
    
    app.route('/cadastroRef')
    .get(function (req,res) {
        res.render('cadastroRef.ejs');
    });

    app.route('/cadastroAluno')
    .get(function (req,res) {
        res.render('cadastroAluno.html');
    });
    app.route('/cadastroProfessor')
    .get(function (req,res) {
        res.render('cadastroProfessor.html');
    });
    
    app.route('/pesquisaByLivro')
    .get(function (req,res) {
        res.render('pesquisaByLivro.ejs');
    });
    
    app.route('/pesquisaByAssunto')
    .get(function (req,res) {
        res.render('pesquisaByAssunto.ejs');
    });
    
    app.route('/InserirLivro')
    .get(livro.cadastrarLivro);
    
    app.route('/consultaLivrosDrop')
    .get(livro.consultaLivrosAll);
    
    app.route('/InserirRef')
    .get(referencia.cadastrarReferencia);
    
    app.route('/pesquisaRefByBook')
    .get(referencia.pesquisaRefByBook);
    
    app.route('/pesquisaRefByAssunto')
    .get(referencia.pesquisaRefByAssunto);
    
};