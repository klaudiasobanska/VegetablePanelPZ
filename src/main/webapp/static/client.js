var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});
$(function () {

    showMenu(6);

    $("#searchBox").dxTextBox({
        placeholder: "Wyszukaj",
        showClearButton: true,
        mode: "search",
        onEnterKey: function (o) {
            clientRefresh();
        }
    });

    $("#addButton").dxButton({
        icon: "add",
        onClick: function () {
            addOrEditClient(false);
        }
    });

    $("#editButton").dxButton({
        icon: "edit",
        onClick: function () {
            var dataGrid = $("#clientGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                addOrEditClient(true);
                clientRefresh();
            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }
    });

    $("#deleteButton").dxButton({
        icon: "trash",
        onClick: function () {

            var dataGrid = $("#clientGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                deleteClient(true);

            } else {
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }

    });
    showClientGrid();
});
function showClientGrid(){
    $.get('./vegetable/centre/user?userId='+userId, function (result) {
        var clintDataSource = new DevExpress.data.DataSource({


            load: function (loadOptions) {
                var params = {
                    p: $("#searchBox").dxTextBox('instance').option('value'),
                    centreId: result.id
                };
                var d = $.Deferred();
                $.getJSON('/client/search', {
                    p: params.p ? params.p : "",
                    centreId: params.centreId ? params.centreId:-1,
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

function addOrEditClient(editTrueFlag){

    var client = getClient();

    $.get('./vegetable/centre/user?userId='+userId, function (result) {

        $("#addOrEditPopup").dxPopup({
            height: 270,
            width: 900
        }).dxPopup("show");

        var newClient = {
            name: "",
            city: "",
            address: "",
            email: "",
            phoneNumber: "",
            nip: ""
        };

        if (editTrueFlag == true) {
            newClient.name = client.name;
            newClient.city = client.city;
            newClient.address = client.address;
            newClient.email = client.email;
            newClient.phoneNumber = client.phoneNumber;
            newClient.nip = client.nip;

        } else {
            $("#addOrEditPopup").dxPopup({
                title: "Nowy Klient"
            });
            $("#addOrEditClientForm").val("");
        }


        $("#addOrEditClientForm").dxForm({
            formData: newClient,
            readOnly: false,
            colCount: 2,
            items: [{
                dataField: "name",
                label: {
                    text: "Nazwa"
                },
                validationRules: [{
                    type: "required",
                    message: "Nazwa jest wymagana"
                }]
            }, {
                dataField: "city",
                label: {
                    text: "Miejscowość"
                },
                validationRules: [{
                    type: "required",
                    message: "Miejscowość jest wymagana"
                }]
            }, {
                dataField: "address",
                label: {
                    text: "Adres"
                },
                validationRules: [{
                    type: "required",
                    message: "Adres jest wymagany"
                }]
            }, {
                dataField: "email",
                label: {
                    text: "Email"
                },
                validationRules: [{
                    type: "required",
                    message: "Email jest wymagany"
                }, {
                    type: "email",
                    message: "Email jest niepoprawny"
                }]
            }, {
                dataField: "phoneNumber",
                label: {
                    text: "Numer telefony"
                },
                validationRules: [{
                    type: "required",
                    message: "Numer telefonu jest wymagany"
                }]
            }, {
                dataField: "nip",
                label: {
                    text: "NIP"
                },
                validationRules: [{
                    type: "required",
                    message: "NIP jest wymagany"
                }]
            }]
        });

        $("#saveClientButton").dxButton({
            text: "Zapisz",
            width: "100px",
            onClick: function (e) {

                var f = $("#addOrEditClientForm").dxForm('instance');
                var newName = f.getEditor('name').option('value');
                var newCity = f.getEditor('city').option('value');
                var newAddress = f.getEditor('address').option('value');
                var newEmail = f.getEditor('email').option('value');
                var newPhoneNumber = f.getEditor('phoneNumber').option('value');
                var newNip = f.getEditor('nip').option('value');

                var ret = f.validate();

                if (ret.isValid) {
                    newClient.name = newName;
                    newClient.city = newCity;
                    newClient.address = newAddress;
                    newClient.email = newEmail;
                    newClient.phoneNumber = newPhoneNumber;
                    newClient.nip = newNip;

                    newClient = JSON.stringify(newClient);

                    if (editTrueFlag) {
                        $.ajax({
                            url: './client/' + client.id,
                            type: 'put',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function (result) {
                                clientRefresh();
                            },
                            data: newClient
                        });
                    } else {

                        $.ajax({
                            url: './vegetable/add/client?centreId='+result.id,
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                clientRefresh();
                            },
                            data: newClient
                        });
                    }
                    $("#addOrEditPopup").dxPopup("hide");
                    showClientGrid();
                }

            }
        });

        $("#cancelClientButton").dxButton({
            text: "Anuluj",
            width: "100px",
            onClick: function (e) {
                $("#addOrEditPopup").dxPopup("hide");
            }
        })
    });
}


function deleteClient(){

    var client = getClient();

    $.get('./vegetable/centre/user?userId='+userId, function (result) {
        $.post('./vegetable/delete/client?clientId=' + client.id + "&centreId=" + result.id, function (result) {
                if (result.errorMsg) {
                    $("#errorDeleteToast").dxToast({
                        message: "Nastąpił błąd w trakcie usuwania",
                        type: "error",
                        displayTime: 2000
                    }).dxToast("show");

                } else {
                    $("#okDeleteToast").dxToast({
                        message: "Klient został poprawnie usunięty",
                        type: "success",
                        displayTime: 2000
                    }).dxToast("show");
                    clientRefresh();


                }
            }
        )
    });
}

function clientRefresh(){

    var dataSource = $("#clientGrid").dxDataGrid("getDataSource");
    dataSource.reload()
    var dataGridInstance = $("#clientGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();
}

function getClient(){
    var dataGrid = $("#clientGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}