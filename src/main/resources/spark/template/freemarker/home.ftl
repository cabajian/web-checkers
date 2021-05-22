<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <meta http-equiv="refresh" content="10">
        <title>${title} | Web Checkers</title>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
    </head>

    <body>
        <div class="page">

            <h1>Web Checkers</h1>

            <div class="navigation">
            <#if currentPlayer??>
                <a href="/">Home</a> |
                <a href="/signout">Sign out [${currentPlayer.name}]</a>
            <#else>
            <a href="/signin">Sign in</a>
            </#if>
                </div>

            <#if message??>
            <div id="message" class="${message.type}">${message.text}</div>
            </#if>

            <div class="body">
            <#if currentPlayer??>
                <p>Hello <span id="currentPlayer">${currentPlayer.name}</span>! Welcome to web checkers!</p>
                <div>
                    <form id="btn-form" action="./requestAI" method="POST">
                        <input type="hidden">
                            <button class= "button">
                                Play against Computer
                            </button>
                        </input>
                    </form>
                    <h3>Online players: </h3>
                    <ul id="player-list">
                        <#list playerList as players>
                            <li><a class="player">${players.name}</a></li>
                        </#list>
                    </ul>
                </div>
                <form id="start-form" action="./request" method="POST">
                    <input type="hidden" name="opponent"/>
                </form>

                <#else>
                <p>You are currently not signed in.</p>
                <p>Number of players currently: ${playerNum}</p>
                <a href="/signin">Sign in</a>
            </#if>
            </div>

        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="/js/custom.js"></script>

    </body>
</html>
