'use strict';

/**
 * Config for the router
 */
angular.module('app')

    .config(
        ['$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 'MODULE_CONFIG',
            function ($stateProvider, $urlRouterProvider, JQ_CONFIG, MODULE_CONFIG) {

                $urlRouterProvider.otherwise('/dashboard');

                //工作台
                $stateProvider
                    .state('exceptions', {
                        url: '/exceptions',
                        templateUrl: 'tpl/exceptions/list.html'
                    })
                    .state('dashboard', {
                        url: '/dashboard',
                        templateUrl: 'tpl/dashboards/welcome.html'
                    })
                    .state('notices', {
                        url: '/notices',
                        templateUrl: "tpl/layouts/notice.html"
                    })
                    .state('users', {
                        url: '/users',
                        templateUrl: "tpl/layouts/users.html"
                    })
                    .state('users.user', {
                        url: '/{id}',
                        templateUrl: "tpl/entities/users/home.html"
                    })
                    .state('users.user.page', {
                        url: '/{entity_type}/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('users.create', {
                        url: '/create',
                        templateUrl: "tpl/entities/users/create.html"
                    })
                    .state('affairs', {
                        url: '/affairs',
                        templateUrl: "tpl/layouts/affairs.html"
                    })
                    .state('affairs.entity', {
                        url: '/:entity_type/:entity_id',
                        views: {
                            '': {
                                templateUrl: function ($stateParams) {
                                    return 'tpl/entities/' + $stateParams.entity_type + '/actlist.html';
                                }
                            },
                            'entity': {
                                templateUrl: function ($stateParams) {
                                    return 'tpl/entities/' + $stateParams.entity_type + '/page.html';
                                }
                            }
                        }
                    })
                    .state('messages', {
                        url: '/messages',
                        templateUrl: "tpl/layouts/message.html",
                        resolve: load(['js/app/message/message.js'])
                    })
                    .state('messages.GROUP', {
                        url: '/GROUP/:session_id',
                        templateUrl: "tpl/layouts/message_group.html"
                    })
                    .state('messages.C2C', {
                        url: '/C2C/:session_id',
                        cache: true,
                        templateUrl: 'tpl/layouts/message_C2C.html',
                        resolve: load(['js/app/message/message.js'])
                    })
                    .state('stores', {
                        url: '/stores',
                        templateUrl: "tpl/layouts/stores.html"
                    })
                    .state('stores.store', {
                        url: '/{id}',
                        templateUrl: "tpl/entities/stores/home.html"
                    })
                    .state('stores.store.page', {
                        url: '/{entity_type}/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('stores.create', {
                        url: '/create',
                        templateUrl: "tpl/entities/stores/create.html"
                    })
                    .state('distributors', {
                        url: '/distributors',
                        templateUrl: "tpl/layouts/distributors.html"
                    })
                    .state('distributors.distributor', {
                        url: '/{id}',
                        templateUrl: "tpl/entities/distributors/home.html"
                    })
                    .state('distributors.distributor.page', {
                        url: '/{entity_type}/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('distributors.create', {
                        url: '/create',
                        templateUrl: "tpl/entities/distributors/create.html"
                    });


                //业务系统
                $stateProvider
                    .state('orders', {
                        url: '/orders',
                        templateUrl: "tpl/layouts/orders.html"
                    })
                    .state('orders.create', {
                        url: '/create',
                        templateUrl: "tpl/entities/orders/create.html"
                    })
                    .state('orders.list', {
                        url: '/{status}',
                        templateUrl: "tpl/entities/orders/list.html"
                    })
                    .state('orders.order', {
                        url: '/{id}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/orders/home.html";
                        }
                    })
                    .state('orders.order.page', {
                        url: '/{entity_type}/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/" + $stateParams.entity_type + "/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('customers', {
                        url: '/customers',
                        templateUrl: "tpl/layouts/customers.html"
                    })
                    .state('customers.create', {
                        url: '/create',
                        templateUrl: "tpl/entities/customers/create.html"
                    })
                    .state('customers.list', {
                        url: '/{status}',
                        templateUrl: "tpl/entities/customers/list.html"
                    });

                //业务系统
                $stateProvider
                    .state('risks', {
                        url: '/risks',
                        templateUrl: "tpl/layouts/risks.html"
                    })
                    .state('risks.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/risks/" + $stateParams.view_type + ".html";
                        }
                    });


                //风控静态页面的测试路由start
                $stateProvider
                //个人风控查询菜单
                    .state('risksperson', {
                        url: '/risksperson',
                        templateUrl: "tpl/layouts/risksperson.html"
                    })
                    //个人风控查询的操作页面
                    .state('risksperson.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/riskcontrol/person/" + $stateParams.view_type + ".html";
                        }
                    })
                    //个人智能风控查询菜单
                    .state('riskspersonIntelligent', {
                        url: '/riskspersonIntelligent',
                        templateUrl: "tpl/layouts/riskspersonIntelligent.html"
                    })
                    //企业智能风控查询菜单
                    .state('risksentertpriseIntelligent', {
                        url: '/risksentertpriseIntelligent',
                        templateUrl: "tpl/layouts/risksentertpriseIntelligent.html"
                    })

                    //资产风控查询菜单
                    .state('risksasset', {
                        url: '/risksasset',
                        templateUrl: "tpl/layouts/risksasset.html"
                    })
                    //资产风控查询的操作页面
                    .state('risksasset.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/riskcontrol/asset/" + $stateParams.view_type + ".html";
                        }
                    })
                    //企业风控查询菜单
                    .state('risksentertprise', {
                        url: '/risksentertprise',
                        templateUrl: "tpl/layouts/risksentertprise.html"
                    })
                    //资产风控查询的操作页面
                    .state('risksentertprise.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/riskcontrol//entertprise/" + $stateParams.view_type + ".html";
                        }
                    });

                //哒哒报表
                $stateProvider
                //贷款统计报表
                    .state('loanReports', {
                        url: '/loanReports',
                        templateUrl: "tpl/layouts/loanReports.html"
                    })
                    .state('loanReports.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/loanReports/" + $stateParams.view_type + ".html";
                        }
                    })
                    //贷款统计报表
                    .state('clients', {
                        url: '/clients',
                        templateUrl: "tpl/layouts/clients.html"
                    })
                    .state('clients.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/clients/" + $stateParams.view_type + ".html";
                        }
                    })
                    //贷款统计报表
                    .state('partners', {
                        url: '/partners',
                        templateUrl: "tpl/layouts/partners.html"
                    })
                    .state('partners.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/partners/" + $stateParams.view_type + ".html";
                        }
                    })
                    //权限统计
                    .state('powers', {
                        url: '/powers',
                        templateUrl: "tpl/layouts/powers.html"
                    })
                    .state('powers.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/powers/" + $stateParams.view_type + ".html";
                        }
                    });

                //集团登录报表
                $stateProvider
                    .state('groupLoanReport', {
                        url: '/groupLoanReport',
                        templateUrl: "tpl/layouts/groupLoanReports.html"
                    })
                    .state('groupLoanReport.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/groupLoanReports/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('groupManageReport', {
                        url: '/groupManageReport',
                        templateUrl: "tpl/layouts/groupManageReports.html"
                    })
                    .state('groupManageReport.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/groupManageReports/" + $stateParams.view_type + ".html";
                        }
                    })
                    .state('groupOperateReport', {
                        url: '/groupOperateReport',
                        templateUrl: "tpl/layouts/groupOperateReports.html"
                    })
                    .state('groupOperateReport.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/groupOperateReports/" + $stateParams.view_type + ".html";
                        }
                    });

                //公司登录报表
                $stateProvider
                //部门报表
                    .state('comDepartReport', {
                        url: '/comDepartReport',
                        templateUrl: "tpl/layouts/comDepartReports.html"
                    })
                    .state('comDepartReport.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/comDepartReports/" + $stateParams.view_type + ".html";
                        }
                    })
                    //运营报表
                    .state('comOperate', {
                        url: '/comOperate',
                        templateUrl: "tpl/layouts/comOperateReports.html"
                    })
                    .state('comOperate.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/comOperateReports/" + $stateParams.view_type + ".html";
                        }
                    })
                    //产品报表
                    .state('comProductReport', {
                        url: '/comProductReport',
                        templateUrl: "tpl/layouts/comProductReports.html"
                    })
                    .state('comProductReport.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/comProductReports/" + $stateParams.view_type + ".html";
                        }
                    });

                //门店登录报表
                $stateProvider
                //产品报表
                    .state('storeProductReport', {
                        url: '/storeProductReport',
                        templateUrl: "tpl/layouts/storeProductReports.html"
                    })
                    .state('storeProductReport.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/storeProductReports/" + $stateParams.view_type + ".html";
                        }
                    });

                //资方登录报表
                $stateProvider
                    .state('manageFinanceReport', {
                        url: '/manageFinanceReport',
                        templateUrl: "tpl/layouts/manageReports.html"
                    })
                    .state('manageFinanceReport.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/manageReports/" + $stateParams.view_type + ".html";
                        }
                    });


                //财务系统
                $stateProvider
                    .state('widthdraws', {
                        url: '/widthdraws',
                        templateUrl: "tpl/layouts/widthdraws.html"
                    })
                    .state('widthdraws.list', {
                        url: '/{status}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/widthdraws/list.html";
                        }
                    })
                    .state('repayments', {
                        url: '/repayments',
                        templateUrl: "tpl/layouts/repayments.html"
                    })
                    .state('repayments.list', {
                        url: '/{status}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/repayments/list.html";
                        }
                    })
                    .state('lendouts', {
                        url: '/lendouts',
                        templateUrl: "tpl/layouts/lendouts.html"
                    })
                    .state('lendouts.list', {
                        url: '/{status}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/lendouts/list.html";
                        }
                    });

                $stateProvider
                    //管理系统
                    .state('investors', {
                        url: '/investors',
                        templateUrl: "tpl/layouts/investors.html"
                    });

                $stateProvider
                //工作流类开发
                    .state('workflowEntity', {
                        url: '/workflowEntity/workflows/{workflow_id}/{entity_key}',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/module.html";
                        }
                    })
                    .state('workflowEntity.list', {
                        url: '/list',
                        cache: false,
                        params:{
                            entity_key:null,
                            workflow_id:null
                        },
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/list.html";
                        }
                    })
                    .state('workflowEntity.form', {
                        url: '/{id}/form',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/form.html";
                        }
                    })
                    .state('workflowEntity.home', {
                        url: '/{id}',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/home.html";
                            return "template/" + $stateParams.entity_key + "/home.html";
                        }
                    })
                    .state('workflowEntity.home.profile', {
                        url: '/profile',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/profile.html";
                            return "template/" + $stateParams.entity_key + "/profile.html";
                        }
                    })
                    .state('workflowEntity.home.profile.info', {
                        url: '/info',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/info.html";
                            return "template/" + $stateParams.entity_key + "/info.html";
                        }
                    })
                    .state('workflowEntity.home.profile.form', {
                        url: '/update',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/form.html";
                        }
                    })
                    .state('workflowEntity.home.profile.page', {
                        url: '/{entity_key}/{view_type}',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/" + $stateParams.view_type + ".html";
                            // return  "template/entities/user/list.html";
                        }
                    });

                $stateProvider
                //审计类开发
                    .state('auditEntity', {
                        url: '/auditEntity/{entity_key}',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/module.html";
                        }
                    })
                    .state('auditEntity.list', {
                        url: '/list',
                        cache: false,
                        params:{
                            entity_key:null
                        },
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/list.html";
                        }
                    })
                    .state('auditEntity.form', {
                        url: '/{id}/form',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/form.html";
                        }
                    })
                    .state('auditEntity.home', {
                        url: '/{id}',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/home.html";
                            return "template/" + $stateParams.entity_key + "/home.html";
                        }
                    })
                    .state('auditEntity.home.profile', {
                        url: '/profile',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/profile.html";
                            return "template/" + $stateParams.entity_key + "/profile.html";
                        }
                    })
                    .state('auditEntity.home.profile.info', {
                        url: '/info',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            //return "template/entities/info.html";
                            return "template/" + $stateParams.entity_key + "/info.html";
                        }
                    })
                    .state('auditEntity.home.profile.form', {
                        url: '/update',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/form.html";
                        }
                    })
                    .state('auditEntity.home.profile.page', {
                        url: '/{entity_key}/{view_type}',
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "template/" + $stateParams.entity_key + "/" + $stateParams.view_type + ".html";
                            // return  "template/entities/user/list.html";
                        }
                    });

                $stateProvider
                    //工作流配置
                    .state('workflowEntityConfig', {
                        url: '/workflowEntityConfig/{entity_key}',
                        cache: false,
                        controller: "workflowEntityConfigModuleController",
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/entityConfigLayout.html";
                            //return  "template/entities/" + $stateParams.entity_type + "/module.html";
                        }
                    })
                    .state('workflowEntityConfig.acts', {
                        url: '/acts',
                        controller: "workflowEntityConfigActListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/actList.html";
                        }
                    })
                    .state('workflowEntityConfig.actCreate', {
                        url: '/act/create',
                        controller: "workflowEntityConfigActFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/actForm.html";
                        }
                    })
                    .state('workflowEntityConfig.actEdit', {
                        url: '/act/{act_id}',
                        controller: "workflowEntityConfigActFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/actForm.html";
                        }
                    })
                    .state('workflowEntityConfig.actRole', {
                        url: '/act/{act_id}/roles',
                        controller: "workflowEntityConfigActRoleController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/actRole.html";
                        }
                    })
                    .state('workflowEntityConfig.states', {
                        url: '/states',
                        controller: "workflowEntityConfigStateListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/stateList.html";
                        }
                    })
                    .state('workflowEntityConfig.stateCreate', {
                        url: '/state/create',
                        controller: "workflowEntityConfigStateFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/stateForm.html";
                        }
                    })
                    .state('workflowEntityConfig.stateEdit', {
                        url: '/state/{state_id}',
                        controller: "workflowEntityConfigStateFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/stateForm.html";
                        }
                    })
                    .state('workflowEntityConfig.stateAct', {
                        url: '/state/{state_id}/acts',
                        controller: "workflowEntityConfigStateActController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/stateAct.html";
                        }
                    })
                    .state('workflowEntityConfig.workflows', {
                        url: '/workflows',
                        controller: "workflowEntityConfigWorkflowListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/workflowList.html";
                        }
                    })
                    .state('workflowEntityConfig.workflowCreate', {
                        url: '/workflow/create',
                        controller: "workflowEntityConfigWorkflowFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/workflowForm.html";
                        }
                    })
                    .state('workflowEntityConfig.workflowEdit', {
                        url: '/workflow/{workflow_id}',
                        controller: "workflowEntityConfigWorkflowFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/workflowForm.html";
                        }
                    })
                    .state('workflowEntityConfig.workflowState', {
                        url: '/workflow/{workflow_id}/states',
                        controller: "workflowEntityConfigWorkflowStateController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/workflowEntityConfigs/workflowState.html";
                        }
                    });

                $stateProvider
                    //审计配置
                    .state('auditEntityConfig', {
                        url: '/auditEntityConfig/{entity_key}',
                        cache: false,
                        controller: "auditEntityConfigModuleController",
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/entityConfigLayout.html";
                            //return  "template/entities/" + $stateParams.entity_type + "/module.html";
                        }
                    })
                    .state('auditEntityConfig.acts', {
                        url: '/acts',
                        controller: "auditEntityConfigActListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/actList.html";
                        }
                    })
                    .state('auditEntityConfig.actCreate', {
                        url: '/act/create',
                        controller: "auditEntityConfigActFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/actForm.html";
                        }
                    })
                    .state('auditEntityConfig.actEdit', {
                        url: '/act/{act_id}',
                        controller: "auditEntityConfigActFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/actForm.html";
                        }
                    })
                    .state('auditEntityConfig.actRole', {
                        url: '/act/{act_id}/roles',
                        controller: "auditEntityConfigActRoleController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/actRole.html";
                        }
                    })
                    .state('auditEntityConfig.states', {
                        url: '/states',
                        controller: "auditEntityConfigStateListController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/stateList.html";
                        }
                    })
                    .state('auditEntityConfig.stateCreate', {
                        url: '/state/create',
                        controller: "auditEntityConfigStateFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/stateForm.html";
                        }
                    })
                    .state('auditEntityConfig.stateEdit', {
                        url: '/state/{state_id}',
                        controller: "auditEntityConfigStateFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/stateForm.html";
                        }
                    })
                    .state('auditEntityConfig.stateAct', {
                        url: '/state/{state_id}/acts',
                        controller: "auditEntityConfigStateActController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/auditEntityConfigs/stateAct.html";
                        }
                    });

                $stateProvider
                    //菜单类审计实体配置，最主要区别是“菜单有对应的可显示角色”/还有父菜单
                    .state('menuConfig', {
                        url: '/menuConfig/{entity_key}',
                        cache: false,
                        controller: "menuConfigModuleController",
                        templateUrl: function ($stateParams) {
                            return "tpl/menuConfigs/menuConfigLayout.html";
                        },
                        resolve: load(['js/controllers/menuConfigControllers.js', 'angularBootstrapNavTree'])
                    })
                    .state('menuConfig.create', {
                        url: '/create',
                        controller: "menuConfigFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/menuConfigs/form.html";
                        }
                    })
                    .state('menuConfig.edit', {
                        url: '/edit/{id}',
                        controller: "menuConfigFormController",
                        cache: false,
                        templateUrl: function ($stateParams) {
                            return "tpl/menuConfigs/form.html";
                        }
                    });

                $stateProvider
                    .state('products', {
                        url: '/products',
                        templateUrl: "tpl/layouts/products.html"
                    })
                    .state('products.product', {
                        url: '/{product_id}',
                        templateUrl: "tpl/entities/products/home.html"
                    })
                    .state('products.product.page', {
                        url: '/{view_type}',
                        templateUrl: function ($stateParams) {
                            return "tpl/entities/products/" + $stateParams.view_type + ".html";
                        }
                    });

                $stateProvider
                    .state('fees', {
                        url: '/fees',
                        templateUrl: "tpl/layouts/fees.html"
                    })
                    .state('fees.fee', {
                        url: '/{fee_id}',
                        templateUrl: "tpl/entities/fees/form.html"
                    })
                    .state('applications', {
                        url: '/applications',
                        templateUrl: "tpl/layouts/applications.html"
                    })
                    .state('applications.application', {
                        url: '/{application_id}',
                        templateUrl: "tpl/entities/applications/form.html"
                    })
                    .state('applications.new', {
                        url: '/new',
                        templateUrl: "tpl/entities/applications/form.html"
                    });

                function load(srcs, callback) {
                    return {
                        deps: ['$ocLazyLoad', '$q',
                            function ($ocLazyLoad, $q) {
                                var deferred = $q.defer();
                                var promise  = false;
                                srcs         = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                                if (!promise) {
                                    promise = deferred.promise;
                                }
                                angular.forEach(srcs, function (src) {
                                    promise = promise.then(function () {
                                        if (JQ_CONFIG[src]) {
                                            return $ocLazyLoad.load(JQ_CONFIG[src]);
                                        }
                                        angular.forEach(MODULE_CONFIG, function (module) {
                                            if (module.name == src) {
                                                name = module.name;
                                            } else {
                                                name = src;
                                            }
                                        });
                                        return $ocLazyLoad.load(name);
                                    });
                                });
                                deferred.resolve();
                                return callback ? promise.then(function () {
                                    return callback();
                                }) : promise;
                            }]
                    }
                }
            }
        ]
    );