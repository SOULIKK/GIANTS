function delComment(stockId, boardId, commentId) {
    // alert(commentId);
    $.ajax({
        type: "POST",
        url: `/comment/delete/${commentId}`,
        data: {
            stockId: stockId,
            boardId: boardId
        },
        success: function (res) {
            location.reload();
        }
    })
}