"use strict";
/* Controllers */

app.controller("AppCtrl",
    [
        "$scope",
        "$http",
        "$translate",
        "$localStorage",
        "$window",
        "$rootScope",
        "$state",
        "entity",
        function ($scope, $http, $translate, $localStorage, $window, $rootScope, $state, entity) {

            $rootScope.myinfo     = {};
            $rootScope.updateUser = {};

            $rootScope.onMessage = window.onMessage;

            $rootScope.distEntityId = window.distEntityId;

            $rootScope.selectMessage = function ($event) {
                $($event.delegateTarget).parent().siblings().find("li").removeClass("active");
                $("#message_container").css("display", "block");
                $("#common_container").css("display", "none");
                $("#message_un_read").css("display", "none");
            };

            $rootScope.unSelectMessage = function () {
                $("#message_container").css("display", "none");
                $("#common_container").css("display", "block");
                $state.reload();
            };

            $rootScope.myinfo = angular.copy(window.myinfo);

            $rootScope.updateUser.nickname   = $rootScope.myinfo.nickname;
            $rootScope.updateUser.sex        = $rootScope.myinfo.sex;
            $rootScope.updateUser.country    = $rootScope.myinfo.country;
            $rootScope.updateUser.province   = $rootScope.myinfo.province;
            $rootScope.updateUser.city       = $rootScope.myinfo.city;
            $rootScope.updateUser.headimgurl = $rootScope.myinfo.headimgurl;

            loginAndInitIm($rootScope.myinfo);

            console.log($rootScope.myinfo);

            entity.getDetail("rest/roles/" + $rootScope.myinfo.role.id).then(function (menuResResult) {
                $rootScope.menus = menuResResult.visibleMenuTree;
                console.log($rootScope.menus);
            });


            $http.get("getEntityInfo").success(function (response) {
                $rootScope.allEntitiesInfo = response;
                console.log($rootScope.allEntitiesInfo);
            });

            $rootScope.sessions     = {};
            $rootScope.groups       = {};
            $rootScope.friends      = {};
            $rootScope.menus        = [];
            $rootScope.notices      = {};
            $rootScope.unReadNumber = 0;

            $scope.distributionSave = function (val, entity_id) {
                if (val) {
                    $http({
                        method: "PATCH",
                        url: "/rest/orderwdsjshes/" + entity_id,
                        data: {
                            act: "distribution",
                            notice: val.openid,
                            serviceId: val.id
                        }
                    }).success(function (resResult) {
                        $("#global_distribution").modal("hide");
                    });
                } else {
                    $rootScope.toasterWarning("注意", "请选择一个业务员！");
                }
            };

            $scope.userList = {};

            $scope.getLogList = function () {
                entity.getList("rest/orderwdsjshLogs/search/latestNoticeUser?act_id=8").then(function (resResult) {
                    if (resResult._embeddedItems) {
                        angular.forEach(resResult._embeddedItems, function (val, key) {
                            $scope.userList[val.id] = val
                        })
                    }

                });
            };

            $scope.getLogList();

            $scope.getList = function () {
                $http.get("/linkman").success(function (resResult) {
                    angular.forEach(resResult, function (val, key) {
                        $scope.userList[val.id] = val
                    })
                });
            };

            $scope.distributionSelect = "0";

            $scope.distributionShow = function (val) {
                if (val === "0") {
                    $scope.userList = {};
                    $scope.getLogList();
                } else {
                    $scope.userList = {};
                    $scope.getList();
                }
            };

            /*
             * 重置密码函数
             * @author 金杭
             * */
            $scope.oldPwd     = null;
            $scope.newPwd     = null;
            $scope.confirmPwd = null;

            $scope.resetPassword = function () {

                $http({
                    method: "PATCH",
                    url: $rootScope.myinfo._links.self.href,
                    data: {
                        act: "Modifypassword",
                        password: $scope.oldPwd,
                        newPassword: $scope.confirmPwd
                    }
                }).success(function (resResult) {
                    $rootScope.toasterSuccess("成功！", "修改密码成功！");
                    $("#reset_password_modal").modal("hide");
                    window.location.href = "/logout";
                });

            };

            /*
             * 登录用户主动修改信息
             * @author  金杭
             * @param   无
             * @return  无
             * */
            $scope.updateUserInfo = function () {

                $rootScope.updateUser.act = "update";

                $http({
                    method: "PATCH",
                    url: $rootScope.myinfo._links.self.href,
                    data: $rootScope.updateUser
                }).success(function (resResult) {
                    $rootScope.toasterSuccess("成功！", "修改用户信息成功！");
                    $("#user_update_modal").modal("hide");
                    window.location.reload(true);
                });

            };

            /*
             * 登录用户主动修改头像
             * @author  金杭
             * @param   无
             * @return  无
             * */
            $scope.updateHeadimg = function () {

                var fd = new FormData();

                var headimg = $("#headimgurl_update_input")[0].files[0];

                if (headimg) {

                    fd.append("fileType", "image");
                    fd.append("file", headimg);

                    $http({
                        method: "POST",
                        url: "fileUpload",
                        data: fd,
                        headers: {
                            "Content-type": undefined
                        }
                    }).success(function (resResult) {

                        $http({
                            method: "PATCH",
                            url: $rootScope.myinfo._links.self.href,
                            data: {
                                act: "update",
                                headimgurl: "http://files.xiaojinpingtai.com/" + resResult.result.newFileName
                            }
                        }).success(function (resResult) {
                            $rootScope.toasterSuccess("成功！", "修改用户头像成功！");
                            $("#headimg_update_modal").modal("hide");
                            window.location.reload(true);
                        });

                    });

                }

            };

            $rootScope.toasterSuccess = function (title, text) {
                toastr.success(text, title);
            };

            $rootScope.toasterError = function (title, text) {
                toastr.error(text, title);
            };

            $rootScope.toasterWarning = function (title, text) {
                toastr.warning(text, title);
            };

            $rootScope.toasterInfo = function (title, text) {
                toastr.info(text, title);
            };

            /*
             * 封装 sweetalert，减少代码量
             * @author 金杭
             * @date   2017/07/20
             * @param  {object} options
             *    options属性：
             *    title     弹出框的标题            string
             *    text      弹出框的具体内容    string
             *    type      弹出框 的类型           string
             *    callback  确认后执行的内容    function
             * @return 无
             * */
            $rootScope.sweetConfirm = function (options) {
                swal({
                    title: options.title,
                    text: options.text,
                    type: options.type,
                    showCancelButton: true,
                    confirmButtonColor: "#F05050",
                    cancelButtonText: "不，让我想想",
                    confirmButtonText: "是，我想好了",
                    closeOnConfirm: true
                }, function () {
                    options.callback();
                });
            };

            /*
             * 设置分页的函数
             * @author  金杭
             * @param   selector  {string}    分页的选择器
             * @param   pageList  {object}    分页参数
             * @param   callback  {function}  点击页码后的回调函数
             * */
            $rootScope.setPaginator = function (selector, pageList, callback) {
                //分页的配置
                var pageOptions = {
                    bootstrapMajorVersion: 3, //版本
                    currentPage: pageList.current_page + 1, //当前页数
                    // numberOfPages:pageList.num_page,//显示页数
                    totalPages: pageList.total_page, //总页数
                    tooltipTitles: function (type, page, current) {
                        //修改鼠标悬停title为中文
                        switch (type) {
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return "第" + page + "页";
                        }
                    },
                    itemTexts: function (type, page, current) {
                        //修改按钮文字为中文
                        switch (type) {
                            case "first":
                                return "首页";
                            case "prev":
                                return "上一页";
                            case "next":
                                return "下一页";
                            case "last":
                                return "末页";
                            case "page":
                                return page;
                        }
                    },
                    onPageChanged: function (event, oldPage, newPage) {
                        //Ajax来刷新整个list列表
                        callback(newPage - 1);
                    }
                };
                //设置分页插件
                $(selector).bootstrapPaginator(pageOptions);
            };

            $rootScope.global_configs = {
                isEnabled: [
                    {key: 0, name: '不启用'},
                    {key: 1, name: '启用'}
                ],
                product_types: {
                    credit_load: '信用贷',
                    mortgage_load: '抵押',
                    pledge_load: '质押'
                },
                repayment_types: {
                    dengerbenxi: '等额本息',
                    dengerbenjin: '等额本金',
                    xianxihouben: '先息后本'
                },
                customer_types: {
                    personal: '个人',
                    enterprise: '企业'
                },
                time_units: {
                    day: '天',
                    week: '周',
                    month: '月'
                }
            };

            // add 'ie' classes to html
            var isIE = !!navigator.userAgent.match(/MSIE/i);
            if (isIE) {
                angular.element($window.document.body).addClass('ie');
            }
            if (isSmartDevice($window)) {
                angular.element($window.document.body).addClass('smart')
            }
            // config
            $scope.app = {
                name: '驿贷',
                version: '1.0.0',
                // for chart colors
                color: {
                    primary: '#7266ba',
                    info: '#23b7e5',
                    success: '#27c24c',
                    warning: '#fad733',
                    danger: '#f05050',
                    light: '#e8eff0',
                    dark: '#3a3f51',
                    black: '#1c2b36'
                },
                settings: {
                    themeID: 13,
                    navbarHeaderColor: "bg-dark",
                    navbarCollapseColor: "bg-white-only",
                    asideColor: "bg-dark",
                    headerFixe: true,
                    asideFixed: true,
                    asideFolded: false,
                    asideDock: false,
                    container: false
                }
            };

            // save settings to local storage
            if (angular.isDefined($localStorage.settings)) {
                $scope.app.settings = $localStorage.settings;
            } else {
                $localStorage.settings = $scope.app.settings;
            }

            $scope.$watch('app.settings', function () {
                if ($scope.app.settings.asideDock && $scope.app.settings.asideFixed) {
                    // aside dock and fixed must set the header fixed.
                    $scope.app.settings.headerFixed = true;
                }
                // for box layout, add background image
                $scope.app.settings.container ? angular.element('html').addClass('bg') : angular.element('html').removeClass('bg');
                // save to local storage
                $localStorage.settings = $scope.app.settings;
            }, true);
            // angular translate
            $scope.lang = {isopen: false};

            $translate.use("getEntityInfo");

            function isSmartDevice($window) {
                // Adapted from http://www.detectmobilebrowsers.com
                var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
                // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
                return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
            }


        }
    ]
)
    .run([
        '$rootScope',
        '$state',
        '$stateParams',
        '$templateCache',
        '$log',
        function ($rootScope, $state, $stateParams, $templateCache, $log) {


            $rootScope.$state       = $state;
            $rootScope.$stateParams = $stateParams;
//                $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
//                    $log.debug('successfully changed states');
//
//                    $log.debug('event', event);
//                    $log.debug('toState', toState);
//                    $log.debug('toParams', toParams);
//                    $log.debug('fromState', fromState);
//                    $log.debug('fromParams', fromParams);
//                });
//
//                $rootScope.$on('$stateNotFound', function (event, unfoundState, fromState, fromParams) {
//                    $log.error('The request state was not found: ' + unfoundState);
//                });
//
//                $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
//                    $log.error('An error occurred while changing states: ' + error);
//
//                    $log.debug('event', event);
//                    $log.debug('toState', toState);
//                    $log.debug('toParams', toParams);
//                    $log.debug('fromState', fromState);
//                    $log.debug('fromParams', fromParams);
//                });

            //joshua: 禁止模板缓存
            $rootScope.$on('$routeChangeStart', function (event, next, current) {
                if (typeof (current) !== 'undefined') {
                    $templateCache.remove(current.templateUrl);
                }
            });

        }
    ]);