/**
 * Created by win7 on 2017/9/7.
 */
//console.log(location)
if(window.location.href.indexOf('www.edairisk.com') >0 ){  //正式服务器
    console.log('ok')
     window.config={
        location : 'www.edairisk.com',
        appid:'wxdd8a90dc085f4106',
        link:'https://appserv.beidoujinfu.com',
        zcName:'驿贷网络',
        ptproductId:'w2479f7092b411e7bd1bbcab9e9fd906',
        productName:'快递网点贷'
    }
}else{
    window.config={
        //location : 'demo.xiaojinpingtai.com',
        location : 'test.edairisk.com',
        appid:'wx2c7242fa52dfd7bc',
        link:'http://101.200.155.227:8186',
        zcName:'红创金服',
        ptproductId:'v80c85f0622811e7bf6cc121982d4a1a',
        productName:'红创小钱包'
    }
}