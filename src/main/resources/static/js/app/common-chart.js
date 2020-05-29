var background = [
    'rgba(255, 99, 132, 1)',
    'rgba(54, 162, 235, 1)',
    'rgba(255, 206, 86, 1)',
    'rgba(75, 192, 192, 1)',
    'rgba(153, 102, 255, 1)',
    'rgba(255, 159, 64, 1)',
    'rgb(255,65,28)',
    'rgb(255,45,137)',
    'rgb(255,27,245)',
    'rgb(182,24,255)',
    'rgb(138,25,255)',
    'rgb(69,12,255)'
]

var spinner;

function getLocalhost() {
    return window.location.host;
}

function initScanChart(scanResult, classesPoint, type, scanResultId, classesPointId) {
    initChart(scanResultId, type, scanResult.key, scanResult.value, [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)'
    ]);
    initChart(classesPointId, type, classesPoint.key, classesPoint.value, background);
}

function changeChart() {
    let click = $('#idSwitch').attr('data-click');
    click++;
    let child = $('#panelChart > div');
    for (var i = 0; i < child.length; i++) {
        if (click % 3 == i) $(child[i]).css('display', '');
        else $(child[i]).css('display', 'none');
    }
    $('#idSwitch').attr('data-click', click);
}

function showDetail() {
    if ($('#panelChart').css('display') === 'block') {
        $('#panelChart').css('display', 'none');
        $('#panelDetail').css('display', 'block');
        $('#idShowDetail')[0].innerHTML = 'Show chart scan';
        $('#idShowDetail').removeClass('btn-primary');
        $('#idShowDetail').addClass('btn-danger');
    } else {
        $('#panelChart').css('display', 'block');
        $('#panelDetail').css('display', 'none');
        $('#idShowDetail')[0].innerHTML = 'Show detail';
        $('#idShowDetail').removeClass('btn-danger');
        $('#idShowDetail').addClass('btn-primary');
    }
}

function initChart(id, type, key, value, backgroundColor) {
    var ctx = document.getElementById(id);
    var myChart = new Chart(ctx, {
        type: type,
        data: {
            labels: key,
            datasets: [{
                label: '# of Votes',
                data: value,
                backgroundColor: backgroundColor
            }]
        },
        options: getTypeChart(type)
    });
}

function getRandomForBaseLine() {
    return Math.floor(Math.random() * 5) + 90;
}

function initChartRadar(id, line1, line2) {
    var ctx = document.getElementById(id);

    var baseLine = [];
    for (let i = 0; i < 11; i++) {
        baseLine.push(getRandomForBaseLine());
    }

    var dataChart = {
        labels: line1.key,
        datasets: [{
            label: "Scan Result",
            backgroundColor: "rgba(200,0,0,0.2)",
            data: line1.value
        }, {
            label: "Average 10 Pages",
            backgroundColor: "rgba(0,0,200,0.2)",
            data: line2.value
        }, {
            label: "Base Line",
            backgroundColor: "rgba(58,200,18,0.72)",
            data: baseLine
        }]
    };

    var myChart = new Chart(ctx, {
        type: 'radar',
        data: dataChart
    });
}

function getTypeChart(type) {
    if (type === 'bar') {
        return {
            scaleShowValues: true,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        max: 100
                    }
                }],
                xAxes: [{
                    ticks: {
                        autoSkip: false
                    }
                }]
            },
            legend: {
                display: false
            },
            tooltips: {
                callbacks: {
                    label: function (tooltipItem) {
                        return tooltipItem.yLabel;
                    }
                }
            }
        };
    } else if (type === 'pie') {
        return '';
    } else if (type === 'radar') {
        return {
            scale: {
                angleLines: {
                    display: false
                },
                ticks: {
                    suggestedMin: 50,
                    suggestedMax: 100
                }
            }
        };
    }
}


function showLoading(mess) {
    if (mess) {
        $('body').loading({
            message: mess + '...'
        });
    } else {
        $('body').loading();
    }
    spinnerInit();
}

function stopLoading() {
    $('body').loading('stop');
    spinner.stop();
}

function setRate(e) {
    $(e)[0].innerHTML = 0;
}

