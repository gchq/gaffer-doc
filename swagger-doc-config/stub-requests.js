(function(open) {
    XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
        if(url.indexOf("v2/") >= 0 && !url.endsWith(".json")) {
            url = url + ".json";
        }

        open.call(this, method, url, async, user, pass);
    };
})(XMLHttpRequest.prototype.open);
