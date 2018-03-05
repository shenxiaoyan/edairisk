"use strict";
/* joshua: entity Common Controllers */
app.controller("workflowEntityModuleController",
    [
        "$rootScope",
        "$scope",
        "$state",
        "$stateParams",
        function ($rootScope, $scope, $state, $stateParams) {
            console.log("workflowEntityModuleController");

        }
    ]
);

app.controller("workflowEntityListController",
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
            entity.getList("/rest/" + $stateParams.entity_key + "Workflows/ " + $stateParams.workflow_id + "/states").then(function (resResult) {
                $scope.states = resResult._embeddedItems;
                if($scope.states.length > 0){

                    $scope.states = $scope.states.sort(function(a, b){
                        return a.sort - b.sort;
                    });

                    $scope.state_code = $scope.states[0].stateCode;
                    $scope.getList(0);
                }
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

app.controller("workflowEntityHomeController",
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

            $scope.profile = {};
            $scope.canActs = [];

            /*
             * 获取实体详情
             * */
            entity.getDetail("/rest/" + $scope.entity_key + "s/" + $scope.id, ["createdBy", "state"]).then(function (resResult) {
                $scope.profile = resResult;

                $scope.canActs     = [];
                $scope.canFileActs = [];

                //将实体可操作的行为act进行分组，方便在不同的页面显示
                angular.forEach($scope.profile.currentUserCanActList, function (val, key) {
                    if (val.actGroup === "UPDATE" || val.actGroup === "OPERATE" || val.actGroup === "FILE_PACKAGE") {
                        $scope.canActs.push(val);
                    } else if (val.actGroup === "FILE_OPERATE") {
                        $scope.canFileActs.push(val);
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
                    $state.go("workflowEntity.home.profile.update", {}, {reload: true});
                }
            };

            console.log("workflowEntityHomeController");

        }
    ]
);

app.controller("workflowEntityProfileController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            console.log("workflowEntityProfileController");

        }
    ]
);

app.controller("workflowEntityInfoController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        function ($rootScope, $scope, $http, $stateParams, $state) {

            console.log("workflowEntityInfoController");

        }
    ]
);

