// 补零函数
function fnW(num) {
    return num < 10 ? '0' + num : num;
}

//中国地图开始
//var china_map =echarts.init(document.getElementById("china_map"),'macarons'); 
var china_map =echarts.init(document.getElementById("china_map"),'infographic');
//var china_map =echarts.init(document.getElementById("china_map"),'shine'); 
function updateCharDataArea(data){
    var mydata = [
        {name: '北京',value: data.data["北京"] },{name: '天津',value: data.data["天津"] },
        {name: '上海',value: data.data["上海"] },{name: '重庆',value: data.data["重庆"] },
        {name: '河北',value: data.data["河北"] },{name: '河南',value: data.data["河南"] },
        {name: '云南',value: data.data["云南"] },{name: '辽宁',value: data.data["辽宁"] },
        {name: '黑龙江',value: data.data["黑龙江"] },{name: '湖南',value: data.data["湖南"]},
        {name: '安徽',value: data.data["安徽"] },{name: '山东',value: data.data["山东"] },
        {name: '新疆',value: data.data["新疆"] },{name: '江苏',value: data.data["江苏"] },
        {name: '浙江',value: data.data["浙江"] },{name: '江西',value: data.data["江西"] },
        {name: '湖北',value: data.data["湖北"] },{name: '广西',value: data.data["广西"] },
        {name: '甘肃',value: data.data["甘肃"] },{name: '山西',value: data.data["山西"] },
        {name: '内蒙古',value: data.data["内蒙古"] },{name: '陕西',value: data.data["陕西"]},
        {name: '吉林',value: data.data["吉林"] },{name: '福建',value: data.data["福建"] },
        {name: '贵州',value: data.data["贵州"] },{name: '广东',value: data.data["广东"] },
        {name: '青海',value: data.data["青海"] },{name: '西藏',value: data.data["西藏"] },
        {name: '四川',value: data.data["四川"] },{name: '宁夏',value: data.data["宁夏"] },
        {name: '海南',value: data.data["海南"] },{name: '台湾',value: data.data["台湾"] },
        {name: '香港',value: data.data["香港"] },{name: '澳门',value: data.data["澳门"] }
    ];

    var option = {
        //backgroundColor: '#FFFFFF',

        title: {
            text: '学生生源分析',
            textStyle: {
                color: '#cdddf7',
                fontSize: 32, // 增大字体
                fontWeight: 'bold', // 加粗字体
                textShadowColor: 'rgba(0, 0, 0, 0.5)', // 添加阴影颜色
                textShadowBlur: 4, // 设置阴影模糊半径
                textShadowOffsetX: 2, // 设置阴影的X偏移量
                textShadowOffsetY: 2  // 设置阴影的Y偏移量
            },
            x: 'center', // 标题居中
            y: 'top', // 设置标题靠上显示
            padding: [20, 0, 0, 0] // 调整标题的内边距，使其与顶部保持距离
        },


        tooltip : {
            trigger: 'item'
        },
        visualMap: {
            show : false,
            x: 'left',
            y: 'bottom',
            //layoutCenter:['30%','30%'],
            splitList: [
                {start: 500, end:600},{start: 400, end: 500},
                {start: 300, end: 400},{start: 200, end: 300},
                {start: 100, end: 200},{start: 0, end: 100},
            ],
            color: ['#ff0', '#ffff00', '#0E94EB','#6FBCF0', '#F0F06F', '#00CC00']
        },
        series: [{
            name: '学生生源分析',
            type: 'map',
            mapType: 'china',
            roam: true,
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: false
                }
            },
            data:mydata
        }]
    };

    china_map.setOption(option);
}
function fetchStudentArea(){
    $.ajax({
        url:'http://localhost:8080/Bigdata/province',
        method:'GET',
        success:function (data){
            if(data.code==200) {
                updateCharDataArea(data);
            }
        },
        error:function (xhr,status,error){
            console.error("取回数据失败",error);
        }
    });
}
fetchStudentArea();
setInterval(fetchStudentArea,5000);
//中国地图结束







