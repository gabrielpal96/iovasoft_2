$(document).ready(function () {
    $(document).keypress(function(e) {
        if(e.which == 13) {
            var searchText = $('#search').val();
            console.log(searchText);
            $.ajax({
                url: "/checkTag",
                type: 'GET',
                data: {tagName: searchText},
                success: function(tagExist){
                    if(tagExist == true){
                        location.href = '/tag/' + searchText;
                    }else {
                        location.href = '/error/401';
                    }
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }
    });
})