app.controller("workflowEntityLogController",
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

app.controller("workflowEntityFileController",
    [
        "$rootScope",
        "$scope",
        "$http",
        "$stateParams",
        "$state",
        "FileUploader",
        "entity",
        function ($rootScope, $scope, $http, $stateParams, $state, FileUploader, entity) {

            if ($stateParams.entity_key === "orderwdsjsh") {
                $scope.entity_key = $stateParams.entity_key + "e";
            } else if ($stateParams.entity_key === "loancollection") {
                $scope.entity_key = "loan";
            } else {
                $scope.entity_key = $stateParams.entity_key;
            }

            $scope.id = $stateParams.id;

            $scope.level2Tree = [];
            $scope.level3Tree = [];

            $scope.firstFolder  = {opened: true, folderName: ""};
            $scope.secondFolder = {opened: false, folderName: ""};
            $scope.thirdFolder  = {opened: false, folderName: ""};

            /*
             * 获取该个实体的文件夹目录
             * */
            entity.getDetail("/rest/" + $scope.entity_key + "s/" + $scope.id).then(function (resResult) {

                if (JSON.stringify(resResult.fileCategoryTree) !== "{}") {
                    $scope.fileTree = resResult.fileCategoryTree;
                } else {
                    $scope.fileTree = null;
                }

            });

            /*
             * 点击进入二级文件夹
             * @author  金杭
             * @param   folderName  {string}  本级文件夹名称
             * @param   level2      {object}  二级文件夹
             * @return  无
             * */
            $scope.jumpToLevel2 = function (folderName, level2) {

                $scope.firstFolder.folderName  = folderName;
                $scope.secondFolder.folderName = "";

                $scope.level2Tree = level2;

                $scope.firstFolder.opened  = false;
                $scope.secondFolder.opened = true;
                $scope.thirdFolder.opened  = false;
            };

            /*
             * 点击查看该文件夹下所有文件
             * @author  金杭
             * @param   folderName  {string}  二级文件夹名称
             * @return  无
             * */
            $scope.jumpToLevel3 = function (folderName) {

                $scope.secondFolder.folderName = folderName;

                entity.getList("/rest/" + $stateParams.entity_key + "Files/search/findByEntityIdAndTopcategoryAndSubcategory?id=" + $scope.id + "&topcategory=" + $scope.firstFolder.folderName + "&subcategory=" + $scope.secondFolder.folderName).then(function (resResult) {

                    $scope.level3Tree = resResult._embeddedItems.sort(function (a, b) {
                        return a.id - b.id;
                    });

                    $scope.firstFolder.opened  = false;
                    $scope.secondFolder.opened = false;
                    $scope.thirdFolder.opened  = true;
                });
            };

            /*
             * 点击回到上一级文件夹
             * @author  金杭
             * @return  无
             * */
            $scope.backFolder = function () {

                if ($scope.thirdFolder.opened) {

                    $scope.firstFolder.opened  = false;
                    $scope.secondFolder.opened = true;
                    $scope.thirdFolder.opened  = false;

                } else if ($scope.secondFolder.opened) {

                    $scope.firstFolder.opened  = true;
                    $scope.secondFolder.opened = false;
                    $scope.thirdFolder.opened  = false;

                }

                $scope.secondFolder.folderName = "";
            };

            //以下是上传文件的动作详情参考前端框架示例 js/controllers/file-upload.js
            $scope.uploader = new FileUploader({
                url: '/fileUpload'
            });

            $scope.uploader.filters.push({
                name: 'customFilter',
                fn: function (item, options) {
                    return this.queue.length < 20;//文件上传数量不超过10个
                }
            });

            $scope.uploader.onAfterAddingFile = function (fileItem) {
                //增加行为code
                fileItem.actCode = $scope.actCode;

                if (fileItem.file.type.indexOf("image") !== -1) {
                    fileItem.formData = [{fileType: "image"}]
                } else {
                    fileItem.formData = [{fileType: "file"}]
                }

            };

            $scope.uploader.onErrorItem = function (fileItem, response, status, headers) {
                $rootScope.toasterError("上传失败！", $scope.subcategory + "：" + fileItem.file.name + " 上传失败！");
            };

            $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) {

                //增加目录结构
                response.result.topcategory = $scope.topcategory;
                response.result.subcategory = $scope.subcategory;

                $http({
                    method: "PATCH",
                    url: "rest/" + $scope.entity_key + "s/" + $scope.id,//这里是关联的实体
                    data: {
                        fileObject: response.result,//fileObject是上传完文件后的文件对象
                        act: fileItem.actCode//上传文件的行为
                    }
                }).success(function (data) {
                    $rootScope.toasterSuccess("上传成功！", $scope.subcategory + "：" + fileItem.file.name + " 上传成功！");
                });
            };

            $scope.uploader.onCompleteAll = function () {
                //console.info('onCompleteAll');
            };

            $("#upload_file_modal").on("hidden.bs.modal", function (e) {
                $state.reload();
            });

            $scope.currentFile  = {};
            $scope.currentIndex = 0;

            $scope.openFileCarousel = function (index) {
                $scope.currentFile  = $scope.level3Tree[index];
                $scope.currentIndex = index;
                $("#carousel_file_modal").modal("show");
            };

            $scope.prevFile = function () {
                if ($scope.currentIndex - 1 < 0) {
                    $rootScope.toasterInfo("提示！", "已经是第一张啦！");
                } else {
                    $scope.currentFile = $scope.level3Tree[$scope.currentIndex - 1];
                    $scope.currentIndex -= 1;
                }
            };

            $scope.nextFile = function () {
                if ($scope.currentIndex + 1 > $scope.level3Tree.length - 1) {
                    $rootScope.toasterInfo("提示！", "已经是最后一张啦！");
                } else {
                    $scope.currentFile = $scope.level3Tree[$scope.currentIndex + 1];
                    $scope.currentIndex += 1;
                }
            };

            $scope.isImage = function(file){

                var isImage = false;

                switch (file.fileType){
                    case "JPEG":
                        isImage = true;
                        break;
                    case "PNG":
                        isImage = true;
                        break;
                    case "GIF":
                        isImage = true;
                        break;
                    case "TIFF":
                        isImage = true;
                        break;
                    case "BMP":
                        isImage = true;
                        break;
                }

                return isImage;
            };
        }
    ]
);