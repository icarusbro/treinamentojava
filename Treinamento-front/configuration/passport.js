var LocalStrategy    = require('passport-local').Strategy
,   FacebookStrategy = require('passport-facebook').Strategy
,   GoogleStrategy = require('passport-google-oauth').OAuth2Strategy

,   Usuario          = require('../models/usuario')
,   configAuth       = require('./auth');

module.exports = function(passport) {

    passport.serializeUser(function(user, done) {
        done(null, user.id);
    });

    passport.deserializeUser(function(id, done) {
        Usuario.findById(id, function(err, user) {
            done(err, user);
        });
    });
    
    passport.use(new FacebookStrategy({
        clientID        : configAuth.facebookAuth.clientID,
        clientSecret    : configAuth.facebookAuth.clientSecret,
        callbackURL     : configAuth.facebookAuth.callbackURL,
        profileFields   : ['id', 'emails', 'name']
    },
    function(token, refreshToken, profile, done) {
        process.nextTick(function() {
            Usuario.findOne({ 'facebook.id' : profile.id }, function(err, user) {

                if (err) return done(err);

                if (user) {
                    return done(null, user);
                } else {
                    var novoUsuario            = new Usuario();
                    
                    novoUsuario.facebook.id    = profile.id;                   
                    novoUsuario.facebook.token = token;                    
                    novoUsuario.facebook.name  = profile.name.givenName + ' ' + profile.name.familyName;
                    novoUsuario.facebook.email = profile.emails[0].value;
                    novoUsuario.dataInclusao   = new Date();
                    novoUsuario.nome           = novoUsuario.facebook.name;

                    novoUsuario.save(function(err) {
                        if (err) throw err;                   
                        return done(null, novoUsuario);
                    });
                }
            });
        });
    }));
    
    passport.use(new GoogleStrategy({

        clientID        : configAuth.googleAuth.clientID,
        clientSecret    : configAuth.googleAuth.clientSecret,
        callbackURL     : configAuth.googleAuth.callbackURL,

    },
    function(token, refreshToken, profile, done) {

        process.nextTick(function() {

            Usuario.findOne({ 'google.id' : profile.id }, function(err, user) {
                if (err)
                    return done(err);
                if (user) {
                    return done(null, user);
                } else {
                    var novoUsuario            = new Usuario();
                    
                    novoUsuario.google.id      = profile.id;                   
                    novoUsuario.google.token   = token;                    
                    novoUsuario.google.name    = profile.displayName;
                    novoUsuario.google.email   = profile.emails[0].value;
                    novoUsuario.dataInclusao   = new Date();
                    novoUsuario.nome           = novoUsuario.google.name;

                    novoUsuario.save(function(err) {
                        if (err) throw err;                   
                        return done(null, novoUsuario);
                    });
                }
            });
        });
    }));
};