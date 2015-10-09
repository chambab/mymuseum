
function log(msg){
	var log = document.getElementById("log");
	if(log) log.innerHTML += msg + "<br>";
}
function log(target, msg){
	var log = document.getElementById(target);
	if(log) log.innerHTML += msg + "<br>";
}
function innerlog(msg){
	var log = document.getElementById("log");
	if(log) log.innerHTML = msg;
}
function innerlog(target, msg){
	var log = document.getElementById(target);
	if(log) log.innerHTML = msg;
}
//현재 날짜를 문자열로 반환
function dateToString(){
	var date = new Date();
	var result = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	return result;
}

// JSON으로 변경하기
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/*//
//	sessionStorage에 값 담고 쓰기
var UserContainer = Class.create({
	initialize: function(){},
	setParam: function(key, value) {
		sessionStorage.setItem(key, value);
	},
	getParam: function(key){
		return sessionStorage.getItem(key);
	}
});*/

function setParam(key, value){
	window.sessionStorage.setItem(key, value);
};
function getParam(key){
	return window.sessionStorage.getItem(key);
};
// 홈으로 이동하기
function fnGohome(){
	var strLocation = "/index.jsp";
	parent.window.location = strLocation;
};

// 날짜 수정
function fnDay(regDate){
	var result = "";
	
	var date = new Date();
	var yyy = date.getFullYear();
	var mm = date.getMonth()+1;
	var dd = date.getDate();
	var hh = date.getHours();
	var mi = date.getMinutes();
	var sec = date.getSeconds();
	
	var oyyy = regDate.substr(0,4);
	var omm = regDate.substr(4,2);
	var odd = regDate.substr(6,2);
	var ohh = regDate.substr(8,2);
	var omi = regDate.substr(10,2);
	var osec = regDate.substr(12,2);
	
	if(yyy == oyyy && mm == omm && dd == odd && parseInt(hh) == parseInt(ohh)) {
		result = (parseInt(mi) - parseInt(omi));
		result += " 분전";
	} else if(yyy == oyyy && mm == omm && dd == odd && parseInt(hh) != parseInt(ohh)){
		result = parseInt(hh) - parseInt(ohh);
		result += " 시간전";
	} else if(yyy == oyyy){
		result = omm + "." + odd + " " + ohh + ":" + omi;
	} else {
		result = oyyy + "." + omm + "." + odd + " " + ohh + ":" + omi;
	};
	return result;
};
