<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">

    <h1>Web Checkers</h1>

    <div class="navigation">
      <a href="/">my home</a>
    </div>

    <#if message??>
      <div id="message" class="${message.type}">${message.text}</div>
    </#if>

    <div class="body">
      <h2>Sign in</h2>
      <form action="./signin" method="POST">
        <label for="name">Enter a name: </label>
        <input id="name" type="text" name="username" />
        <input type="submit" value="Sign in" />
      </form>
    </div>

  </div>
</body>
</html>
