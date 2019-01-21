var menuData = [/*{
    id: 1,
    name: "Home"
},*/ {
    id: 1,
    name: "Skupy"

}, {
    id: 2,
    name: "Kontrakty"
},{
    id: 3,
    name: "Rozliczenia"
}];


function showMenuClient(page) {

    /*if (page <= 7 ){

    }else{
        menuData[page - 1].selected = true;
    }*/

    menuData[page -1].selected = true;

    $("#panelMenu").dxMenu({
        dataSource: menuData,
        displayExpr: "name",
        selectionMode: "single",
        selectionByClick: true,
        onItemClick: function (data) {
            switch (data.itemData.id) {
                case 1:
                    location.href = './centre';
                    break;
                case 2:
                    location.href = './contract';
                    break;
                case 3:
                    location.href = './csett';

            }
        }

    })
};

$(function loadPanel() {

    showMenuClient(1);

});