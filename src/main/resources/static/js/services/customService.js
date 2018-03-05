/*
 * 这是一个可以获取实体列表，详情，及关联数据的服务
 * 依赖 spring-data-rest-angular 详见https://guylabs.ch/portfolio/items/angular-spring-data-rest/
 * @author  金杭
 * */
app.service("entity",
    [
        "$rootScope",
        "$http",
        "$q",
        "SpringDataRestAdapter",
        function($rootScope, $http, $q, SpringDataRestAdapter){

            /*
             * 获取实体列表及关联数据
             * @author  金杭
             * @param   {string}   url     实体列表请求路由
             * @param   {array}    _links  实体关联数据
             * @return  {function} promise 承诺对象，是一个函数，在回调函数中可以处理数据
             * */
            this.getList = function(url, _links){

                var httpPromise = $http.get(url).success(function (resResult) {
                    //不用处理任何事
                });

                if(_links){
                    return SpringDataRestAdapter.process(httpPromise, _links).then(function (processedResponse) {
                        return processedResponse;
                    });
                }else{
                    return SpringDataRestAdapter.process(httpPromise).then(function (processedResponse) {
                        return processedResponse;
                    });
                }
                
            };

            /*
             * 获取实体详情及关联数据
             * @author  金杭
             * @param   {string}   url     实体详情请求路由
             * @param   {array}    _links  实体关联数据
             * @return  {function} promise 承诺对象，是一个函数，在回调函数中可以处理数据
             * */
            this.getDetail = function(url, _links){

                var httpPromise = $http.get(url).success(function (resResult) {
                    //不用处理任何事
                });

                if(_links){
                    return SpringDataRestAdapter.process(httpPromise, _links).then(function (processedResponse) {
                        return processedResponse;
                    });
                }else{
                    return SpringDataRestAdapter.process(httpPromise).then(function (processedResponse) {
                        return processedResponse;
                    });
                }

            };

            /*
             * 获取select选择框的数据
             * @author  金杭
             * @param   {array}   list    选择框的列表数据
             * @param   {object}  myOwn   自己的数据
             * @return  {object}  output  对比后列表的数据
             * */
            this.getSelectedMyOwn = function(list, myOwn){

                var output = null;

                if(angular.isObject(myOwn)){
                    angular.forEach(list,function(value, index){
                        if(value.id === myOwn.id){
                            output =  value;
                        }
                    });
                }

                return output;

            };

            /*
             * 获取自己选择的地区
             * @author  金杭
             * @param   {object}   全国地区对象，详见window.regions
             * @param   {string}   已选中的地区字符串中文字
             * */
            this.getSelectedRegion = function(regions, myRegion){

                var output = null;

                angular.forEach(regions.children, function(region, index){
                    if(region.text === myRegion){
                        output = region;
                    }
                });

                return output;

            };

            /*
             * 数据正则判断
             * @author  李斌
             * @param   {array}
             * @param   {object}
             * @return  {object}
             * */
            this.getRegular = function(type, val){

                var output = null;

                switch(type) {
                    /*身份证*/
                    case "identity":
                        if (!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test( val))){
                            output = true;
                        }
                        break;
                    /*手机号*/
                    case "mobile":
                        if (!(/^(?:13\d|15\d|18\d|14\d|17\d)-?\d{5}(\d{3}|\*{3})$/.test( val))){
                            output = true;
                        }
                        break;



                }

                return output;

            };




        }
    ]
);