// 获取当前时间
var timer = setInterval(function () {
    var date = new Date();
    var year = date.getFullYear(); // 当前年份
    var month = date.getMonth(); // 当前月份
    var data = date.getDate(); // 天
    var hours = date.getHours(); // 小时
    var minute = date.getMinutes(); // 分
    var second = date.getSeconds(); // 秒
    var day = date.getDay(); // 获取当前星期几

    // 星期几的中文映射
    var weekDays = ['日', '一', '二', '三', '四', '五', '六'];
    var ampm = hours < 12 ? 'am' : 'pm';

    $('#time').html(fnW(hours) + ":" + fnW(minute) + ":" + fnW(second));
    $('#date').html('<span>' + year + '/' + (month + 1) + '/' + data + '</span><span>' + ampm + '</span><span>周' + weekDays[day] + '</span>');

}, 1000);




//新生男女比例分析占比，带边框效果的饼图
//var pie_fanzui =echarts.init(document.getElementById("pie_fanzui"),'macarons'); 
var pie_fanzui =echarts.init(document.getElementById("pie_fanzui"),'infographic');
function updateCharDataSex(data){
    // 格式化数据
    option = {
        title: {
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['男生', '女生'],
            textStyle: { color: '#fff' }
        },
        label: {
            normal: {
                textStyle: {
                    color: 'red'  // 改变标示文字的颜色
                }
            }
        },
        series: [
            {
                name: '新生男女比例分析',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    { value: data.data["男"], name: '男生' },
                    { value: data.data["女"], name: '女生' }
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    pie_fanzui.setOption(option);
}
function fetchStudentSex(){
    $.ajax({
        url:'http://localhost:8080/Bigdata/sex',
        method:'GET',
        success:function (data){
            if(data.code==200) {
                updateCharDataSex(data);
            }
        },
        error:function (xhr,status,error){
            console.error("取回数据失败",error);
        }
    });
}
fetchStudentSex();
setInterval(fetchStudentSex,5000);




//新生入学报道专业分析占比，带边框效果的饼图
//var pie_age =echarts.init(document.getElementById("pie_age"),'macarons'); 
var pie_age =echarts.init(document.getElementById("pie_age"),'infographic');
//var pie_age =echarts.init(document.getElementById("pie_age"),'shine');
function updateCharDataMajor(data){
    option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['软件工程专业','大学数学专业','计算机专业','机械专业','土木专业','其他专业'],
            textStyle: {color: '#fff'}
        },
        series: [
            {
                name:'新生专业分布',
                type:'pie',
                radius: ['30%', '55%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '20',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:data.data["软件工程专业"], name:'软件工程专业'},
                    {value:data.data["数学专业"], name:'数学专业'},
                    {value:data.data["计算机专业"], name:'计算机专业'},
                    {value:data.data["机械专业"], name:'机械专业'},
                    {value:data.data["土木专业"], name:'土木专业'},
                    {value:data.data["其他专业"], name:'其他专业'}
                ]
            }
        ]
    };
    pie_age.setOption(option);
}

function fetchStudentMajor(){
    $.ajax({
        url:'http://localhost:8080/Bigdata/major',
        method:'GET',
        success:function (data){
            if(data.code==200) {
                updateCharDataMajor(data);
            }
        },
        error:function (xhr,status,error){
            console.error("取回数据失败",error);
        }
    });
}
fetchStudentMajor();
setInterval(fetchStudentMajor,5000);

//----------------------新生入学报道专业分析占比end---------------



//===================人口出入时间段统计=======================
//var line_time =echarts.init(document.getElementById("line_time"),'shine'); 
var line_time =echarts.init(document.getElementById("line_time"),'macarons');
//var line_time =echarts.init(document.getElementById("line_time"),'infographic'); 
var option = {
    // 给echarts图设置背景色
    //backgroundColor: '#FBFBFB',  // -----------> // 给echarts图设置背景色
    color: ['#7FFF00'],
    tooltip: {
        trigger: 'axis'
    },

    grid:{
        x:40,
        y:30,
        x2:5,
        y2:20

    },
    calculable: true,


    xAxis: [{
        type: 'category',
        data: ['6:00-9:00', '10:00-12:00', '13:00-15:00', '16:00-20:00', '21:00-24:00'],
        axisLabel: {
            color: "#7FFF00" //刻度线标签颜色
        }
    }],
    yAxis: [{

        type: 'value',
        axisLabel: {
            color: "#7FFF00" //刻度线标签颜色
        }
    }],
    series: [{
        name: '人次',
        type: 'line',
        data: [800, 300, 500, 800, 300, 600],

    }]
};


line_time.setOption(option);


//=========违法犯罪人员地区分布开始=======================
//var qufenbu_data =echarts.init(document.getElementById("qufenbu_data"),'shine'); 
//var qufenbu_data =echarts.init(document.getElementById("qufenbu_data"),'macarons'); 
var qufenbu_data =echarts.init(document.getElementById("qufenbu_data"),'infographic');
function updateCharDataBuild(data){
    option = {
        color: ['#FADB71'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            x:30,
            y:10,
            x2:15,
            y2:20
        },
        xAxis : [
            {
                type : 'category',
                data : ['兰园', '竹园', '梅园', '松园'],
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel: {
                    color: "#FADB71" //刻度线标签颜色
                }
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel: {
                    color: "#FADB71" //刻度线标签颜色
                }
            }
        ],
        series : [
            {
                name:'地区分布',
                type:'bar',
                barWidth: '60%',
                data:[data.data["兰园"],data.data["竹园"],data.data["梅园"],data.data["松园"]]
            }
        ]
    };

    qufenbu_data.setOption(option);
}
function fetchStudentBuild(){
    $.ajax({
        url:'http://localhost:8080/Bigdata/dormit',
        method:'GET',
        success:function (data){
            if(data.code==200) {
                updateCharDataBuild(data);
            }
        },
        error:function (xhr,status,error){
            console.error("取回数据失败",error);
        }
    });
}
fetchStudentBuild();
setInterval(fetchStudentBuild,5000);
//=========违法犯罪人员地区分布结束=======================


//时间选择器
var startV = '';
var endV = '';
laydate.skin('danlan');
var startTime = {
    elem: '#startTime',
    format: 'YYYY-MM-DD',
    min: '1997-01-01', //设定最小日期为当前日期
    max: laydate.now(), //最大日期
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        startV = datas;
        endTime.min = datas; //开始日选好后，重置结束日的最小日期
    }
};
var endTime = {
    elem: '#endTime',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: laydate.now(),
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        //        startTime.max = datas; //结束日选好后，重置开始日的最大日期
        endV = datas;
    }
};

