initData();

function initData() {
    showLoading();
    axios.get("/api/scan/listSummary").then(function (res) {
        console.log(res);
        if (res.data.http == 200) {
            let data = res.data.data;
            data.forEach(function (ele, idx) {
                $('#tableHis tbody').append(`<tr onclick="showDetail(` + ele.id + `,` + ele.pageId + `)" style="cursor: pointer">
                                <td>` + (idx + 1) + `</td>
                                <td> ` + ele.url + `</td>
                                <td>` + ele.scanDate + `</td>
                            </tr>`)

            });
            $('#tableHis').DataTable();
            stopLoading();
        } else {
            stopLoading();
            swal(
                'Error',
                res.data.data.message,
                'error'
            );
        }
    }, function (err) {
        stopLoading();
    })
}

function showDetail(id, pageId) {
    window.location.href = $('#goToDetail').attr('href') + '/' + id + '/' + pageId;
}