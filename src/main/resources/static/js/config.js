// config

var app =

angular.module('app')
    .config(
        ['$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
            function ($controllerProvider, $compileProvider, $filterProvider, $provide) {

                // lazy controller, directive and service
                app.controller = $controllerProvider.register;
                app.directive  = $compileProvider.directive;
                app.filter     = $filterProvider.register;
                app.factory    = $provide.factory;
                app.service    = $provide.service;
                app.constant   = $provide.constant;
                app.value      = $provide.value;
            }
        ])
    .config(['$translateProvider', function ($translateProvider) {
        // Register a loader for the static files
        // So, the module will search missing translation tables under the specified urls.
        // Those urls are [prefix][langKey][suffix].
        $translateProvider.useStaticFilesLoader({
            prefix: '',
            suffix: ''
        });
        // Tell the module what language to use by default
        $translateProvider.preferredLanguage('getEntityInfo');
        // Tell the module to store the language in the local storage
        $translateProvider.useLocalStorage();
    }])
    .config(['$sceDelegateProvider', function ($sceDelegateProvider) {
        $sceDelegateProvider.resourceUrlWhitelist([
            // Allow same origin resource loads.
            'self',
            // Allow loading from our assets domain.  Notice the difference between * and **.
            '**']);
    }])
    .config(['SpringDataRestInterceptorProvider', function (SpringDataRestInterceptorProvider) {
        SpringDataRestInterceptorProvider.initInterceptors();
    }])
    .config(['$httpProvider', function ($httpProvider) {

        var citylocation = new qq.maps.CityService({
            complete : function(result){

                $httpProvider.defaults.headers.common = {
                    "app_code": "XIAOJINPINGTAI",
                    "client": "web",
                    "longitude": result.detail.latLng.lng,
                    "latitude": result.detail.latLng.lat
                };

            }
        });

        citylocation.searchLocalCity();

    }]);