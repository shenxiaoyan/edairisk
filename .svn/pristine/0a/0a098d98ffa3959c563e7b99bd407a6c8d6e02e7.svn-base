app.directive("regionSelectStore", function () {
    return {
        restrict: "AE",
        template: "<div class='clearfix'>" +
        "<div class='pull-left inline m-r' style='width:calc( 33.3% - 15px);'>" +
        "<select class='form-control' name='province' required ng-model='province' ng-options='region.text for region in regions.children' ng-change='selectProvince()'></select>" +
        "</div>" +
        "<div class='pull-left inline m-r' style='width:calc( 33.3% - 15px);'>" +
        "<select class='form-control' name='city' ng-model='city' ng-options='city.text for city in cities' ng-change='selectCity()'><option value=''>不选择</option></select>" +
        "</div>" +
        "<div class='pull-left inline' style='width:calc( 33.3% );'>" +
        "<select class='form-control'  name='district' ng-model='district' ng-options='district.text for district in districts' ng-change='selectChane();'><option value=''>不选择</option></select>" +
        "</div>" +
        "</div>",
        replace: true,
        transclude: true,
        scope: false,
        link: function (scope, iElement, iAttrs) {

            scope.selectProvince = function () {
                scope.cities    = scope.province ? scope.province.children : null;
                scope.city      = scope.province ? scope.province.children[0] : null;
                scope.districts = scope.city ? scope.city.children : null;
                scope.district  = scope.city ? scope.city.children[0] : null;
                scope.selectChane();
            };

            scope.selectCity = function () {
                scope.districts = scope.city ? scope.city.children : null;
                scope.district  = scope.city ? scope.city.children[0] : null;
                scope.selectChane();
            };


        }
    }
});

app.directive("regionSelect", function () {
    return {
        restrict: "AE",
        template: "<div class='clearfix'>" +
        "<div class='pull-left inline m-r' style='width:calc( 33.3% - 15px);'>" +
        "<select class='form-control' name='province' required ng-model='province' ng-options='region.text for region in regions.children' ng-change='selectProvince()'></select>" +
        "</div>" +
        "<div class='pull-left inline m-r' style='width:calc( 33.3% - 15px);'>" +
        "<select class='form-control' name='city' ng-model='city' ng-options='city.text for city in cities' ng-change='selectCity()'><option value=''>不选择</option></select>" +
        "</div>" +
        "<div class='pull-left inline' style='width:calc( 33.3% );'>" +
        "<select class='form-control'  name='district' ng-model='district' ng-options='district.text for district in districts'><option value=''>不选择</option></select>" +
        "</div>" +
        "</div>",
        replace: true,
        transclude: true,
        scope: false,
        link: function (scope, iElement, iAttrs) {

            scope.selectProvince = function () {
                scope.cities    = scope.province ? scope.province.children : null;
                scope.city      = scope.province ? scope.province.children[0] : null;
                scope.districts = scope.city ? scope.city.children : null;
                scope.district  = scope.city ? scope.city.children[0] : null;
            };

            scope.selectCity = function () {
                scope.districts = scope.city ? scope.city.children : null;
                scope.district  = scope.city ? scope.city.children[0] : null;
            };


        }
    }
});

app.directive("twoRegionSelect", function () {
    return {
        restrict: "AE",
        template: "<div class='clearfix'>" +
        "<div class='pull-left inline m-r' style='width:calc( 33.3% - 15px);'>" +
        "<select class='form-control' name='province' required ng-model='province' ng-options='region.text for region in regions.children' ng-change='selectProvince()'></select>" +
        "</div>" +
        "<div class='pull-left inline m-r' style='width:calc( 33.3% - 15px);'>" +
        "<select class='form-control' name='city' ng-model='city' ng-options='city.text for city in cities'><option value=''>不选择</option></select>" +
        "</div>" +
        "</div>",
        replace: true,
        transclude: true,
        scope: false,
        link: function (scope, iElement, iAttrs) {

            scope.selectProvince = function () {
                scope.cities    = scope.province ? scope.province.children : null;
                scope.city      = scope.province ? scope.province.children[0] : null;
            };

        }
    }
});