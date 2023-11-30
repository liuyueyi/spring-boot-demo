let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    // 首先进行登录
    const uname = $("#uname").val();
    // 第一步： 创建xhr对象
    let xhr = new XMLHttpRequest();
// 第二步： 调用open函数 指定请求方式 与URL地址
    xhr.open('GET', 'http://localhost:8080/login?name=' + uname, false);
// 第三步： 调用send函数 发起ajax请求
    xhr.send();

    // 其次建立ws链接
    const channel = $("#endpoint").val();
    // const socket = new SockJS('/ws/chat/' + channel);
    const socket = new SockJS('/ws/chat/channel');
    stompClient = Stomp.over(socket);

    stompClient.connect({"uname": $("#uname").val()}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        let topic = '/topic/chat/' + channel;
        // showGreeting("链接成功! 欢迎: " + $("#uname").val());
        console.log("订阅:", topic);
        stompClient.subscribe(topic, function (greeting) {
            // 表示这个长连接，订阅了 "/topic/hello" , 这样后端像这个路径转发消息时，我们就可以拿到对应的返回
            console.log("resp: ", greeting.body)
            showGreeting(greeting.body);
        });
    });
    socket.onclose = disconnect;
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    const channel = $("#endpoint").val();
    const headers = {
        'u-name': $("#uname").val(),
    };
    // 表示将消息转发到哪个目标，类似与http请求中的path路径，对应的是后端 @MessageMapping 修饰的方法
    stompClient.send("/app/hello/" + channel, headers, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").prepend("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
});