/*!
 * angular-translate - v2.5.2 - 2014-12-10
 * http://github.com/angular-translate/angular-translate
 * Copyright (c) 2014 ; Licensed MIT
 */
;angular.module("pascalprecht.translate").factory("$translateCookieStorage",["$cookieStore",function(b){var a={get:function(c){return b.get(c)},set:function(c,d){b.put(c,d)},put:function(c,d){b.put(c,d)}};return a}]);