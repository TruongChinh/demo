function showReport(e) {
    let val = $(e).val();
    if (val > 20) {
        val = 1;
    } else if (val < 1) {
        val = 20;
    }
    $(e).val(val);

    initData(val);
}

initData(10);


function initData(pageQty) {
    showLoading();
    axios.get("/api/scan/report/" + pageQty).then(function (res) {
        console.log('res',res);
        if (res.data.http == 200) {
            $('#reportDetail').remove();
            $('#chartWrapper').append(`<canvas id="reportDetail" height="250px"></canvas>`);
            let data = res.data.data;
            initChart('reportDetail', 'bar', data.key, data.value, getColors(data.key.length));
            // $('#reportDetail').css("height", "500px");
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

