function expel() {
    console.log("Вы отписались!")
    $.ajax({
        method: "POST",
        url: '/expel',
        data: null,
        success: function(response) {
            location.reload();
        }
    });
    return false;
}

function enroll(id) {
    console.log("Вы подписались!")
    $.ajax({
        method: "POST",
        url: '/enroll/' + id,
        data: null,
        success: function(response) {
            location.reload();
        }
    });
    return false;
}

function fire(id) {
    $.ajax({
        method: "POST",
        url: '/admin/del/' + id,
        data: null,
        success: function(response) {
            location.reload();
        }
    });
    return false;
}
//$('#enroll').on('click', expel());