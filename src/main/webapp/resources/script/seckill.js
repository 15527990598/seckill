// 存放主要交互逻辑代码
// javascript模块化
var seckill={
    // 封装秒杀相关ajax的url
    URL:{
        now : function(){
            return "/seckill/time/now";
        },
        exposer : function(seckillId){
            return "/seckill/"+seckillId+"/exposer";
        },
        execution : function(seckillId,md5){
            return "/seckill/"+seckillId+"/"+md5+"/execution";
        }
    },
    handleSeckillkill : function(seckillId,node){
        // 获取秒杀地址，控制实现逻辑，执行秒杀操作
        node.hide().html('<buttton class="btn btn-primary btn-lg" id="killBtn">开始秒杀</buttton>');// 秒杀按钮
        $.post(seckill.URL.exposer(seckillId),{},function(result){
            // 在回调函数中执行交互流程
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    // 开启秒杀
                    // 获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log("killUrl="+killUrl);
                    $("#killBtn").one('click',function(){
                        // 只绑定一次事件，执行秒杀请求
                        // 1.先禁用按钮
                        $(this).addClass("disabled");
                        // 2.发送秒杀请求、
                        $.post(killUrl,{},function(result){
                            var killResult = result['data'];
                            var state = killResult['state'];
                            var stateInfo = killResult['stateInfo'];
                            if(result && result['success']){
                                // 3.显示秒杀结果
                                node.html('<span  class="label label-success">'+stateInfo+'</span>');
                            }else{
                                // 秒杀失败
                                node.html('<span  class="label label-danger">'+stateInfo+'</span>');
                            }
                            console.log("seckillId="+seckillId+" "+"stateInfo="+stateInfo);
                        });
                    });
                    node.show();
                }else{
                    // 未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    // 重新倒计时
                    seckill.countdown(seckillId,now,start,end);
                }
            }else{
                console.log("result="+result);
            }
        });
    },
    // 验证手机号
    validataPhone:function(phone){
        if(phone && phone.length==11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    // 计时交互
    countdown : function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $("#seckill-box");
        // 时间判断
        if(nowTime>endTime){
            seckillBox.html("秒杀结束！");
        }else if(nowTime<startTime){
            // 秒杀未开始，倒计时
            var killTime = new Date(startTime+1000);
            // countdown.js提供的倒计时方法
            seckillBox.countdown(killTime,function(event){
                // 控制时间格式
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown',function(){
                // 倒计时结束后回调事件
                // 秒杀开始
                seckill.handleSeckillkill(seckillId,seckillBox);

            });
        }else{
            // 秒杀开始
            seckill.handleSeckillkill(seckillId,seckillBox);
        }
    },
    // 详情页秒杀逻辑
    detail:{
        // 详情页初始化
        init:function(params){
            // 手机验证和登陆，计时
            // 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            // 验证手机号
            if(!seckill.validataPhone(killPhone)){
                // 验证出错，显示弹出层
                $('#killPhoneModal').modal({
                    show : true,
                    backdrop : false,// 禁止位置关闭 /*亲测：官方文档说可以使用'static'，但是测试却不行，花了1个多小时才发现*/
                    keyboard : false// 关闭键盘事件
                });
                // 绑定手机号
                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    if(seckill.validataPhone(inputPhone)){
                        // 将电话写入cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        // 刷新页面
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300)
                    }
                });

            }
            // 已经登录
            // 及时交互
            $.get(seckill.URL.now(),{},function(result){
                if(result && result['success']){
                    var nowTime = result['data'];
                    // 时间判断,计时交互
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                }else{
                    console.log("result="+result);
                }
            });
        }
    }
}