(function (T, h, i, n, k, P, a, g, e) {
    g = function () {
        P = h.createElement(i);
        a = h.getElementsByTagName(i)[0];
        P.src = k;
        P.charset = "utf-8";
        P.async = 1;
        a.parentNode.insertBefore(P, a)
    };
    T["ThinkPageWeatherWidgetObject"] = n;
    T[n] || (T[n] = function () {
        (T[n].q = T[n].q || []).push(arguments)
    });
    T[n].l = +new Date();
    if (T.attachEvent) {
        T.attachEvent("onload", g)
    } else {
        T.addEventListener("load", g, false)
    }
}(window, document, "script", "tpwidget", "//widget.seniverse.com/widget/chameleon.GlobalJs"))
tpwidget("init", {
    "flavor": "bubble",
    "location": "WX4FBXXFKE4F",
    "geolocation": "enabled",
    "position": "bottom-right",
    "margin": "300px 0px",
    "language": "zh-chs",
    "unit": "c",
    "theme": "chameleon",
    "uid": "UBD3C262D5",
    "hash": "754823c8768b336999bc5259c9fe4aac"
});
tpwidget("show");