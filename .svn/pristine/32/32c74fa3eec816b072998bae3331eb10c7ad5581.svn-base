"use strict";

/* joshua: entity Common Controllers */


app.controller("workflowEntityConfigModuleController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        function ($rootScope, $scope, $http, $stateParams) {

            $scope.entity_key = $stateParams.entity_key;//复数
            console.log("workflowEntityConfigModuleController");

        }
    ]
);

//行为配置部分
app.controller("workflowEntityConfigActListController",
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
                $state.go("workflowEntityConfig.actEdit", {act_id: act_id}, {reload: true});
            };

            $scope.actRole = function (act_id) {
                $state.go("workflowEntityConfig.actRole", {act_id: act_id}, {reload: true});
            };

        }
    ]
);

app.controller("workflowEntityConfigActFormController",
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
            $scope.nextWorkflow         = null;
            $scope.targetState          = null;
            $scope.noticeDepartmenttype = null;
            /**/
            $scope.messageSender        = null;

            /*所有信息发送人*/
            $scope.allMessengerSender = [];

            $scope.allRoles           = [];
            $scope.allStates          = [];
            $scope.allWorkflows       = [];
            $scope.allDepartmenttypes = [];
            $scope.allActGroups       = $rootScope.allEntitiesInfo[$scope.entity_key + "Act"].actGroup.values;
            $scope.allActTypes        = $rootScope.allEntitiesInfo[$scope.entity_key + "Act"].actType.values;

            $scope.getAllStates = function () {

                entity.getList("/rest/" + $scope.entity_key + "States?size=10000").then(function(resResult){

                    $scope.allStates = resResult._embeddedItems;

                    if ($scope.act_id) {

                        entity.getDetail($scope.act._links.targetState.href).then(function(resResult){

                            $scope.targetState = entity.getSelectedMyOwn($scope.allStates,resResult);

                        });

                    }

                });

            };

            $scope.getAllWorkflows = function () {

                entity.getList("/rest/" + $scope.entity_key + "Workflows?size=10000").then(function(resResult){

                    $scope.allWorkflows = resResult._embeddedItems;

                    if ($scope.act_id) {

                        entity.getDetail($scope.act._links.nextWorkflow.href).then(function(resResult){

                            $scope.nextWorkflow = entity.getSelectedMyOwn($scope.allWorkflows,resResult);

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

            $scope.getAllMessengerSender = function () {

                entity.getList("/rest/users/search/findByRoleRoleCode?roleCode=MESSENGER_SENDER").then(function (resResult) {

                    $scope.allMessengerSender = resResult._embeddedItems;

                        if ($scope.act_id) {

                            entity.getDetail($scope.act._links.messageSender.href).then(function (resResult) {

                                $scope.messageSender = entity.getSelectedMyOwn($scope.allMessengerSender, resResult);

                            });

                        }
                    });

            };

            if ($scope.act_id) {

                entity.getDetail("rest/" + $scope.entity_key + "Acts/" + $scope.act_id).then(function (resResult) {

                    $scope.act = resResult;

                    $scope.getAllStates();
                    $scope.getAllWorkflows();
                    $scope.getAllDepartmenttypes();
                    $scope.getAllMessengerSender();

                });

            } else {

                $scope.getAllStates();
                $scope.getAllWorkflows();
                $scope.getAllDepartmenttypes();
                $scope.getAllMessengerSender();

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

                $scope.act.noticeRole           = $scope.noticeRole ? $scope.noticeRole._links.self.href : null;
                $scope.act.targetState          = $scope.targetState ? $scope.targetState._links.self.href : null;
                $scope.act.nextWorkflow         = $scope.nextWorkflow ? $scope.nextWorkflow._links.self.href : null;
                $scope.act.noticeDepartmenttype = $scope.noticeDepartmenttype ? $scope.noticeDepartmenttype._links.self.href : null;

                $scope.act.messageSender        = $scope.messageSender ? $scope.messageSender._links.self.href : null;

                if ($scope.act._links) {
                    $http({
                        url: $scope.act._links.self.href,
                        method: "PATCH",
                        data: $scope.act
                    }).success(function (resResult) {
                        $state.go("workflowEntityConfig.acts", {}, {reload: true});
                        $rootScope.toasterSuccess("成功！", "修改成功！");
                    });
                } else {
                    $http.post("rest/" + $scope.entity_key + "Acts", $scope.act).success(function (resResult) {
                        $state.go("workflowEntityConfig.acts", {}, {reload: true});
                        $rootScope.toasterSuccess("成功！", "新建成功！");
                    });
                }

            };
        }
    ]
);

app.controller("workflowEntityConfigActRoleController",
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
app.controller("workflowEntityConfigStateListController",
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
                $state.go("workflowEntityConfig.stateEdit", {state_id: state_id}, {reload: true});
            };

            $scope.stateAct = function (state_id) {
                $state.go("workflowEntityConfig.stateAct", {state_id: state_id}, {reload: true});
            };

        }
    ]
);

app.controller("workflowEntityConfigStateFormController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $state, $stateParams, entity) {

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
                    $state.go("workflowEntityConfig.states", {}, {reload: true});
                    $rootScope.toasterSuccess("成功！", "修改成功！");
                }).error(function (resResult) {

                })
            } else {
                $http.post("rest/" + $scope.entity_key + "States", $scope.state).success(function (resResult) {
                    $state.go("workflowEntityConfig.states", {}, {reload: true});
                    $rootScope.toasterSuccess("成功！", "新建成功！");
                })
            }

        };
    }
    ]
);

