$(document).ready(function() {

    // on player click, attempt to start game
    $("#player-list .player").click(function() {
            //get current player and opponent names
            var currentPlayerName = $("#currentPlayer")[0].innerHTML;
            var opponentName = this.innerHTML;
            //if player clicks their own name, return without call
            if (currentPlayerName === opponentName) return;
            //update form value
            $('#start-form input[name="opponent"]').val(opponentName);
            //submit form
            $('#start-form').submit();
        });

});