$(function () {

    var data = {
        login: "",
        password: ""
    };

    $("#loginForm").dxForm({
        formData: data,
        items: [{
            dataField: "login",
            label: {
                location: "top",
                alignment: "left",
                text: "Login"
            }
        }, {
            dataField: "password",
            editorOptions: {
                mode: 'password'
            },
            label: {
                location: "top",
                size: "20px",
                alignment: "left",
                text: "Hasło"
            }
        }]
    });

    $("#loginButton").dxButton({
        text: "Zaloguj",
        type: "normal",
        onClick: function (e) {
            var loginForm = $("#loginForm").dxForm('instance');
            var logform = {
                username: loginForm.getEditor('login').option('value'),
                password: loginForm.getEditor('password').option('value')
            };

            $.post('./login', logform, function (result) {
            }).done(function () {
                location.href = './home'
            })
        }
    });
});