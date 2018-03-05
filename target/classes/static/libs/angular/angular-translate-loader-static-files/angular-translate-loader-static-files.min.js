/*!
 * angular-translate - v2.5.2 - 2014-12-10
 * http://github.com/angular-translate/angular-translate
 * Copyright (c) 2014 ; Licensed MIT
 */
;angular.module("pascalprecht.translate").factory("$translateStaticFilesLoader",["$q","$http",function(a,b){return function(d){if(!d||(!angular.isString(d.prefix)||!angular.isString(d.suffix))){throw new Error("Couldn't load static files, no prefix or suffix specified!")}var c=a.defer();b(angular.extend({url:[d.prefix,d.key,d.suffix].join(""),method:"GET",params:""},d.$http)).success(function(e){c.resolve(e)}).error(function(e){c.reject(d.key)});return c.promise}}]);