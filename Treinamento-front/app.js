//modulos
var express = require('express')
  , http = require('http')
  , path = require('path')
  //, mongoose = require('mongoose')
  , passport = require('passport')
  , session = require('express-session')
  , flash = require('connect-flash')
  , bodyParser = require('body-parser');
  
//configuracoes
var config = require('./configuration/config')
  
//modelos
//var usuario = require('./models/livro');
//var usuario = require('./models/referencia');
  
var app = express();

app.use(session({ secret: 'secret' }));
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

var routes = require('./routes/routes')(app,passport);
//var connect = mongoose.connect(config.databaseUrl);

app.set('port', config.porta);
app.use(express.static(path.join(__dirname, config.publicDir)));
app.set('views', path.join(__dirname, 'views'));

app.engine('html', require('ejs').renderFile);
app.set('view engine', 'html');

http.createServer(app).listen(app.get('port'), function(){
  console.log('Aplicação funcionando e escutando a porta ' + app.get('port'));
});

module.exports = app;