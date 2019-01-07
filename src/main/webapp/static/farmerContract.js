var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});
$(function () {

    showMenuFarmer(2);

    $("#searchBox").dxTextBox({
        placeholder: "Wyszukaj",
        showClearButton: true,
        mode: "search",
        onEnterKey: function (o) {
            centreRefresh();
        }
    });

    showCentreGrid();
});
function showCentreGrid(){
    $.get('./farmer/user?userId='+userId, function (result) {

        var clintDataSource = new DevExpress.data.DataSource({

            load: function (loadOptions) {
                var params = {
                    p: $("#searchBox").dxTextBox('instance').option('value'),
                    farmerId: result.id
                };
                var d = $.Deferred();
                $.getJSON('./farmer/contract/search', {
                    p: params.p ? params.p : "",
                    farmerId: params.farmerId ? params.farmerId:-1,
                    page: loadOptions.skip / loadOptions.take,
                    size: loadOptions.take,
                    sort: sortString(loadOptions)
                }).done(function (result) {
                    d.resolve(result.content, {
                        totalCount: result.totalElements

                    });
                });
                return d.promise();
            }
        });


        /*show grid with clients in clint page*/
        $("#clientGrid").dxDataGrid({
            dataSource: clintDataSource,
            key: "id",
            columnAutoWidth: true,
            remoteOperations: {groupPaging: true},
            selection: {
                mode: "single"
            },
            scrolling: {
                "showScrollbar": "never"
            },
            paging: {
                pageSize: 3
            },
            pager: {
                showPageSizeSelector: true,
                allowedPageSizes: [3, 6, 12]
            },
            showBorders: true,

            hoverStateEnabled: true,

            columns: [{
                caption: "Skup",
                dataField: "vegetableCentreName"
            }, {
                caption: "Produkt",
                dataField: "productName"
            }, {
                caption: "Dostawca",
                dataField: "providerName"

            }, {
                caption: "Data rozpoczęcia",
                dataField: "startDate"

            }, {
                caption: "Data zakończenia",
                dataField: "endDate"

            }, {
                caption: "Ilość",
                dataField: "amount"

            },{
                caption: "Cena",
                dataField: "price"
            },{
                caption: "Status",
                dataField: "statusName"
            }]
        });
    });

};

function sortString(loadOptions) {

    if(!loadOptions.sort) return 'id,asc';

    var sort=loadOptions.sort[0].selector;
    var sort1= loadOptions.sort[0].desc===true?',desc':',asc';
    if (sort == "clientId"){
        sort = "client_id"
    }
    if (sort == "vegetableCentreId"){
        sort = "vegetable_centre_id"
    }
    if (sort == "startDate"){
        sort = "start_date"
    }
    if (sort == "endDate"){
        sort = "end_date"
    }
    if (sort == "productId"){
        sort = "product_id"
    }
    if (sort == "providerId"){
        sort = "provider_id"
    }


    var sort2 = sort + sort1;

    return sort2;

}

function centreRefresh(){

    var dataSource = $("#clientGrid").dxDataGrid("getDataSource");
    dataSource.reload()
    var dataGridInstance = $("#clientGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();
}