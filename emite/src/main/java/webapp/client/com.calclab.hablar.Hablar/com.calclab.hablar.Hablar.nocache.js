var eb='',rb='" for "gwt:onLoadErrorFn"',ob='" for "gwt:onPropertyErrorFn"',fc='"><\/script>',Eb='.cache.html',hc='/',ec='<script id="',dc="<script>com_calclab_hablar_Hablar.onInjectionDone('com.calclab.hablar.Hablar')<\/script>",lb='=',Db='BD13596652496CF4DAC51C7F6221F006',nb='Bad handler "',Fb='DOMContentLoaded',gc='SCRIPT',Bb='__gwt_marker_com.calclab.hablar.Hablar',ic='base',gb='clear.cache.gif',qb='com.calclab.hablar.Hablar',kb='content',yb='gecko',zb='gecko1_8',fb='gwt.hybrid',pb='gwt:onLoadErrorFn',mb='gwt:onPropertyErrorFn',jb='gwt:property',Cb='hosted.html?com_calclab_hablar_Hablar',xb='ie6',ac='iframe',jc='img',bc="javascript:''",hb='meta',wb='msie',ib='name',tb='opera',cc='position:absolute;width:0;height:0;border:none',vb='safari',Ab='unknown',sb='user.agent',ub='webkit';function com_calclab_hablar_Hablar(){var l=window,k=document,t=l.external,ab,w,q,p=eb,z={},db=[],F=[],o=[],C,E;if(!l.__gwt_stylesLoaded){l.__gwt_stylesLoaded={};}if(!l.__gwt_scriptsLoaded){l.__gwt_scriptsLoaded={};}function v(){try{return t&&(t.gwtOnLoad&&l.location.search.indexOf(fb)==-1);}catch(a){return false;}}
function y(){if(ab&&w){var c=k.getElementById(qb);var b=c.contentWindow;b.__gwt_initHandlers=com_calclab_hablar_Hablar.__gwt_initHandlers;if(v()){b.__gwt_getProperty=function(a){return r(a);};}com_calclab_hablar_Hablar=null;b.gwtOnLoad(C,qb,p);}}
function s(){var j,h=Bb,i;k.write(ec+h+fc);i=k.getElementById(h);j=i&&i.previousSibling;while(j&&j.tagName!=gc){j=j.previousSibling;}function d(b){var a=b.lastIndexOf(hc);return a>=0?b.substring(0,a+1):eb;}
;if(j&&j.src){p=d(j.src);}if(p==eb){var c=k.getElementsByTagName(ic);if(c.length>0){p=c[c.length-1].href;}else{var g=k.location;var e=g.href;p=d(e.substr(0,e.length-g.hash.length));}}else if(p.match(/^\w+:\/\//)){}else{var f=k.createElement(jc);f.src=p+gb;p=d(f.src);}if(i){i.parentNode.removeChild(i);}}
function D(){var f=document.getElementsByTagName(hb);for(var d=0,g=f.length;d<g;++d){var e=f[d],h=e.getAttribute(ib),b;if(h){if(h==jb){b=e.getAttribute(kb);if(b){var i,c=b.indexOf(lb);if(c>=0){h=b.substring(0,c);i=b.substring(c+1);}else{h=b;i=eb;}z[h]=i;}}else if(h==mb){b=e.getAttribute(kb);if(b){try{E=eval(b);}catch(a){alert(nb+b+ob);}}}else if(h==pb){b=e.getAttribute(kb);if(b){try{C=eval(b);}catch(a){alert(nb+b+rb);}}}}}}
function n(a,b){return b in db[a];}
function m(a){var b=z[a];return b==null?null:b;}
function cb(d,e){var a=o;for(var b=0,c=d.length-1;b<c;++b){a=a[d[b]]||(a[d[b]]=[]);}a[d[c]]=e;}
function r(d){var e=F[d](),b=db[d];if(e in b){return e;}var a=[];for(var c in b){a[b[c]]=c;}if(E){E(d,a,e);}throw null;}
F[sb]=function(){var d=navigator.userAgent.toLowerCase();var b=function(a){return parseInt(a[1])*1000+parseInt(a[2]);};if(d.indexOf(tb)!=-1){return tb;}else if(d.indexOf(ub)!=-1){return vb;}else if(d.indexOf(wb)!=-1){var c=/msie ([0-9]+)\.([0-9]+)/.exec(d);if(c&&c.length==3){if(b(c)>=6000){return xb;}}}else if(d.indexOf(yb)!=-1){var c=/rv:([0-9]+)\.([0-9]+)/.exec(d);if(c&&c.length==3){if(b(c)>=1008)return zb;}return yb;}return Ab;};db[sb]={gecko:0,gecko1_8:1,ie6:2,opera:3,safari:4};com_calclab_hablar_Hablar.onInjectionDone=function(){ab=true;y();};com_calclab_hablar_Hablar.onScriptLoad=function(){w=true;y();};s();D();var bb;if(v()){bb=Cb;}else{try{bb=Db;}catch(a){return;}bb+=Eb;}var B;function A(){if(!q){q=true;y();if(k.removeEventListener){k.removeEventListener(Fb,A,false);}if(B){clearInterval(B);}}}
var u;function x(){if(!u){u=true;var a=k.createElement(ac);a.src=bc;a.id=qb;a.style.cssText=cc;k.body.appendChild(a);a.src=p+bb;}}
if(k.addEventListener){k.addEventListener(Fb,function(){x();A();},false);}var B=setInterval(function(){if(/loaded|complete/.test(k.readyState)){x();A();}},50);k.write(dc);}
com_calclab_hablar_Hablar.__gwt_initHandlers=function(i,e,j){var d=window,g=d.onresize,f=d.onbeforeunload,h=d.onunload;d.onresize=function(a){try{i();}finally{g&&g(a);}};d.onbeforeunload=function(a){var c,b;try{c=e();}finally{b=f&&f(a);}if(c!=null){return c;}if(b!=null){return b;}};d.onunload=function(a){try{j();}finally{h&&h(a);}};};com_calclab_hablar_Hablar();