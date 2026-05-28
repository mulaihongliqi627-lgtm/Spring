function getLoginToken() {
    return localStorage.getItem("user_token");
}

function requireLogin() {
    if (!getLoginToken()) {
        alert("请先登录");
        location.href = "blog_login.html";
        return false;
    }
    return true;
}

$(document).ajaxSend(function (event, xhr) {
    let token = getLoginToken();
    if (token) {
        xhr.setRequestHeader("Authorization", token);
    }
});

$(document).ajaxError(function (event, xhr) {
    if (xhr.status === 401 || xhr.status === 403) {
        localStorage.removeItem("user_token");
        localStorage.removeItem("login_userid");
        location.href = "blog_login.html";
    }
});

function getUserInfo(url) {
    $.ajax({
        type: "get",
        url: url,
        success: function (result) {
            if (result != null && result.code === 200 && result.data != null) {
                let userInfo = result.data;
                $(".left .card h3").text(userInfo.userName || "");
                if (userInfo.githubUrl) {
                    $(".left .card a").attr("href", userInfo.githubUrl);
                }
            }
        }
    });
}

function logout() {
    //退出登录,删除token
    localStorage.removeItem("user_token");
    localStorage.removeItem("login_userid");
    location.href = "blog_login.html";
}

function getQueryParam(name) {
    return new URLSearchParams(location.search).get(name);
}