app.controller("workflowEntityConfigStateActController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $stateParams, entity) {

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

//工作流配置部分
app.controller("workflowEntityConfigWorkflowListController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $state, $stateParams, entity) {

            $scope.entity_key = $stateParams.entity_key;//复数
            $scope.workflows  = [];

            entity.getList("/rest/" + $scope.entity_key + "Workflows?size=10000").then(function(resResult){
                $scope.workflows = resResult._embeddedItems;
            });

            $scope.workflowEdit = function (workflow_id) {
                $state.go("workflowEntityConfig.workflowEdit", {workflow_id: workflow_id}, {reload: true});
            };

            $scope.workflowState = function (workflow_id) {
                $state.go("workflowEntityConfig.workflowState", {workflow_id: workflow_id}, {reload: true});
            };
        }
    ]
);

app.controller("workflowEntityConfigWorkflowFormController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $state, $stateParams, entity) {

        $scope.entity_key  = $stateParams.entity_key;
        $scope.workflow_id = $stateParams.workflow_id;
        $scope.workflow    = {};

        if ($scope.workflow_id) {

            entity.getDetail("rest/" + $scope.entity_key + "Workflows/" + $scope.workflow_id).then(function(resResult){
                $scope.workflow = resResult;
            });

        }

        $scope.save = function () {

            if ($scope.workflow._links) {
                $http({
                    url: $scope.workflow._links.self.href,
                    method: "PATCH",
                    data: $scope.workflow
                }).success(function (resResult) {
                    $state.go("workflowEntityConfig.workflows", {}, {reload: true});
                    $rootScope.toasterSuccess("成功！", "修改成功！");
                }).error(function (resResult) {

                })
            } else {
                $http.post("rest/" + $scope.entity_key + "Workflows", $scope.workflow).success(function (resResult) {
                    $state.go("workflowEntityConfig.workflows", {}, {reload: true});
                    $rootScope.toasterSuccess("成功！", "新建成功！");
                })
            }

        };
    }
    ]
);

app.controller("workflowEntityConfigWorkflowStateController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $stateParams, entity) {

            $scope.entity_key  = $stateParams.entity_key;
            $scope.workflow_id = $stateParams.workflow_id;

            $scope.allStates      = [];
            $scope.workflowStates = [];

            entity.getList("/rest/" + $scope.entity_key + "States?size=10000").then(function(resResult){

                $scope.allStates = resResult._embeddedItems;

                entity.getList("/rest/" + $scope.entity_key + "Workflows/" + $scope.workflow_id + "/states?size=10000").then(function(resResult){

                    $scope.workflowStates = resResult._embeddedItems;

                    angular.forEach($scope.allStates, function (value, index) {

                        value.isSelected = false;

                        angular.forEach($scope.workflowStates, function (v2, i2) {
                            if (v2.id === value.id) {
                                value.isSelected = true;
                            }
                        });

                    });

                });
            });

            $scope.changeWorkflowState = function (state) {
                //增加
                if (state.isSelected) {
                    $http({
                        method: "PATCH",
                        url: "/rest/" + $scope.entity_key + "Workflows/" + $scope.workflow_id + "/states",
                        data: state._links.self.href,
                        headers: {"Content-Type": "text/uri-list"}
                    }).success(function (resResult) {
                        $rootScope.toasterSuccess("成功！", "关联成功！");
                    });
                } else {//删除

                    $http({
                        method: "DELETE",
                        url: "/rest/" + $scope.entity_key + "Workflows/" + $scope.workflow_id + "/states/" + state.id
                    }).success(function (resResult) {
                        $rootScope.toasterInfo("成功！", "已取消关联！");
                    });
                }
            };

        }
    ]
);