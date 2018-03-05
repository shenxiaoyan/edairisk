/*!
 * angular-translate - v2.5.2 - 2014-12-10
 * http://github.com/angular-translate/angular-translate
 * Copyright (c) 2014 ; Licensed MIT
 */
;angular.module("pascalprecht.translate").factory("$translateLocalStorage",["$window","$translateCookieStorage",function(f,a){var c=(function(){var e;return{get:function(i){if(!e){e=f.localStorage.getItem(i)}return e},set:function(i,j){e=j;f.localStorage.setItem(i,j)},put:function(i,j){e=j;f.localStorage.setItem(i,j)}}}());var b="localStorage" in f;if(b){var h="pascalprecht.translate.storageTest";try{if(f.localStorage!==null){f.localStorage.setItem(h,"foo");f.localStorage.removeItem(h);b=true}else{b=false}}catch(d){b=false}}var g=b?c:a;return g}]);