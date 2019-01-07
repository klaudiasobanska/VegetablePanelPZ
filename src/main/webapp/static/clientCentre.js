var userId;

$.get('../users/current', function (data) {
    userId = data.id;

});
$(function () {

    showMenuClient(1);

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
    $.get('../client/user?userId='+userId, function (result) {

        var clintDataSource = new DevExpress.data.DataSource({

            load: function (loadOptions) {
                var params = {
                    p: $("#searchBox").dxTextBox('instance').option('value'),
                    clientId: result.id
                };
                var d = $.Deferred();
                $.getJSON('../client/centre/search', {
                    p: params.p ? params.p : "",
                    clientId: params.clientId ? params.clientId:-1,
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
                caption: "Nazwa",
                dataField: "name"
            }, {
                caption: "Miejscowość",
                dataField: "city"
            }, {
                caption: "Adres",
                dataField: "address"

            }, {
                caption: "Email",
                dataField: "email"

            }, {
                caption: "Numer telefonu",
                dataField: "phoneNumber",
                width: 150

            }, {
                caption: "NIP",
                dataField: "nip"

            }]
        });
    });

};

function sortString(loadOptions) {

    /*settings of sorting for clint grid*/

    if(!loadOptions.sort) return 'id,asc';

    var sort=loadOptions.sort[0].selector;
    var sort1= loadOptions.sort[0].desc===true?',desc':',asc';
    if (sort == "phoneNumber"){
        sort = "phone_number"
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