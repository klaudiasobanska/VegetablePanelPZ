
var userId;

$.get('./users/current', function (data) {
    userId = data.id;

});

$(function () {

    showMenu(4);

    $("#searchBox").dxTextBox({
        placeholder: "Wyszukaj",
        showClearButton: true,
        mode: "search",
        onEnterKey: function (o) {
            providerRefresh();
        }
    });

    $("#addButton").dxButton({
        icon: "add",
        onClick: function () {
            addOrEditProvider(false);
        }
    });

    $("#editButton").dxButton({
        icon: "edit",
        onClick: function () {
            var dataGrid = $("#providerGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                addOrEditProvider(true);
                providerRefresh();
            }else{
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

            var dataGrid = $("#providerGrid").dxDataGrid("instance");
            if (dataGrid.getSelectedRowsData().length > 0) {
                deleteProvider(true);

            }else{
                $("#noSelectedRowToast").dxToast({
                    message: "Wiersz nie został wybrany",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            }
        }

    });
    $.get('./vegetable/centre/user?userId='+userId, function (result) {
        var providerDataSource = new DevExpress.data.DataSource({


            load: function (loadOptions) {
                var params = {
                    p: $("#searchBox").dxTextBox('instance').option('value'),
                    centreId: result.id
                };
                var d = $.Deferred();
                $.getJSON('/provider/search', {
                    p: params.p ? params.p : "",
                    centreId: params.centreId ? params.centreId: -1,
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


        /*show grid with providers in provider page*/
        $("#providerGrid").dxDataGrid({
            dataSource: providerDataSource,
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

});

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

function addOrEditProvider(editTrueFlag){

    var provider = getProvider();

    $.get('./vegetable/centre/user?userId='+userId, function (result) {

        $("#addOrEditPopup").dxPopup({
            height: 270,
            width: 900
        }).dxPopup("show");

        var newProvider = {
            name: "",
            city: "",
            address: "",
            email: "",
            phoneNumber: "",
            nip: "",
            vegetableCentreId:""
        };

        if (editTrueFlag == true) {

            newProvider.name = provider.name;
            newProvider.city = provider.city;
            newProvider.address = provider.address;
            newProvider.email = provider.email;
            newProvider.phoneNumber = provider.phoneNumber;
            newProvider.nip = provider.nip;
        } else {
            $("#addOrEditPopup").dxPopup({
                title: "Nowy Dostawca"
            });
            $("#addOrEditProviderForm").val("");
        }


        $("#addOrEditProviderForm").dxForm({
            formData: newProvider,
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

        $("#saveProviderButton").dxButton({
            text: "Zapisz",
            width: "100px",
            onClick: function (e) {

                var f = $("#addOrEditProviderForm").dxForm('instance');
                var newName = f.getEditor('name').option('value');
                var newCity = f.getEditor('city').option('value');
                var newAddress = f.getEditor('address').option('value');
                var newEmail = f.getEditor('email').option('value');
                var newPhoneNumber = f.getEditor('phoneNumber').option('value');
                var newNip = f.getEditor('nip').option('value');

                var ret = f.validate();

                if (ret.isValid) {
                    newProvider.name = newName;
                    newProvider.city = newCity;
                    newProvider.address = newAddress;
                    newProvider.email = newEmail;
                    newProvider.phoneNumber = newPhoneNumber;
                    newProvider.nip = newNip;
                    newProvider.vegetableCentreId = result.id;

                    newProvider = JSON.stringify(newProvider);

                    if (editTrueFlag) {
                        $.ajax({
                            url: './provider/' + provider.id,
                            type: 'put',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function (result) {
                                providerRefresh();
                            },
                            data: newProvider
                        });
                    } else {

                        $.ajax({
                            url: './provider/add',
                            type: 'post',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function () {
                                providerRefresh();
                            },
                            data: newProvider
                        });
                    }
                    $("#addOrEditPopup").dxPopup("hide");
                }

            }
        });

        $("#cancelProviderButton").dxButton({
            text: "Anuluj",
            width: "100px",
            onClick: function (e) {
                $("#addOrEditPopup").dxPopup("hide");
            }
        })
    });
}


function deleteProvider(){

    var provider = getProvider();


    $.post('/provider/delete?id=' + provider.id, function (result) {
            if(result.errorMsg){
                $("#errorDeleteToast").dxToast({
                    message: "Nastąpił błąd w trakcie usuwania",
                    type: "error",
                    displayTime: 2000
                }).dxToast("show");

            } else {
                $("#okDeleteToast").dxToast({
                    message: "Dostawca został poprawnie usunięty",
                    type: "success",
                    displayTime: 2000
                }).dxToast("show");
                providerRefresh();

            }
        }
    )
}

function providerRefresh(){

    var dataSource = $("#providerGrid").dxDataGrid("getDataSource");
    dataSource.reload()
    var dataGridInstance = $("#providerGrid").dxDataGrid("instance");
    dataGridInstance.clearSelection();
}

function getProvider() {
    var dataGrid = $("#providerGrid").dxDataGrid("instance");
    if (dataGrid.getSelectedRowsData().length > 0) {
        return dataGrid.getSelectedRowsData()[0];
    } else {
        console.log("Nie wybrano");
    }
}