function initData(path, data, mess) {
    showLoading(mess);
    axios.post(path, data).then(function (res) {
        console.log(res);
        if (res.data.http == 200) {
            $('.generalInfo').css('display', 'none');
            $('.resultScan').css('display', '');

            let scanData = res.data.data.scanData;
            let scanResult = scanData[0];
            let classesPoint = scanData[1];
            let classesPointAVG = scanData[2];

            let point = +scanResult.value[1];

            $('.titleSite')[0].innerHTML = 'Site : ' + scanData[3];
            $('#score')[0].innerHTML = 'Score :  ' + point.toFixed(2) + "/100";

            initScanChart(scanResult, classesPoint, 'bar', 'scanResultBar', 'classesPointBar');
            initScanChart(scanResult, classesPoint, 'pie', 'scanResultPie', 'classesPointPie');
            initChartRadar("classesPointRadar", classesPoint, classesPointAVG);

            let severity = res.data.data.severityDTO;
            let rateIssue = severity.rateIssue;
            let rateDetail = severity.rateDetail;
            rateDetail.forEach(function (ele, idx) {
                if (ele.rank == 1) {
                    $('#rate1')[0].innerHTML = ele.samples ? ele.samples.length : 0;
                } else if (ele.rank == 2) {
                    $('#rate2')[0].innerHTML = ele.samples ? ele.samples.length : 0;
                } else if (ele.rank == 3) {
                    $('#rate3')[0].innerHTML = ele.samples ? ele.samples.length : 0;
                } else if (ele.rank == 4) {
                    $('#rate4')[0].innerHTML = ele.samples ? ele.samples.length : 0;
                } else if (ele.rank == 5) {
                    $('#rate5')[0].innerHTML = ele.samples ? ele.samples.length : 0;
                }
            });
            for (var i = rateDetail.length - 1; i >= 0; i--) {
                var ele = rateDetail[i];
                var samples = ele.samples;
                if (samples.length != 0 || samples != null) {
                    $('#accordion').append(`
                    <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" style="color:#06274a" data-parent="#accordion" href="#collapse` + ele.rank + `">
                                         ` + getNameOfError(ele.rank) + `
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse` + ele.rank + `" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    
                                </div>
                            </div>
                        </div>`);
                    var idIssue = 'collapse' + ele.rank;
                    for (var j = 0; j < samples.length; j++) {
                        var sample = samples[j];
                        $('#' + idIssue + ' .panel-body').append(`<table>
                                        <tbody>
                                            <tr>
                                                <a href="#">` + sample.urlIssue + `</a>
                                                <span onclick="showTrace(` + sample.id + `)" style="cursor: pointer;color: #ff1bf5">
                                                [ show trace ]</span>
                                            </tr>
                                        </tbody>
                                    </table>
                        `);
                    }
                }
            }
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

function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function getColors(num) {
    let arr = [];
    for (let i = 0; i < num; i++) {
        arr.push(getRandomColor());
    }
    return arr;
}

function getNameOfError(rank) {
    if (rank == 5) return 'Critical Errors Detail';
    if (rank == 4) return 'High Errors Detail';
    if (rank == 3) return 'Medium Errors Detail';
    if (rank == 2) return 'Low Errors Detail';
    if (rank == 1) return 'Warning Detail';
}

function showTrace(id) {
    console.log(id);
    axios.get('/api/scan/sample/' + id).then(function (res) {
        console.log(res);
        if (res.data.http == 200) {
            let data = res.data.data;
            let content = '=== REQUEST ===\n';
            content += data.request + '\n\n';
            content += '=== RESPONSE ===' + '\n';
            content += data.response + '\n';
            // $('#showSampleDetail .modal-body')[0].innerHTML = content;
            $('#showSampleDetail .modal-body')[0].innerText = content;
            $('#showSampleDetail').modal();
        } else {
            swal(
                'Error',
                res.data.data.message,
                'error'
            );
        }
    });

}


function spinnerInit() {
    var opts = {
            lines: 13,
            length: 0,
            width: 14,
            radius: 42,
            scale: 1,
            corners: 1,
            color: '#7db8ff',
            opacity: 0.25,
            rotate: 0,
            direction: 1,
            speed: 1,
            trail: 60,
            fps: 20,
            zIndex: 2e9,
            className: 'spinner',
            top: '50%',
            left: '60%',
            shadow: false,
            hwaccel: false,
            position: 'fixed',
        };
        target = document.getElementsByTagName('body')[0];
        spinner = new Spinner(opts).spin(target);
}

