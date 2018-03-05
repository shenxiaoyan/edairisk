"use strict";
/* joshua: entity Common Controllers */
app.controller("auditEntityModuleController",
    [
        "$rootScope",
        "$scope",
        "$state",
        "$stateParams",
        function ($rootScope, $scope, $state, $stateParams) {

            console.log("auditEntityModuleController");

        }
    ]
);

app.controller("auditEntityListController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $stateParams, entity) {

            //单数 例user
            if ($stateParams.entity_key === "orderwdsjsh") {
                $scope.entity_key = $stateParams.entity_key + "e";
            } else {
                $scope.entity_key = $stateParams.entity_key;
            }

            $scope.states = [];

            /*
             * 获取该实体的所有状态
             * @author  金杭
             * @param   无
             * @return  无
             * */
            entity.getList("/rest/" + $stateParams.entity_key + "States?sort=sort").then(function (resResult) {
                //去除删除状态
                angular.forEach(resResult._embeddedItems, function (val, key) {
                    if (val.stateCode !== "DELETED") {
                        $scope.states.push(val)
                    }
                });
                $scope.state_code = $scope.states[0].stateCode;
                $scope.getList(0);
            });

            //初始化分页参数
            $scope.pageList = {
                size: 0,
                total_elements: 0,
                current_page: 0,
                num_page: 1,
                total_page: 1
            };

            $scope.list = [];

            /*
             * 获取实体列表
             * @author  金杭
             * @param   {number}  页码
             * @return  无
             * */
            $scope.getList = function (page) {

                $scope.loading = true;

                $scope.list = [];

                //全部筛选
                //var link = "/rest/" + $scope.entity_key + "s/search/listStateOwnDepartmentAndChildren?size=20&page=" + page;

                //有状态时的筛选路由
                //if($scope.state_code){
                var link = "/rest/" + $scope.entity_key + "s/search/listStateOwnDepartmentAndChildren?sort=id,desc&stateCode=" + $scope.state_code + "&size=20&page=" + page;
                //}

                entity.getList(link, ["state"]).then(function (resResult) {

                    $scope.loading = false;

                    $scope.list = resResult._embeddedItems;

                    //设置分页参数
                    $scope.pageList = {
                        size: resResult.page.size,
                        total_elements: resResult.page.totalElements,
                        current_page: resResult.page.number,
                        num_page: resResult.page.totalPages,
                        total_page: resResult.page.totalPages
                    };

                    //生成翻页插件
                    if ($scope.list.length > 0) {
                        $rootScope.setPaginator("#" + $stateParams.entity_key + "_list_paginator", $scope.pageList, $scope.getList);
                    }

                });
            };

            //状态筛选
            $scope.stateSelect = function ($event, stateCode) {
                if ($event.target.nodeName === "INPUT") {
                    $scope.state_code = stateCode;
                    $scope.getList(0);
                }
            };

        }
    ]
);

app.controller("auditEntityHomeController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$state",
        "$stateParams",
        "entity",
        function ($rootScope, $scope, $http, $stateParams, entity) {

            //判断是否以sh结尾
            if ($stateParams.entity_key === "orderwdsjsh") {
                $scope.entity_key = "orderwdsjshe";
            } else {
                $scope.entity_key = $stateParams.entity_key;
            }

            //实体id
            $scope.id = $stateParams.id;

            $scope.profile     = {};
            $scope.canActs     = [];
            $scope.canFileActs = [];


            /*
             * 获取实体详情
             * */
            entity.getDetail("/rest/" + $scope.entity_key + "s/" + $scope.id, ["createdBy", "state"]).then(function (resResult) {
                $scope.profile = resResult;

                //将实体可操作的行为act进行分组，方便在不同的页面显示
                angular.forEach($scope.profile.currentUserCanActList, function (val, key) {
                    if (val.actGroup === "UPDATE" || val.actGroup === "OPERATE" || val.actGroup === "FILE_PACKAGE") {
                        $scope.canActs.push(val);
                    } else if (val.actGroup === "FILE_OPERATE") {
                        $scope.canFileActs.push(val)
                    }
                });

                $scope.state_code   = $scope.profile.state.stateCode;
                $scope.stateActList = $scope.profile.stateActList[$scope.state_code];

            });

            /*
             * 用户所有操作的方法集合，其中每一个方法对应实体的一个行为，也有自定义方法
             * @author  李斌
             * */
            $scope.excute = function (actCode) {
                if (actCode === "update") {
                    $state.go("auditEntity.home.profile.update", {}, {reload: true});
                }
            };

            console.log("auditEntityHomeController");

        }
    ]
);

app.controller("auditEntityProfileController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            console.log("auditEntityProfileController");

        }
    ]
);

app.controller("auditEntityInfoController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            console.log("auditEntityInfoController");

        }
    ]
);

app.controller("auditEntityLogController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        "entity",
        function ($rootScope, $scope, $http, $stateParams, $state, entity) {

            if ($stateParams.entity_key === "orderwdsjsh") {
                $scope.entity_key = $stateParams.entity_key + "e";
            } else {
                $scope.entity_key = $stateParams.entity_key;
            }

            $scope.id = $stateParams.id;

            $scope.logs = [];

            /*
             * 获取实体的log记录
             * */
            entity.getList("/rest/" + $scope.entity_key + "s/" + $scope.id + "/logs").then(function (resResult) {

                $scope.logs = resResult._embeddedItems;

                angular.forEach($scope.logs, function (value, index) {
                    entity.getDetail(value._links.createdBy.href).then(function (resResult) {
                        value.createdBy = resResult.nickname;
                        value.avatar    = resResult.headimgurl;
                    });
                });

                //log记录需要倒序
                $scope.logs.sort(function (a, b) {
                    return new Date(b.lastModifiedAt) - new Date(a.lastModifiedAt);
                });

            });

            /*
             * 点击查看log详情
             * @author  金杭
             * @param   无
             * @return  无
             * */
            $scope.jumpToLog = function (log) {

                $scope.log = log;

                //弹出模态框
                $("#log_modal").modal("show");

            };
        }
    ]
);