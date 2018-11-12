$(function () {
    showMenu(1);

    var source = new DevExpress.data.DataSource({
        load: function () {
            $('#chartOne').dxChart('instance').showLoadingIndicator();
            return $.getJSON('./kgDay/all');
        },
        loadMode: 'raw'
    });

    var sourceWarehouse1 = new DevExpress.data.DataSource({
        load: function () {
            $('#chartOne').dxChart('instance').showLoadingIndicator();
            return $.getJSON('./one/warehouse?wId=1');
        },
        loadMode: 'raw'
    });
    var sourceWarehouse2 = new DevExpress.data.DataSource({
        load: function () {
            $('#chartOne').dxChart('instance').showLoadingIndicator();
            return $.getJSON('./one/warehouse?wId=2');
        },
        loadMode: 'raw'
    });
    var sourceWarehouse3 = new DevExpress.data.DataSource({
        load: function () {
            $('#chartOne').dxChart('instance').showLoadingIndicator();
            return $.getJSON('./one/warehouse?wId=3');
        },
        loadMode: 'raw'
    });

    $("#chartOne").dxChart({
        palette: "vintage",
        dataSource: source,
        commonSeriesSettings: {
            argumentField: "docDate",
            valueField: "sum",
            type: "bar"
        },
        seriesTemplate: {
            nameField: "name"
        },
        title: {
            text: "Dzienna ilość towaru w magazynie",
            subtitle: {
                text: "(w kilogramach)"
            }
        },
        legend: {
            verticalAlignment: "bottom",
            horizontalAlignment: "center"
        }
    });

    $("#chartTwo").dxPieChart({
        type: "doughnut",
        palette: "vintage",
        dataSource: sourceWarehouse1,
        title: "Ilość produktów w Magazynie 1",
        tooltip: {
            enabled: true
        },
        legend: {
            horizontalAlignment: "right",
            verticalAlignment: "top",
            margin: 0
        },
        series: [{
            argumentField: "product",
            valueField: "sum"
        }]
    });
    $("#chartThree").dxPieChart({
        type: "doughnut",
        palette: "pastel",
        dataSource: sourceWarehouse2,
        title: "Ilość produktów w Magazynie 2",
        tooltip: {
            enabled: true
        },
        legend: {
            horizontalAlignment: "right",
            verticalAlignment: "top",
            margin: 0
        },
        series: [{
            argumentField: "product",
            valueField: "sum"
        }]
    });
    $("#chartFour").dxPieChart({
        type: "doughnut",
        palette: "soft",
        dataSource: sourceWarehouse3,
        title: "Ilość produktów w Magazynie 3",
        tooltip: {
            enabled: true
        },
        legend: {
            horizontalAlignment: "right",
            verticalAlignment: "top",
            margin: 0
        },
        series: [{
            argumentField: "product",
            valueField: "sum"

        }]
    });
});

var menuData = [{
    id: 1,
    name: "Home"
}, {
    id: 2,
    name: "Magazyny"

}, {
    id: 3,
    name: "Produkty"
}, {
    id: 4,
    name: "Dostawcy"
}];

function showMenu(page) {

    menuData[page - 1].selected = true;

    $("#panelMenu").dxMenu({
        dataSource: menuData,
        displayExpr: "name",
        selectionMode: "single",
        selectionByClick: true,
        onItemClick: function (data) {
            switch (data.itemData.id) {
                case 2:
                    location.href = './warehouse';
                    break;
                case 3:
                    location.href = './product';
                    break;
                case 4:
                    location.href = './client';

                    break;
                default:
                    location.href = './home';

            }
        }

    })
};