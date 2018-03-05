/**
 * Created by Jh on 2017/8/10.
 */
"use strict";

/* joshua: entity Common Controllers */


app.controller("auditEntityConfigModuleController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        function ($rootScope, $scope, $http, $stateParams) {

            $scope.entity_key = $stateParams.entity_key;//复数
            console.log("auditEntityConfigModuleController");

        }
    ]
);

//行为配置部分
app.controller("auditEntityConfigActListController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $state, $stateParams, entity) {

            $scope.entity_key = $stateParams.entity_key;//复数
            $scope.acts       = [];

            entity.getList("/rest/" + $scope.entity_key + "Acts?size=10000").then(function(resResult){
                $scope.acts = resResult._embeddedItems;
            });

            $scope.actEdit = function (act_id) {
                $state.go("auditEntityConfig.actEdit", {act_id: act_id}, {reload: true});
            };

            $scope.actRole = function (act_id) {
                $state.go("auditEntityConfig.actRole", {act_id: act_id}, {reload: true});
            };

        }
    ]
);

app.controller("auditEntityConfigActFormController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $state, $stateParams, entity) {

            //joshua: 如果参数里带了 act_id 则为修改，否则为创建
            $scope.entity_key = $stateParams.entity_key;
            $scope.act_id     = $stateParams.act_id;

            $scope.act = {};

            $scope.noticeRole           = null;
            $scope.noticeDepartmenttype = null;

            $scope.allRoles           = [];
            $scope.allDepartmenttypes = [];
            $scope.allActGroups       = $rootScope.allEntitiesInfo[$scope.entity_key + "Act"].actGroup.values;

            $scope.getAllRoles = function () {

                entity.getList("/rest/roles?size=10000").then(function (resResult) {
                        $scope.allRoles = resResult._embeddedItems;

                        if ($scope.act_id) {

                            entity.getDetail($scope.act._links.noticeRole.href).then(function (resResult) {

                                $scope.noticeRole = entity.getSelectedMyOwn($scope.allRoles, resResult);

                            });

                        }

                    });
            };

            $scope.getAllDepartmenttypes = function () {
                entity.getList("/rest/departmenttypes?size=10000").then(function (resResult) {

                    $scope.allDepartmenttypes = resResult._embeddedItems;

                    if ($scope.act_id) {

                        entity.getDetail($scope.act._links.noticeDepartmenttype.href).then(function (resResult) {

                            $scope.noticeDepartmenttype = entity.getSelectedMyOwn($scope.allDepartmenttypes,resResult);

                            if($scope.noticeDepartmenttype){
                                $scope.setRoleFromDepartment(true);
                            }

                        });

                    } else {

                        $scope.setRoleFromDepartment(false);

                    }

                });
            };

            if ($scope.act_id) {
                entity.getDetail("rest/" + $scope.entity_key + "Acts/" + $scope.act_id).then(function (resResult) {

                    $scope.act = resResult;

                    $scope.getAllRoles();
                    $scope.getAllDepartmenttypes();
                });
            }else{
                $scope.getAllRoles();
                $scope.getAllDepartmenttypes();
            }

            $scope.setRoleFromDepartment = function (bool) {

                if ($scope.noticeDepartmenttype && bool) {

                    entity.getList($scope.noticeDepartmenttype._links.roles.href).then(function (resResult) {

                        $scope.allRoles = resResult._embeddedItems;

                        entity.getDetail($scope.act._links.noticeRole.href).then(function (resResult) {

                            $scope.noticeRole = entity.getSelectedMyOwn($scope.allRoles, resResult);

                        });

                    });

                } else if ($scope.noticeDepartmenttype && !bool) {

                    entity.getList($scope.noticeDepartmenttype._links.roles.href).then(function (resResult) {

                        $scope.allRoles = resResult._embeddedItems;

                    });

                }

            };

            $scope.save = function () {

                $scope.act.noticeRole       = $scope.noticeRole ? $scope.noticeRole._links.self.href : null;
                $scope.act.targetState      = $scope.targetState ? $scope.targetState._links.self.href : null;
                $scope.act.noticeDepartmenttype = $scope.noticeDepartmenttype ? "/rest/departmenttypes/" + $scope.noticeDepartmenttype.id : null;

                if ($scope.act._links) {
                    $http({
                        url: $scope.act._links.self.href,
                        method: "PATCH",
                        data: $scope.act
                    }).success(function (resResult) {
                        $state.go("auditEntityConfig.acts", {}, {reload: true});
                        $rootScope.toasterSuccess("成功！", "修改成功！");
                    });
                } else {
                    $http.post("rest/" + $scope.entity_key + "Acts", $scope.act).success(function (resResult) {
                        $state.go("auditEntityConfig.acts", {}, {reload: true});
                        $rootScope.toasterSuccess("成功！", "新建成功！");
                    });
                }

            };
        }
    ]
);

