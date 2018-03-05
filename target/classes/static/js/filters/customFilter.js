'use strict';
/* Filters */
// need load the moment.js to use this filter.
app.filter("fromNow", function () {
        return function (date) {
            return moment(date).fromNow();
        }
    })
    .filter('datetime', function () {
        return function (date) {
            if (parseInt(date) > 1400000) {
                date = (date + '000') * 1;
            }
            return moment(date).format('YYYY') + "年 " + moment(date).format('MM') +"月" + moment(date).format('DD') +"日 " + moment(date).format('HH:mm');
        };
    })
    .filter('toFixed', function () {
        return function (date) {
            return date.toFinite(2);
        };
    })
    .filter('toLocalDate', function () {
        return function (date) {
            if(date){
                if (parseInt(date) > 1400000) {
                    date = (date + '000') * 1;
                }
                return moment(date).format('YYYY MMMDo');
            }else{
                return "未填写或未知";
            }
        };
    })
    .filter("myJson", function () {
        return function (json) {
            if(json){
                return JSON.parse(json);
            }else{
                return "无";
            }
        }
    })
    .filter("nullToString", function(){
        return function(string){
            if(string){
                return string;
            }else{
                return "未填写或未知";
            }
        }
    })