/**
 * Created by Jh on 2017/8/10.
 */
"use strict";

/* joshua: entity Common Controllers */


app.controller("menuConfigModuleController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            $scope.select_branch = function (branch) {
                $state.go("menuConfig.edit", branch);
            };

            console.log("menuConfigModuleController");

        }
    ]
);

//创建
app.controller("menuConfigFormController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $stateParams, entity) {

            $scope.id               = $stateParams.id;
            $scope.ownRoles         = [];
            $scope.allRoles         = [];
            $scope.menu             = {};
            $scope.entities         = [];
            $scope.selectEntity     = null;
            $scope.entityWorkflows  = null;
            $scope.selectWorkflow   = null;
            $scope.parentMenu       = null;
            $scope.entityIsSelected = false;

            var reg = /[A-Z]/;

            angular.forEach($rootScope.allEntitiesInfo, function (value, key) {
                if (reg.exec(key) === null) {
                    $scope.entities.push({label: key});
                }
            });

            $scope.changeEntity = function (selectEntity) {
                if (selectEntity) {
                    $scope.entityIsSelected = true;
                    entity.getList("rest/" + selectEntity.label + "Workflows").then(function (resResult) {

                        $scope.entityWorkflows = resResult._embeddedItems;

                        if($scope.menu._links){
                            angular.forEach($scope.entityWorkflows,function(value,index){
                                if($scope.menu.workflowId === value.id){
                                    $scope.selectWorkflow = value;
                                }
                            });
                        }

                    });
                }
            };

            if ($scope.id) {
                entity.getDetail("rest/menus/" + $scope.id, ["parent", "visibleRoles"]).then(function (resResult) {

                    $scope.menu = resResult;

                    if (JSON.stringify($scope.menu.parent) !== "{}" && angular.isObject($scope.menu.parent)) {
                        $scope.parentMenu = entity.getSelectedMyOwn($rootScope.menus, $scope.menu.parent);
                    }

                    angular.forEach($scope.entities, function (value, index) {
                        if ($scope.menu.entityKey === value.label) {
                            $scope.selectEntity = value;
                            $scope.changeEntity($scope.selectEntity);
                        }
                    });

                    $scope.ownRoles = $scope.menu.visibleRoles._embeddedItems;
                    $scope.getAllRoles($scope.ownRoles);

                });
            }

            $scope.getAllRoles = function (ownRoles) {
                entity.getList("rest/roles?size=10000").then(function (resResult) {
                    $scope.allRoles = resResult._embeddedItems;

                    angular.forEach($scope.allRoles, function (value, index) {
                        value.isSelected = false;

                        angular.forEach(ownRoles, function (v2, i2) {
                            if (v2.id === value.id) {
                                value.isSelected = true;
                            }
                        });

                    });
                });
            };

            $scope.save = function () {

                delete $scope.menu.visibleRoles;
                $scope.menu.parent     = $scope.parentMenu ? "rest/menus/" + $scope.parentMenu.id : null;
                $scope.menu.level      = $scope.menu.level?Number($scope.menu.level):null;
                $scope.menu.entityKey  = $scope.selectEntity ? $scope.selectEntity.label : null;
                $scope.menu.workflowId = $scope.selectWorkflow ? $scope.selectWorkflow.id : null;

                if ($scope.menu._links) {
                    $http({
                        url: $scope.menu._links.self.href,
                        method: "PATCH",
                        data: $scope.menu
                    }).success(function (resResult) {
                        $rootScope.toasterSuccess("成功！", "修改成功！");
                        location.reload(true);
                    });
                } else {
                    $http.post("rest/menus", $scope.menu).success(function (resResult) {

                        $http({
                            method: "PATCH",
                            url: "rest/menus/" + resResult.id + "/visibleRoles",
                            data: "rest/roles/1",
                            headers: {"Content-Type": "text/uri-list"}
                        }).success(function (resResult) {
                            $rootScope.toasterSuccess("成功！", "新建成功！");
                            location.reload(true);
                        });

                    });
                }

            };

            $scope.changeMenuRole = function (role) {
                //增加
                if (role.isSelected) {
                    $http({
                        method: "PATCH",
                        url: $scope.menu._links.visibleRoles.href,
                        data: role._links.self.href,
                        headers: {"Content-Type": "text/uri-list"}
                    }).success(function (resResult) {
                        $rootScope.toasterSuccess("成功！", "关联成功！");
                    });
                } else {//删除

                    $http({
                        method: "DELETE",
                        url: $scope.menu._links.visibleRoles.href + "/" + role.id
                    }).success(function (resResult) {
                        $rootScope.toasterInfo("成功！", "已取消关联！");
                    });

                }
            };

        }
    ]
);