laydate(startTime);
laydate(endTime);

//时间选择器
var startVs = '';
var endVs = '';
laydate.skin('danlan');
var startTimes = {
    elem: '#startTimes',
    format: 'YYYY-MM-DD',
    min: '1997-01-01', //设定最小日期为当前日期
    max: '2099-06-16', //最大日期
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        startVs = datas;
        endTimes.min = datas; //开始日选好后，重置结束日的最小日期
        setQgData($('#barTypes').parent().parent(), 1);
    }
};
var endTimes = {
    elem: '#endTimes',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: laydate.now(),
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        //        startTime.max = datas; //结束日选好后，重置开始日的最大日期
        endVs = datas;
        setQgData($('#barTypes').parent().parent(), 1);
    }
};

laydate(startTimes);
laydate(endTimes);




//更改日期插件的样式
function dateCss() {
    var arr = $('#laydate_box').attr('style').split(';');
    var cssStr =
        'position:absolute;right:0;';
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].indexOf('top') != -1) {
            cssStr += arr[i];
        }
    }

    $('#laydate_box').attr('style', cssStr);
}


var workDate;
var time = {
    elem: '#times',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: laydate.now() + 30,
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        //        startTime.max = datas; //结束日选好后，重置开始日的最大日期
        workDate = datas;
    }
};

laydate(time);



