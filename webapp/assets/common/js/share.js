String.prototype.startWith=function(str){
    var reg=new RegExp("^"+str);
    return reg.test(this);
}

String.prototype.endWith=function(str){
    var reg=new RegExp(str+"$");
    return reg.test(this);
}




function openWindow(url){
    var 	heigth=(window.screen.availHeight||728) -50;
    var     width=(window.screen.availWidth||1350)-20;
    window.open (url,'','height='+heigth+',width='+width+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no');
}