app.controller("auditEntityConfigActRoleController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $stateParams, entity) {
            $scope.entity_key = $stateParams.entity_key;
            $scope.act_id     = $stateParams.act_id;

            $scope.allRoles = [];
            $scope.actRoles = [];

            entity.getList("/rest/" + $scope.entity_key + "Acts/" + $scope.act_id + "/roles?size=10000").then(function(resResult){

                $scope.actRoles = resResult._embeddedItems;

                entity.getList("/rest/roles?size=10000").then(function(resResult){

                    $scope.allRoles = resResult._embeddedItems;

                    angular.forEach($scope.allRoles, function (value, index) {

                        value.isSelected = false;

                        angular.forEach($scope.actRoles, function (v2, i2) {
                            if (v2.id === value.id) {
                                value.isSelected = true;
                            }
                        });

                    });

                });

            });

            $scope.changeActRole = function (role) {
                //增加
                if (role.isSelected) {
                    $http({
                        method: "PATCH",
                        url: "/rest/" + $scope.entity_key + "Acts/" + $scope.act_id + "/roles",
                        data: role._links.self.href,
                        headers: {"Content-Type": "text/uri-list"}
                    }).success(function (resResult) {
                        $rootScope.toasterSuccess("成功！", "关联成功！");
                    })
                } else {//删除

                    $http({
                        method: "DELETE",
                        url: "/rest/" + $scope.entity_key + "Acts/" + $scope.act_id + "/roles/" + role.id
                    }).success(function (resResult) {
                        $rootScope.toasterInfo("成功！", "已取消关联！");
                    });

                }
            };


        }
    ]
);

//状态配置部分
app.controller("auditEntityConfigStateListController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $state, $stateParams, entity) {

            $scope.entity_key = $stateParams.entity_key;//复数
            $scope.states     = [];

            entity.getList("/rest/" + $scope.entity_key + "States?size=10000").then(function(resResult){
                $scope.states = resResult._embeddedItems;
            });

            $scope.stateEdit = function (state_id) {
                $state.go("auditEntityConfig.stateEdit", {state_id: state_id}, {reload: true});
            };

            $scope.stateAct = function (state_id) {
                $state.go("auditEntityConfig.stateAct", {state_id: state_id}, {reload: true});
            };

        }
    ]
);

app.controller("auditEntityConfigStateFormController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        function ($rootScope, $scope, $http, $state, $stateParams) {

        $scope.entity_key = $stateParams.entity_key;
        $scope.state_id   = $stateParams.state_id;
        $scope.state      = {};

        if ($scope.state_id) {

            entity.getDetail("rest/" + $scope.entity_key + "States/" + $scope.state_id).then(function(resResult){
                $scope.state = resResult;
            });

        }

        $scope.save = function () {

            if ($scope.state._links) {
                $http({
                    url: $scope.state._links.self.href,
                    method: "PATCH",
                    data: $scope.state
                }).success(function (resResult) {
                    $state.go("auditEntityConfig.states", {}, {reload: true});
                    $rootScope.toasterSuccess("成功！", "修改成功！");
                }).error(function (resResult) {

                })
            } else {
                $http.post("rest/" + $scope.entity_key + "States", $scope.state).success(function (resResult) {
                    $state.go("auditEntityConfig.states", {}, {reload: true});
                    $rootScope.toasterSuccess("成功！", "新建成功！");
                })
            }

        };
    }
    ]
);

app.controller("auditEntityConfigStateActController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$resource",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $resource, $stateParams, entity) {

            $scope.entity_key = $stateParams.entity_key;
            $scope.state_id   = $stateParams.state_id;

            $scope.allActs   = [];
            $scope.stateActs = [];

            entity.getList("/rest/" + $scope.entity_key + "Acts?size=10000").then(function(resResult){

                $scope.allActs = resResult._embeddedItems;

                entity.getList("/rest/" + $scope.entity_key + "States/" + $scope.state_id + "/acts?size=10000").then(function(resResult){

                    $scope.stateActs = resResult._embeddedItems;

                    angular.forEach($scope.allActs, function (value, index) {

                        value.isSelected = false;

                        angular.forEach($scope.stateActs, function (v2, i2) {
                            if (v2.id === value.id) {
                                value.isSelected = true;
                            }
                        });

                    });

                });

            });

            $scope.changeStateAct = function (act) {
                //增加
                console.log(act);
                if (act.isSelected) {

                    $http({
                        method: "PATCH",
                        url: "/rest/" + $scope.entity_key + "States/" + $scope.state_id + "/acts",
                        data: act._links.self.href,
                        headers: {"Content-Type": "text/uri-list"}
                    }).success(function (resResult) {
                        $rootScope.toasterSuccess("成功！", "关联成功！");
                    });

                } else {//删除

                    $http({
                        method: "DELETE",
                        url: "/rest/" + $scope.entity_key + "States/" + $scope.state_id + "/acts/" + act.id
                    }).success(function (resResult) {
                        $rootScope.toasterInfo("成功！", "已取消关联！");
                    });

                }
            };

        }
    ]
);