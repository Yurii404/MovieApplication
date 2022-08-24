'use strict';

// Register `registration` component, along with its associated controller and template
angular.module('login').component('login', {
    templateUrl: 'login/login.template.html',
    controller: ['Authentication',
        function LoginController(Authentication) {
            var self = this;

            self.login = function (user) {
                self.user = null;
                Authentication.login(user).then(function (data) {
                        self.jwt = data.token;

                        localStorage.setItem('token', self.jwt);
                    }
                );

            };

        }
    ]
});
