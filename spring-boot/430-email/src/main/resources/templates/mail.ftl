<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="SpringBoot thymeleaf"/>
    <meta name="author" content="YiHui"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>邮件模板</title>
</head>
<style>
    .title {
        color: #c00;
        font-weight: normal;
        font-size: 2em;
    }

    .content {
        color: darkblue;
        font-size: 1.2em;
    }

    .sign {
        color: lightgray;
        font-size: 0.8em;
        font-style: italic;
    }
</style>
<body>

<div>
    <div class="title">${title}</div>
    <div class="content">${content}</div>
</div>
</body>
</html>
